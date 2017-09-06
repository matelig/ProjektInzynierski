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

    private Long timestamp;
    private double latitude;
    private double longitude;
    private Long routeId;

    @Generated(hash = 1606831457)
    public LocationData() {
    }
    @Generated(hash = 388589760)
    public LocationData(Long timestamp, double latitude, double longitude,
            Long routeId) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.routeId = routeId;
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
    public Long getRouteId() {
        return this.routeId;
    }
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
