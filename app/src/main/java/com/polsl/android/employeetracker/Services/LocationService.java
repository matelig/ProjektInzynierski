package com.polsl.android.employeetracker.Services;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.polsl.android.employeetracker.Entity.DaoMaster;
import com.polsl.android.employeetracker.Entity.DaoSession;
import com.polsl.android.employeetracker.Entity.LocationData;
import com.polsl.android.employeetracker.Entity.LocationDataDao;
import com.polsl.android.employeetracker.Entity.RouteData;
import com.polsl.android.employeetracker.Entity.RouteDataDao;
import com.polsl.android.employeetracker.Helper.Command;

import org.greenrobot.greendao.database.Database;


/**
 * Created by m_lig on 25.07.2017.
 */

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private LocationSettingsRequest.Builder builder;
    private Location currentLocation;
    private DaoSession daoSession;
    private Database database;
    private RouteData routeData;
    private RouteDataDao routeDataDao;
    private LocationDataDao locationDataDao;



    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "main-db");
        database = helper.getWritableDb();
        daoSession = new DaoMaster(database).newSession();
        routeDataDao = daoSession.getRouteDataDao();
        locationDataDao = daoSession.getLocationDataDao();

        buildGoogleApiClient();
        createLocationRequest();
        createBuilder();
        googleApiClient.connect();
        Toast.makeText(this,"OnCreate",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        if (intent==null) {
            return START_STICKY;
        }
        if (intent.getAction().equals(Command.START_SERVICE)) {
            Toast.makeText(this,"Serwis uruchomiony",Toast.LENGTH_SHORT).show();
            //TODO: stworzenie notyfikacji

        } else if (intent.getAction().equals(Command.STOP_SERVICE)) {
            routeData.finish();
            routeDataDao.update(routeData);
            finishLocationReadings();

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
        currentLocation = location;
        double longitude = currentLocation.getLongitude();
        double latitude = currentLocation.getLatitude();
        Intent intent = new Intent("LocationData");
        intent.putExtra("long",longitude);
        intent.putExtra("lat",latitude);
        sendBroadcast(intent);
        long timestamp = System.currentTimeMillis();
        LocationData locationData = new LocationData();
        locationData.setLatitude(latitude);
        locationData.setLongitude(longitude);
        locationData.setTimestamp(timestamp);
        locationData.setRouteId(routeData.getId());
        locationDataDao.insert(locationData);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        routeData = new RouteData();
        routeData.start();
        routeDataDao.insert(routeData);
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
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void buildGoogleApiClient() {
        if (googleApiClient==null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createBuilder() {
        builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(googleApiClient,builder.build());
    }

    private void startLocationReadings() {
        if (ActivityCompat.checkSelfPermission(LocationService.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(LocationService.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest, LocationService.this);
    }

    private void finishLocationReadings() {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        } catch (Exception e) {

        }
    }
}
