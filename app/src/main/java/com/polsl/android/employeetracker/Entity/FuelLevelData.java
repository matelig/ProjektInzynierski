package com.polsl.android.employeetracker.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by m_lig on 15.09.2017.
 */
@Entity
public class FuelLevelData {

    private Long routeId;
    private float value;
    private Long timestamp;
    @Generated(hash = 1087796204)
    public FuelLevelData(Long routeId, float value, Long timestamp) {
        this.routeId = routeId;
        this.value = value;
        this.timestamp = timestamp;
    }
    @Generated(hash = 1362423428)
    public FuelLevelData() {
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
