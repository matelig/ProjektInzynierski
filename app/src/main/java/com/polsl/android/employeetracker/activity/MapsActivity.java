package com.polsl.android.employeetracker.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.polsl.android.employeetracker.application.CarApp;
import com.polsl.android.employeetracker.entity.DaoMaster;
import com.polsl.android.employeetracker.entity.DaoSession;
import com.polsl.android.employeetracker.entity.LocationData;
import com.polsl.android.employeetracker.entity.RouteData;
import com.polsl.android.employeetracker.entity.RouteDataDao;
import com.polsl.android.employeetracker.helper.ApiHelper;
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
        daoSession = ((CarApp) getApplication()).getDaoSession();
        routeDataDao = daoSession.getRouteDataDao();
        routeData = routeDataDao.load(routeId);
        locationData = routeData.getLocationDataList();
    }


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
