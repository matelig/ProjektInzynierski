package com.polsl.android.employeetracker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.engine.ThrottlePositionCommand;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.enums.ObdProtocols;
import com.github.pires.obd.exceptions.MisunderstoodCommandException;


import java.io.IOException;
import java.util.UUID;


/**
 * Created by Jakub on 03.05.2017.
 */

public class ODBInterface {


    private String deviceAddress, deviceName;
    private BluetoothSocket socket;
    private Context context;
    private static Long responseDelay = 100L;
    private boolean readValues;
    private Long routeId;
    private SharedPreferences sharedPreferences;
    private boolean useOldAddress = false;

    //shared pref jak w MainService linia 151
    public ODBInterface(Context con, SharedPreferences preferences) {
        context = con;
        sharedPreferences = preferences;
    }


    private void saveNewAddress() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(deviceAddress, "previousDeviceAddress");
        editor.commit();
    }

    public void connect_bt(String deviceAddress) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context,
                        "Trying to connect with device ",
                        Toast.LENGTH_SHORT).show();
            }
        });

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        btAdapter.cancelDiscovery();
        BluetoothDevice device = btAdapter.getRemoteDevice(deviceAddress);

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        try {
            socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            socket.connect();
            handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context,
                            "Connected",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context,
                            "ODB connection failed",
                            Toast.LENGTH_SHORT).show();
                }
            });
            Log.e("gping2", "BT connect error");
        }
        saveNewAddress();
    }

    public void finishODBReadings() {
        readValues = false;
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startODBReadings() {

        try {

            new EchoOffCommand().run(socket.getInputStream(), socket.getOutputStream());

            new LineFeedOffCommand().run(socket.getInputStream(), socket.getOutputStream());

            try {
                new TimeoutCommand(125).run(socket.getInputStream(), socket.getOutputStream());
                //new TimeoutObdCommand().run(socket.getInputStream(), socket.getOutputStream()); A MOZE TO?

            } catch (MisunderstoodCommandException e) {
                Log.d("gping2", "Timeout command not understood, hope that wasn't important..");
            }

            try {
                new SelectProtocolCommand(ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
            } catch (MisunderstoodCommandException e) {
                Log.d("gping2", "Select protocol command failed");
            }

            readValues = true;
            new Thread() {
                public void run() {
                    RPMCommand engineRpmCommand = new RPMCommand();
                    SpeedCommand speedCommand = new SpeedCommand();
                    ThrottlePositionCommand throttlePositionCommand = new ThrottlePositionCommand();
                    engineRpmCommand.setResponseTimeDelay(responseDelay);
                    speedCommand.setResponseTimeDelay(responseDelay);
                    throttlePositionCommand.setResponseTimeDelay(responseDelay);
                    while (socket.isConnected() && readValues) {
                        try {
                            Intent intent = new Intent("GETDATA");
                            engineRpmCommand.run(socket.getInputStream(), socket.getOutputStream());
                            intent.putExtra("engineRpmCommand", engineRpmCommand.getFormattedResult());

                            speedCommand.run(socket.getInputStream(), socket.getOutputStream());
                            intent.putExtra("speed", speedCommand.getImperialSpeed());

                            throttlePositionCommand.run(socket.getInputStream(), socket.getOutputStream());
                            intent.putExtra("position", throttlePositionCommand.getFormattedResult());

                            context.sendBroadcast(intent);
                        } catch (IOException e) {
                        } catch (InterruptedException e) {
                            Log.e("gping2", "test error");
                            e.printStackTrace();
                        }
                    }
                }
            }.start();


        } catch (MisunderstoodCommandException e) {
            Log.e("gping2", "MisunderstoodCommandException: " + e.toString());
        } catch (IOException e) {
            Log.e("gping2", "test error");
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("gping2", "test error");
            e.printStackTrace();
        }
    }
}
