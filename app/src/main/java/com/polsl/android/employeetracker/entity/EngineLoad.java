package com.polsl.android.employeetracker.entity;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by m_lig on 03.01.2018.
 */
@Entity
public class EngineLoad {
    @Expose(serialize = false, deserialize = false)
    private Long routeId;
    @Expose
    private double value;
    @Expose
    private long timestamp;
    @Generated(hash = 671404977)
    public EngineLoad(Long routeId, double value, long timestamp) {
        this.routeId = routeId;
        this.value = value;
        this.timestamp = timestamp;
    }
    @Generated(hash = 1002879284)
    public EngineLoad() {
    }
    public Long getRouteId() {
        return this.routeId;
    }
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
    public double getValue() {
        return this.value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
   
}
