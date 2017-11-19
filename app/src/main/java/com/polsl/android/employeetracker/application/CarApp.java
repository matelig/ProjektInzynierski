package com.polsl.android.employeetracker.application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.entity.DaoSession;

import io.fabric.sdk.android.Fabric;


/**
 * Created by m_lig on 25.10.2017.
 */

public class CarApp extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Hawk.init(this).build();
    }

    private void initDB() {

    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
