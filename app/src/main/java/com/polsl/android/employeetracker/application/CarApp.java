package com.polsl.android.employeetracker.application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.entity.DaoMaster;
import com.polsl.android.employeetracker.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

import io.fabric.sdk.android.Fabric;


/**
 * Created by m_lig on 25.10.2017.
 */

public class CarApp extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
        Fabric.with(this, new Crashlytics());
        Hawk.init(this).build();
    }

    private void initDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "main-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
