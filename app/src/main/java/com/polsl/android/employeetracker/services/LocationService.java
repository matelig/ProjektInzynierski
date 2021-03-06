package com.polsl.android.employeetracker.services;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.RESTApi.CurrentLocation;
import com.polsl.android.employeetracker.activity.SlideActivityPager;
import com.polsl.android.employeetracker.application.CarApp;
import com.polsl.android.employeetracker.entity.DaoMaster;
import com.polsl.android.employeetracker.entity.DaoSession;
import com.polsl.android.employeetracker.entity.LocationData;
import com.polsl.android.employeetracker.entity.LocationDataDao;
import com.polsl.android.employeetracker.entity.RouteData;
import com.polsl.android.employeetracker.entity.RouteDataDao;
import com.polsl.android.employeetracker.entity.User;
import com.polsl.android.employeetracker.helper.ApiHelper;
import com.polsl.android.employeetracker.helper.Timer;

import org.greenrobot.greendao.database.Database;


/**
 * Created by m_lig on 25.07.2017.
 */

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private LocationSettingsRequest.Builder builder;
    private Location currentLocation;
    private DaoSession daoSession;
    private Database database;
    private RouteData routeData;
    private RouteDataDao routeDataDao;
    private LocationDataDao locationDataDao;
    private OBDInterface ODBConnection;
    private String deviceAddress;
    private PowerManager powerManager;
    private SharedPreferences preferences;
    private Long userId;
    private int maxCycle;
    private int currentCycle;
    private boolean sendLocation;
    /**
     * Wake lock used to maintain the device active
     */
    private PowerManager.WakeLock wakeLock;
    private boolean finish = false;
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyWakelockTag");
        wakeLock.acquire();
        currentCycle = 0;

        daoSession = ((CarApp) getApplication()).getDaoSession();
        routeDataDao = daoSession.getRouteDataDao();
        locationDataDao = daoSession.getLocationDataDao();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        timer = new Timer(this);

        buildGoogleApiClient();
        createLocationRequest();
        createBuilder();
        googleApiClient.connect();
        Toast.makeText(this, "OnCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_STICKY;
        }
        if (intent.getAction().equals(ApiHelper.START_SERVICE)) {
            Toast.makeText(this, "Serwis uruchomiony", Toast.LENGTH_SHORT).show();
            User user = intent.getExtras().getParcelable(ApiHelper.USER);
            maxCycle = intent.getIntExtra("frequency",1);
            sendLocation = intent.getBooleanExtra("sendLocation",true);
            Log.v("service cycles",String.valueOf(maxCycle));
            Log.v("service location",String.valueOf(sendLocation));
            userId = user.getId();
            deviceAddress = intent.getStringExtra(ApiHelper.OBD_DEVICE_ADDRESS);
            PendingIntent contentIntent =
                    PendingIntent.getActivity(this, 0, new Intent(this, SlideActivityPager.class), 0);
            Notification notification = new NotificationCompat.Builder(this)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.logo_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                            R.drawable.logo_notification))
                    .setContentIntent(contentIntent)
                    .setContentTitle("Car Tracker is running")
                    .build();
            startForeground(100,
                    notification);
            timer.startTimer();

        } else if (intent.getAction().equals(ApiHelper.STOP_SERVICE)) {
            Toast.makeText(this, "Serwis zatrzymany", Toast.LENGTH_SHORT).show();
            timer.stopTimer();
            routeData.finish();
            if (ODBConnection.getNumberVIN() == null || ODBConnection.getNumberVIN().isEmpty()) {
                routeData.setVinNumber("no number detected");
            } else {
                routeData.setVinNumber(ODBConnection.getNumberVIN());
            }
            routeDataDao.update(routeData);
            finishLocationReadings();
            finish = true;
            ODBConnection.finishODBReadings(routeData.getEndDate());
            ODBConnection.disconnect();
            wakeLock.release();
            this.stopSelf();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        float distance = currentLocation.distanceTo(location);
        routeData.setRoadLength(routeData.getRoadLength() + distance);
        currentLocation = location;
        double longitude = currentLocation.getLongitude();
        double latitude = currentLocation.getLatitude();
        Intent intent = new Intent("LocationData");
        intent.putExtra("long", longitude);
        intent.putExtra("lat", latitude);
        intent.putExtra("distance", routeData.getRoadLength());
        sendBroadcast(intent);
        long timestamp = System.currentTimeMillis();
        LocationData locationData = new LocationData();
        locationData.setLatitude(latitude);
        locationData.setLongitude(longitude);
        locationData.setTimestamp(timestamp);
        locationData.setRouteId(routeData.getId());
        locationDataDao.insert(locationData);
        if (sendLocation) {
            currentCycle++;
            if (currentCycle == maxCycle) {
                CurrentLocation currentLocation = new CurrentLocation();
                currentLocation.setCarVin(routeData.getVinNumber());
                currentLocation.setLatitude(latitude);
                currentLocation.setLongitude(longitude);
                currentLocation.setTimestamp(timestamp);
                currentLocation.setUserId(userId);
                currentLocation.send();
                currentCycle=0;
            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        routeData = new RouteData();
        routeData.start();
        routeData.setUserId(userId);
        routeDataDao.insert(routeData);
        ODBConnection = new OBDInterface(this, preferences, routeData.getId());
        if (ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (currentLocation != null) {
            double longitude = currentLocation.getLongitude();
            double latitude = currentLocation.getLatitude();
            Intent intent = new Intent("LocationData");
            intent.putExtra("long", longitude);
            intent.putExtra("lat", latitude);
            LocationData locationData = new LocationData();
            locationData.setLatitude(latitude);
            locationData.setLongitude(longitude);
            long timestamp = System.currentTimeMillis();
            locationData.setTimestamp(timestamp);
            locationData.setRouteId(routeData.getId());
            locationDataDao.insert(locationData);
            sendBroadcast(intent);
        }
        startLocationReadings();
        maintainOBDConnection();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void maintainOBDConnection() {
        new Thread(() -> {
            while (!finish) {
                if (!ODBConnection.isConnected()) {
                    ODBConnection.connect_bt(deviceAddress);
                    ODBConnection.startODBReadings();
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createBuilder() {
        builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(googleApiClient, builder.build());
    }

    private void startLocationReadings() {
        if (ActivityCompat.checkSelfPermission(LocationService.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(LocationService.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, LocationService.this);
    }

    private void finishLocationReadings() {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        } catch (Exception e) {

        }
    }
}
