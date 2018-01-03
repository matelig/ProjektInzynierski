package com.polsl.android.employeetracker.entity;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.polsl.android.employeetracker.entity.FuelConsumptionRateData;
import com.polsl.android.employeetracker.entity.FuelLevelData;
import com.polsl.android.employeetracker.entity.LocationData;
import com.polsl.android.employeetracker.entity.OilTemperatureData;
import com.polsl.android.employeetracker.entity.RouteData;
import com.polsl.android.employeetracker.entity.RPMData;
import com.polsl.android.employeetracker.entity.SpeedData;
import com.polsl.android.employeetracker.entity.TroubleCodesData;
import com.polsl.android.employeetracker.entity.EngineLoad;

import com.polsl.android.employeetracker.entity.FuelConsumptionRateDataDao;
import com.polsl.android.employeetracker.entity.FuelLevelDataDao;
import com.polsl.android.employeetracker.entity.LocationDataDao;
import com.polsl.android.employeetracker.entity.OilTemperatureDataDao;
import com.polsl.android.employeetracker.entity.RouteDataDao;
import com.polsl.android.employeetracker.entity.RPMDataDao;
import com.polsl.android.employeetracker.entity.SpeedDataDao;
import com.polsl.android.employeetracker.entity.TroubleCodesDataDao;
import com.polsl.android.employeetracker.entity.EngineLoadDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig fuelConsumptionRateDataDaoConfig;
    private final DaoConfig fuelLevelDataDaoConfig;
    private final DaoConfig locationDataDaoConfig;
    private final DaoConfig oilTemperatureDataDaoConfig;
    private final DaoConfig routeDataDaoConfig;
    private final DaoConfig rPMDataDaoConfig;
    private final DaoConfig speedDataDaoConfig;
    private final DaoConfig troubleCodesDataDaoConfig;
    private final DaoConfig engineLoadDaoConfig;

    private final FuelConsumptionRateDataDao fuelConsumptionRateDataDao;
    private final FuelLevelDataDao fuelLevelDataDao;
    private final LocationDataDao locationDataDao;
    private final OilTemperatureDataDao oilTemperatureDataDao;
    private final RouteDataDao routeDataDao;
    private final RPMDataDao rPMDataDao;
    private final SpeedDataDao speedDataDao;
    private final TroubleCodesDataDao troubleCodesDataDao;
    private final EngineLoadDao engineLoadDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        fuelConsumptionRateDataDaoConfig = daoConfigMap.get(FuelConsumptionRateDataDao.class).clone();
        fuelConsumptionRateDataDaoConfig.initIdentityScope(type);

        fuelLevelDataDaoConfig = daoConfigMap.get(FuelLevelDataDao.class).clone();
        fuelLevelDataDaoConfig.initIdentityScope(type);

        locationDataDaoConfig = daoConfigMap.get(LocationDataDao.class).clone();
        locationDataDaoConfig.initIdentityScope(type);

        oilTemperatureDataDaoConfig = daoConfigMap.get(OilTemperatureDataDao.class).clone();
        oilTemperatureDataDaoConfig.initIdentityScope(type);

        routeDataDaoConfig = daoConfigMap.get(RouteDataDao.class).clone();
        routeDataDaoConfig.initIdentityScope(type);

        rPMDataDaoConfig = daoConfigMap.get(RPMDataDao.class).clone();
        rPMDataDaoConfig.initIdentityScope(type);

        speedDataDaoConfig = daoConfigMap.get(SpeedDataDao.class).clone();
        speedDataDaoConfig.initIdentityScope(type);

        troubleCodesDataDaoConfig = daoConfigMap.get(TroubleCodesDataDao.class).clone();
        troubleCodesDataDaoConfig.initIdentityScope(type);

        engineLoadDaoConfig = daoConfigMap.get(EngineLoadDao.class).clone();
        engineLoadDaoConfig.initIdentityScope(type);

        fuelConsumptionRateDataDao = new FuelConsumptionRateDataDao(fuelConsumptionRateDataDaoConfig, this);
        fuelLevelDataDao = new FuelLevelDataDao(fuelLevelDataDaoConfig, this);
        locationDataDao = new LocationDataDao(locationDataDaoConfig, this);
        oilTemperatureDataDao = new OilTemperatureDataDao(oilTemperatureDataDaoConfig, this);
        routeDataDao = new RouteDataDao(routeDataDaoConfig, this);
        rPMDataDao = new RPMDataDao(rPMDataDaoConfig, this);
        speedDataDao = new SpeedDataDao(speedDataDaoConfig, this);
        troubleCodesDataDao = new TroubleCodesDataDao(troubleCodesDataDaoConfig, this);
        engineLoadDao = new EngineLoadDao(engineLoadDaoConfig, this);

        registerDao(FuelConsumptionRateData.class, fuelConsumptionRateDataDao);
        registerDao(FuelLevelData.class, fuelLevelDataDao);
        registerDao(LocationData.class, locationDataDao);
        registerDao(OilTemperatureData.class, oilTemperatureDataDao);
        registerDao(RouteData.class, routeDataDao);
        registerDao(RPMData.class, rPMDataDao);
        registerDao(SpeedData.class, speedDataDao);
        registerDao(TroubleCodesData.class, troubleCodesDataDao);
        registerDao(EngineLoad.class, engineLoadDao);
    }
    
    public void clear() {
        fuelConsumptionRateDataDaoConfig.clearIdentityScope();
        fuelLevelDataDaoConfig.clearIdentityScope();
        locationDataDaoConfig.clearIdentityScope();
        oilTemperatureDataDaoConfig.clearIdentityScope();
        routeDataDaoConfig.clearIdentityScope();
        rPMDataDaoConfig.clearIdentityScope();
        speedDataDaoConfig.clearIdentityScope();
        troubleCodesDataDaoConfig.clearIdentityScope();
        engineLoadDaoConfig.clearIdentityScope();
    }

    public FuelConsumptionRateDataDao getFuelConsumptionRateDataDao() {
        return fuelConsumptionRateDataDao;
    }

    public FuelLevelDataDao getFuelLevelDataDao() {
        return fuelLevelDataDao;
    }

    public LocationDataDao getLocationDataDao() {
        return locationDataDao;
    }

    public OilTemperatureDataDao getOilTemperatureDataDao() {
        return oilTemperatureDataDao;
    }

    public RouteDataDao getRouteDataDao() {
        return routeDataDao;
    }

    public RPMDataDao getRPMDataDao() {
        return rPMDataDao;
    }

    public SpeedDataDao getSpeedDataDao() {
        return speedDataDao;
    }

    public TroubleCodesDataDao getTroubleCodesDataDao() {
        return troubleCodesDataDao;
    }

    public EngineLoadDao getEngineLoadDao() {
        return engineLoadDao;
    }

}
