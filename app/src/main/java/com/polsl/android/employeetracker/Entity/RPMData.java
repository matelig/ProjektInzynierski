package com.polsl.android.employeetracker.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by m_lig on 15.09.2017.
 */

@Entity
public class RPMData {

    private Long routeId;
    private Long timestamp;
    private int value;
    @Generated(hash = 1314842728)
    public RPMData(Long routeId, Long timestamp, int value) {
        this.routeId = routeId;
        this.timestamp = timestamp;
        this.value = value;
    }
    @Generated(hash = 1658879389)
    public RPMData() {
    }
    public Long getRouteId() {
        return this.routeId;
    }
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
    public Long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
