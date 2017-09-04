package com.polsl.android.employeetracker.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by m_lig on 25.07.2017.
 */

@Entity
public class LocationData {

    @Index
    private Long timestamp;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private Long routeId;

    @Generated(hash = 1606831457)
    public LocationData() {
    }
    @Generated(hash = 1464004124)
    public LocationData(Long timestamp, double latitude, double longitude,
            @NotNull Long routeId) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.routeId = routeId;
    }
    public Long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public Long getRouteId() {
        return this.routeId;
    }
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

}
