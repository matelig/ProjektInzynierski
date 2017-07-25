package com.polsl.android.employeetracker;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by m_ligus on 01.06.2017.
 */

public class MyService extends IntentService {

    OBDInterface obdInterface;
    boolean finish = false;
    private String deviceAddress;
    private boolean obdConnected;

    public MyService() {
        super (".MyService");
    }

    @Override
    public void onCreate() { //<==to jako pierwsze
        super.onCreate();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        obdInterface = new OBDInterface(this,preferences);
        finish = preferences.getBoolean("finish",false);
        deviceAddress = preferences.getString("deviceAddress","");
        Toast.makeText(this,"Create",Toast.LENGTH_SHORT).show();
        Log.d("MyService","OnCreate");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) { //<== drugie
        obdInterface.connect_bt(deviceAddress);
        obdInterface.startODBReadings();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        while (!finish) {
            finish = preferences.getBoolean("finish",false);
            obdConnected = preferences.getBoolean("obdConnected",true);
            if (!obdConnected) {
                obdInterface.connect_bt(deviceAddress);
                obdInterface.startODBReadings();
            }
        }
        obdInterface.finishODBReadings();
        obdInterface.disconnect();
    }




    @Override
    public void onDestroy() {
        Toast.makeText(this,"Delete",Toast.LENGTH_SHORT).show();
    }
}
