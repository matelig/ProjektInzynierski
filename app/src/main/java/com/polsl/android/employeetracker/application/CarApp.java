package com.polsl.android.employeetracker.application;

import android.app.Application;
import android.content.ContextWrapper;

import com.orhanobut.hawk.Hawk;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


/**
 * Created by m_lig on 25.10.2017.
 */

public class CarApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Hawk.init(this).build();
    }
}
