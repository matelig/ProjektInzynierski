package com.polsl.android.employeetracker.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.polsl.android.employeetracker.Entity.DaoMaster;
import com.polsl.android.employeetracker.Entity.DaoSession;
import com.polsl.android.employeetracker.Entity.FuelConsumptionRateData;
import com.polsl.android.employeetracker.Entity.FuelLevelData;
import com.polsl.android.employeetracker.Entity.LocationData;
import com.polsl.android.employeetracker.Entity.OilTemperatureData;
import com.polsl.android.employeetracker.Entity.RPMData;
import com.polsl.android.employeetracker.Entity.RouteData;
import com.polsl.android.employeetracker.Entity.RouteDataDao;
import com.polsl.android.employeetracker.Entity.SpeedData;
import com.polsl.android.employeetracker.Entity.TroubleCodesData;
import com.polsl.android.employeetracker.Helper.ApiHelper;
import com.polsl.android.employeetracker.R;

import org.greenrobot.greendao.database.Database;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DaoSession daoSession;
    private RouteData routeData;
    private List<LocationData> locationData;
    private RouteDataDao routeDataDao;
    private List<Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Long routeId = intent.getLongExtra(ApiHelper.ROUTE_ID,0);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "main-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        routeDataDao = daoSession.getRouteDataDao();
        routeData = routeDataDao.load(routeId);
        locationData = routeData.getLocationDataList();
        List<SpeedData> speedDataList = routeData.getSpeedDataList();
        List<RPMData> rpmDataList = routeData.getRpmDataList();
        List<FuelConsumptionRateData> fuelConsumptionRateDataList = routeData.getFuelConsumptionRateDataList();
        List<FuelLevelData> fuelLevelDataList = routeData.getFuelLevelDataList();
        List<OilTemperatureData> oilTemperatureDatas = routeData.getOilTemperatureDataList();
        List<TroubleCodesData> troubleCodesDatas = routeData.getTroubleCodesDataList();
        System.out.println("sjgdssj");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int padding = 10;
        markers = new ArrayList<>();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        PolylineOptions polylineOptions = new PolylineOptions();

        for (int i = 0;i<locationData.size();i++) {
            LocationData ld = locationData.get(i);
            LatLng coords = new LatLng(ld.getLatitude(),ld.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(coords)
                    .title(dateFormat.format(new Date(ld.getTimestamp())))
                    .visible(false));
            markers.add(marker);
            polylineOptions.add(coords);
        }
        markers.get(0).setVisible(true);
        markers.get(markers.size()-1).setVisible(true);
        polylineOptions.width(20.0f);
        mMap.addPolyline(polylineOptions);
        mMap.setPadding(padding, padding, padding, padding);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker m : markers) {
            builder.include(m.getPosition());
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 5);
        mMap.setOnMapLoadedCallback(() -> {
            mMap.animateCamera(cameraUpdate);
        });
    }

}
