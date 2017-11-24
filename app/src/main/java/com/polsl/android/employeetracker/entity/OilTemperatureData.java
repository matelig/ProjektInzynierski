package com.polsl.android.employeetracker.entity;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by m_lig on 15.09.2017.
 */

@Entity
public class OilTemperatureData {
    private Long routeId;
    @Expose
    private float value;
    @Expose
    private Long timestamp;
    @Generated(hash = 2037619741)
    public OilTemperatureData(Long routeId, float value, Long timestamp) {
        this.routeId = routeId;
        this.value = value;
        this.timestamp = timestamp;
    }
    @Generated(hash = 1023463194)
    public OilTemperatureData() {
    }
    public Long getRouteId() {
        return this.routeId;
    }
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
    public float getValue() {
        return this.value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public Long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
