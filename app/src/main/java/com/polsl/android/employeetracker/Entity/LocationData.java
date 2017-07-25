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
    private int routeNumber;
    @Generated(hash = 1637779407)
    public LocationData(Long timestamp, double latitude, double longitude,
            int routeNumber) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.routeNumber = routeNumber;
    }
    @Generated(hash = 1606831457)
    public LocationData() {
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
    public int getRouteNumber() {
        return this.routeNumber;
    }
    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }
}
