package com.polsl.android.employeetracker.entity;

import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.polsl.android.employeetracker.RESTApi.RESTServicesEndpoints;
import com.polsl.android.employeetracker.RESTApi.Route;
import com.polsl.android.employeetracker.helper.UploadStatus;
import com.polsl.android.employeetracker.helper.UploadStatusPropertyConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by m_lig on 27.07.2017.
 */
@Entity
public class RouteData {
    @Generated
    @Id(autoincrement = true)
    @SerializedName("idRoute")
    @Expose
    private Long id;
    @Expose
    private Long startDate;
    @Expose
    private Long endDate;
    @SerializedName("User_idUser")
    @Expose
    private Long userId;
    @Expose
    @SerializedName("Car_vinNumber")
    private String vinNumber="no number detected";
    @Expose
    @SerializedName("roadLength")
    private Double roadLength;

    private boolean toSend;

    @Convert(converter = UploadStatusPropertyConverter.class, columnType = Integer.class)
    private UploadStatus uploadStatus;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "routeId")
    })
    @OrderBy("timestamp ASC")
    @SerializedName("locationCollection")
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


    @ToMany(joinProperties = {
            @JoinProperty(name = "id", referencedName = "routeId")
    })
    @OrderBy("timestamp ASC")
    private List<EngineLoad> engineLoadDataList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 368698595)
    private transient RouteDataDao myDao;


    @Generated(hash = 280729267)
    public RouteData() {
    }

    @Generated(hash = 1471886068)
    public RouteData(Long id, Long startDate, Long endDate, Long userId, String vinNumber,
            Double roadLength, boolean toSend, UploadStatus uploadStatus) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.vinNumber = vinNumber;
        this.roadLength = roadLength;
        this.toSend = toSend;
        this.uploadStatus = uploadStatus;
    }

    public void start() {
        setRoadLength(0.0);
        setStartDate(System.currentTimeMillis());
        setUploadStatus(UploadStatus.NOT_UPLOADED);
        toSend = false;
    }

    public void finish() {
        setEndDate(System.currentTimeMillis());
        setUploadStatus(UploadStatus.READY_TO_UPLOAD);
    }

    public String calculateDuration() {
        long ms = (endDate - startDate);
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

    public Long getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }


    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean getToSend() {
        return this.toSend;
    }

    public void setToSend(boolean toSend) {
        this.toSend = toSend;
    }

    public UploadStatus getUploadStatus() {
        return this.uploadStatus;
    }

    public void setUploadStatus(UploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getVinNumber() {
        return this.vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public Double getRoadLength() {
        return this.roadLength;
    }

    public void setRoadLength(Double roadLength) {
        this.roadLength = roadLength;
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
            List<FuelLevelData> fuelLevelDataListNew = targetDao
                    ._queryRouteData_FuelLevelDataList(id);
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
    @Generated(hash = 900703630)
    public List<EngineLoad> getEngineLoadDataList() {
        if (engineLoadDataList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EngineLoadDao targetDao = daoSession.getEngineLoadDao();
            List<EngineLoad> engineLoadDataListNew = targetDao._queryRouteData_EngineLoadDataList(id);
            synchronized (this) {
                if (engineLoadDataList == null) {
                    engineLoadDataList = engineLoadDataListNew;
                }
            }
        }
        return engineLoadDataList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1663045850)
    public synchronized void resetEngineLoadDataList() {
        engineLoadDataList = null;
    }

}
