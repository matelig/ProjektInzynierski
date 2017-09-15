package com.polsl.android.employeetracker.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by m_lig on 27.07.2017.
 */
@Entity
public class RouteData {
    @Generated
    @Id(autoincrement = true)
    private Long id;
    private Date startDate;
    private Date endDate;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "routeId")
    })
    @OrderBy("timestamp ASC")
    private List<LocationData> locationDataList;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "routeId")
    })
    @OrderBy("timestamp ASC")
    private List<TroubleCodesData> troubleCodesDataList;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "routeId")
    })
    @OrderBy("timestamp ASC")
    private List<RPMData> rpmDataList;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "routeId")
    })
    @OrderBy("timestamp ASC")
    private List<SpeedData> speedDataList;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "routeId")
    })
    @OrderBy("timestamp ASC")
    private List<OilTemperatureData> oilTemperatureDataList;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "routeId")
    })
    @OrderBy("timestamp ASC")
    private List<FuelConsumptionRateData> fuelConsumptionRateDataList;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "routeId")
    })
    @OrderBy("timestamp ASC")
    private List<FuelLevelData> fuelLevelDataList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 368698595)
    private transient RouteDataDao myDao;

    @Generated(hash = 1561554076)
    public RouteData(Long id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Generated(hash = 280729267)
    public RouteData() {
    }

    public void start() {
        setStartDate(new Date(System.currentTimeMillis()));
    }

    public void finish() {
        setEndDate(new Date(System.currentTimeMillis()));
    }

    public String calculateDuration() {
        long ms = (endDate.getTime() - startDate.getTime());
        long seconds = (ms / 1000) % 60;
        long minutes = (ms / (1000 * 60)) % 60;
        long hours = (ms / (1000 * 60 * 60)) % 24;
        return  String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1179144003)
    public List<LocationData> getLocationDataList() {
        if (locationDataList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LocationDataDao targetDao = daoSession.getLocationDataDao();
            List<LocationData> locationDataListNew = targetDao
                    ._queryRouteData_LocationDataList(id);
            synchronized (this) {
                if (locationDataList == null) {
                    locationDataList = locationDataListNew;
                }
            }
        }
        return locationDataList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1180624715)
    public synchronized void resetLocationDataList() {
        locationDataList = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 884091940)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRouteDataDao() : null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2084841790)
    public List<TroubleCodesData> getTroubleCodesDataList() {
        if (troubleCodesDataList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TroubleCodesDataDao targetDao = daoSession.getTroubleCodesDataDao();
            List<TroubleCodesData> troubleCodesDataListNew = targetDao
                    ._queryRouteData_TroubleCodesDataList(id);
            synchronized (this) {
                if (troubleCodesDataList == null) {
                    troubleCodesDataList = troubleCodesDataListNew;
                }
            }
        }
        return troubleCodesDataList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 72996683)
    public synchronized void resetTroubleCodesDataList() {
        troubleCodesDataList = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1299221027)
    public List<RPMData> getRpmDataList() {
        if (rpmDataList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RPMDataDao targetDao = daoSession.getRPMDataDao();
            List<RPMData> rpmDataListNew = targetDao._queryRouteData_RpmDataList(id);
            synchronized (this) {
                if (rpmDataList == null) {
                    rpmDataList = rpmDataListNew;
                }
            }
        }
        return rpmDataList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1744332308)
    public synchronized void resetRpmDataList() {
        rpmDataList = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1810813848)
    public List<SpeedData> getSpeedDataList() {
        if (speedDataList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SpeedDataDao targetDao = daoSession.getSpeedDataDao();
            List<SpeedData> speedDataListNew = targetDao._queryRouteData_SpeedDataList(id);
            synchronized (this) {
                if (speedDataList == null) {
                    speedDataList = speedDataListNew;
                }
            }
        }
        return speedDataList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1865682672)
    public synchronized void resetSpeedDataList() {
        speedDataList = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1202414507)
    public List<OilTemperatureData> getOilTemperatureDataList() {
        if (oilTemperatureDataList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OilTemperatureDataDao targetDao = daoSession.getOilTemperatureDataDao();
            List<OilTemperatureData> oilTemperatureDataListNew = targetDao
                    ._queryRouteData_OilTemperatureDataList(id);
            synchronized (this) {
                if (oilTemperatureDataList == null) {
                    oilTemperatureDataList = oilTemperatureDataListNew;
                }
            }
        }
        return oilTemperatureDataList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 75226629)
    public synchronized void resetOilTemperatureDataList() {
        oilTemperatureDataList = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1667797255)
    public List<FuelConsumptionRateData> getFuelConsumptionRateDataList() {
        if (fuelConsumptionRateDataList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FuelConsumptionRateDataDao targetDao = daoSession.getFuelConsumptionRateDataDao();
            List<FuelConsumptionRateData> fuelConsumptionRateDataListNew = targetDao
                    ._queryRouteData_FuelConsumptionRateDataList(id);
            synchronized (this) {
                if (fuelConsumptionRateDataList == null) {
                    fuelConsumptionRateDataList = fuelConsumptionRateDataListNew;
                }
            }
        }
        return fuelConsumptionRateDataList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 782084382)
    public synchronized void resetFuelConsumptionRateDataList() {
        fuelConsumptionRateDataList = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 686232964)
    public List<FuelLevelData> getFuelLevelDataList() {
        if (fuelLevelDataList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FuelLevelDataDao targetDao = daoSession.getFuelLevelDataDao();
            List<FuelLevelData> fuelLevelDataListNew = targetDao._queryRouteData_FuelLevelDataList(id);
            synchronized (this) {
                if (fuelLevelDataList == null) {
                    fuelLevelDataList = fuelLevelDataListNew;
                }
            }
        }
        return fuelLevelDataList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1266587033)
    public synchronized void resetFuelLevelDataList() {
        fuelLevelDataList = null;
    }

}
