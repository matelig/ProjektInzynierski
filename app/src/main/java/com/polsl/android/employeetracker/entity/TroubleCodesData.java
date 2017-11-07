package com.polsl.android.employeetracker.entity;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by m_lig on 15.09.2017.
 */

@Entity
public class TroubleCodesData {

    @Expose(serialize = false, deserialize = false)
    private long routeId;
    @Expose
    private String code;
    @Expose
    private long timestamp;
    @Expose
    private int state;
    @Generated(hash = 849108624)
    public TroubleCodesData(long routeId, String code, long timestamp, int state) {
        this.routeId = routeId;
        this.code = code;
        this.timestamp = timestamp;
        this.state = state;
    }
    @Generated(hash = 777316854)
    public TroubleCodesData() {
    }
    public long getRouteId() {
        return this.routeId;
    }
    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
}
