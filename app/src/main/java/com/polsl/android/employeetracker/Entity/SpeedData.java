package com.polsl.android.employeetracker.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by m_lig on 15.09.2017.
 */

@Entity
public class SpeedData {

    private Long routeId;
    private int value;
    private Long timestamp;
    @Generated(hash = 146792878)
    public SpeedData(Long routeId, int value, Long timestamp) {
        this.routeId = routeId;
        this.value = value;
        this.timestamp = timestamp;
    }
    @Generated(hash = 2108339383)
    public SpeedData() {
    }
    public Long getRouteId() {
        return this.routeId;
    }
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public Long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    
}
