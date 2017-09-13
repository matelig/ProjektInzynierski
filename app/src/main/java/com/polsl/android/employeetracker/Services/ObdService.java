package com.polsl.android.employeetracker.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.polsl.android.employeetracker.Helper.ApiHelper;
import com.polsl.android.employeetracker.Services.OBDInterface;

/**
 * Created by m_ligus on 01.06.2017.
 */

public class ObdService extends IntentService {

    OBDInterface obdInterface;
    boolean finish = false;
    private String deviceAddress;
    private boolean obdConnected;
    private Long routeId;

    public ObdService() {
        super (".ObdService");
    }

    @Override
    public void onCreate() { //<==to jako pierwsze
        super.onCreate();
        SharedPreferences prefs = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        routeId = prefs.getLong("routeId",0);
        obdInterface = new OBDInterface(this,prefs,routeId);
        finish = prefs.getBoolean("finish",false);
        deviceAddress = prefs.getString(ApiHelper.OBD_DEVICE_ADDRESS,"");
        Toast.makeText(this,"Create",Toast.LENGTH_SHORT).show();
        Log.d("ObdService","OnCreate");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) { //<== drugie
        obdInterface.connect_bt(deviceAddress);
        obdInterface.startODBReadings();
        SharedPreferences prefs = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        obdInterface.connect_bt(deviceAddress);
        obdInterface.startODBReadings();
        while (!finish) {
            if (!obdInterface.isConnected()) {
                finish = prefs.getBoolean("finish",false);
                obdInterface.connect_bt(deviceAddress);
                obdInterface.startODBReadings();
            }
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        obdInterface.finishODBReadings();
        obdInterface.disconnect();
    }




    @Override
    public void onDestroy() {
        Toast.makeText(this,"Delete",Toast.LENGTH_SHORT).show();
    }
}
