package com.polsl.android.employeetracker.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by m_lig on 15.09.2017.
 */

@Entity
public class TroubleCodesData {

    private long routeId;
    private String code;
    private long timestamp;
    @Generated(hash = 369829970)
    public TroubleCodesData(long routeId, String code, long timestamp) {
        this.routeId = routeId;
        this.code = code;
        this.timestamp = timestamp;
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
}