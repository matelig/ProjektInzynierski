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
import com.github.pires.obd.commands.control.VinCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.engine.ThrottlePositionCommand;
import com.github.pires.obd.commands.protocol.CloseCommand;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.HeadersOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.ObdResetCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.SpacesOffCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.enums.ObdProtocols;
import com.github.pires.obd.exceptions.MisunderstoodCommandException;
import com.github.pires.obd.exceptions.NoDataException;


import java.io.IOException;
import java.lang.reflect.Method;
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

    public void disconnect() {
        try {
            if (socket!=null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        disconnect();
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        btAdapter.cancelDiscovery();
        BluetoothDevice device = btAdapter.getRemoteDevice(deviceAddress);

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        try {
            socket = device.createRfcommSocketToServiceRecord(uuid);
            socket.connect();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("obdConnected",true);
            editor.apply();
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
            Log.e("gping2", "There was an error while establishing Bluetooth connection. Falling back..", e);
            Class<?> clazz = socket.getRemoteDevice().getClass();
            Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};
            BluetoothSocket sockFallback = null;
            try {
                Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                Object[] params = new Object[]{Integer.valueOf(1)};
                sockFallback = (BluetoothSocket) m.invoke(socket.getRemoteDevice(), params);
                sockFallback.connect();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("obdConnected",true);
                editor.apply();
                socket = sockFallback;
            } catch (Exception e2) {
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

        }
        saveNewAddress();
    }

    public void finishODBReadings() {
        readValues = false;
    }



    public void startODBReadings() {
        try {
            readValues = true;

            new Thread() {
                public void run() {

                    try {
                        ObdSetDefaultCommand defaultCommand = new ObdSetDefaultCommand();
                        defaultCommand.setResponseTimeDelay(responseDelay);
                        defaultCommand.run(socket.getInputStream(), socket.getOutputStream());

                        ObdResetCommand obdResetCommand = new ObdResetCommand();
                        obdResetCommand.setResponseTimeDelay(responseDelay);
                        obdResetCommand.run(socket.getInputStream(), socket.getOutputStream());

                        EchoOffCommand echoOffCommand = new EchoOffCommand();
                        echoOffCommand.setResponseTimeDelay(responseDelay);
                        echoOffCommand.run(socket.getInputStream(), socket.getOutputStream());

                        LineFeedOffCommand lineFeedOffCommand = new LineFeedOffCommand();
                        lineFeedOffCommand.setResponseTimeDelay(responseDelay);
                        lineFeedOffCommand.run(socket.getInputStream(), socket.getOutputStream());

                        SpacesOffCommand spacesOffCommand = new SpacesOffCommand();
                        spacesOffCommand.setResponseTimeDelay(responseDelay);
                        spacesOffCommand.run(socket.getInputStream(), socket.getOutputStream());

                        HeadersOffCommand headersOffCommand = new HeadersOffCommand();
                        headersOffCommand.setResponseTimeDelay(responseDelay);
                        headersOffCommand.run(socket.getInputStream(), socket.getOutputStream());

                        SelectProtocolCommand selectProtocolCommand = new SelectProtocolCommand(ObdProtocols.AUTO);
                        selectProtocolCommand.setResponseTimeDelay(responseDelay);
                        selectProtocolCommand.run(socket.getInputStream(), socket.getOutputStream());

                        TimeoutCommand timeoutCommand = new TimeoutCommand(200);
                        timeoutCommand.setResponseTimeDelay(responseDelay);
                        timeoutCommand.run(socket.getInputStream(), socket.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = preferences.edit();
                    Intent intent = new Intent("GETDATA");
                    while (socket.isConnected() && readValues) {
                        try {
                            RPMCommand engineRpmCommand = new RPMCommand();
                            SpeedCommand speedCommand = new SpeedCommand();
                            VinCommand vinCommand = new VinCommand();
                            ThrottlePositionCommand throttlePositionCommand = new ThrottlePositionCommand();

                            engineRpmCommand.setResponseTimeDelay(responseDelay);
                            speedCommand.setResponseTimeDelay(responseDelay);
                            throttlePositionCommand.setResponseTimeDelay(responseDelay);
                            vinCommand.setResponseTimeDelay(responseDelay);
                            try {
                                engineRpmCommand.run(socket.getInputStream(), socket.getOutputStream());
                                intent.putExtra("engineRpmCommand", engineRpmCommand.getFormattedResult());
                            } catch (NoDataException e) {
                                intent.putExtra("engineRpmCommand", "NO DATA");
                                editor.putBoolean("obdConnected",false);

                                finishODBReadings();
                            } catch (IndexOutOfBoundsException e) {
                            }
                            try {
                                speedCommand.run(socket.getInputStream(), socket.getOutputStream());
                                intent.putExtra("speed", speedCommand.getFormattedResult());
                            } catch (NoDataException e) {
                                intent.putExtra("speed", "NO DATA");
                                editor.putBoolean("obdConnected",false);

                                finishODBReadings();
                            } catch (IndexOutOfBoundsException e) {
                            }
                            try {
                                throttlePositionCommand.run(socket.getInputStream(), socket.getOutputStream());
                                intent.putExtra("position", throttlePositionCommand.getFormattedResult());
                            } catch (NoDataException e) {
                                intent.putExtra("position", "NO DATA");
                                editor.putBoolean("obdConnected",false);

                                finishODBReadings();
                            } catch (IndexOutOfBoundsException e) {
                            }
                            try {
                                vinCommand.run(socket.getInputStream(), socket.getOutputStream());
                                intent.putExtra("vinNumber", vinCommand.getFormattedResult());
                            } catch (NoDataException e) {
                                intent.putExtra("vinNumber", "NO DATA");
                                editor.putBoolean("obdConnected",false);

                                finishODBReadings();
                            } catch (IndexOutOfBoundsException e) {
                            }
                            context.sendBroadcast(intent);
                            if (!readValues) {
                                editor.apply();
                                disconnect();
                            }
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

        }
    }

    private void resetConnection(BluetoothSocket socket) {
        boolean isConnected = false;
        Intent intent = new Intent("GETDATA");
        while (!isConnected) {
            CloseCommand closeCommand = new CloseCommand();
            closeCommand.setResponseTimeDelay(responseDelay);
            try {
                closeCommand.run(socket.getInputStream(), socket.getOutputStream());
                intent.putExtra("position", closeCommand.getResult());
                RPMCommand engineRpmCommand = new RPMCommand();
                engineRpmCommand.setResponseTimeDelay(responseDelay);
                engineRpmCommand.run(socket.getInputStream(), socket.getOutputStream());
                intent.putExtra("engineRpmCommand", engineRpmCommand.getResult());
                context.sendBroadcast(intent);
                isConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NoDataException e) {
                isConnected = false;
            } catch (IndexOutOfBoundsException e) {
            }
        }


    }
}
