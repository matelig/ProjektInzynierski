package com.polsl.android.employeetracker.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.control.TroubleCodesCommand;
import com.github.pires.obd.commands.control.VinCommand;
import com.github.pires.obd.commands.engine.LoadCommand;
import com.github.pires.obd.commands.engine.OilTempCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.fuel.ConsumptionRateCommand;
import com.github.pires.obd.commands.fuel.FuelLevelCommand;
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
import com.github.pires.obd.exceptions.NonNumericResponseException;
import com.github.pires.obd.exceptions.UnableToConnectException;
import com.github.pires.obd.exceptions.UnsupportedCommandException;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.commands.ObdSetDefaultCommand;
import com.polsl.android.employeetracker.entity.DaoMaster;
import com.polsl.android.employeetracker.entity.DaoSession;
import com.polsl.android.employeetracker.entity.EngineLoad;
import com.polsl.android.employeetracker.entity.EngineLoadDao;
import com.polsl.android.employeetracker.entity.FuelConsumptionRateData;
import com.polsl.android.employeetracker.entity.FuelConsumptionRateDataDao;
import com.polsl.android.employeetracker.entity.FuelLevelData;
import com.polsl.android.employeetracker.entity.FuelLevelDataDao;
import com.polsl.android.employeetracker.entity.OilTemperatureData;
import com.polsl.android.employeetracker.entity.OilTemperatureDataDao;
import com.polsl.android.employeetracker.entity.RPMData;
import com.polsl.android.employeetracker.entity.RPMDataDao;
import com.polsl.android.employeetracker.entity.SpeedData;
import com.polsl.android.employeetracker.entity.SpeedDataDao;
import com.polsl.android.employeetracker.entity.TroubleCodesData;
import com.polsl.android.employeetracker.entity.TroubleCodesDataDao;

import org.greenrobot.greendao.database.Database;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * Created by m_lig on 25.07.2017.
 */

public class OBDInterface {


    /**
     * Field used to store device address
     */
    private String deviceAddress;
    /**
     * A connected or connecting Bluetooth socket.
     */
    private BluetoothSocket socket;
    /**
     * Context of the LocationService class
     */
    private Context context;
    /**
     * Time between next commands received from OBD interface
     */
    private static Long responseDelay = 200L;
    /**
     * Field that informs if receiving data should be maintain.
     */
    private boolean readValues;
    /**
     * Shared preferences of application
     */
    private SharedPreferences sharedPreferences;
    /**
     * Informs if the device successfully connected with OBD
     */
    private boolean isConnected = false;

    private Long routeId;

    private DaoSession daoSession;
    private Database database;

    private TroubleCodesDataDao troubleCodesDataDao;
    private RPMDataDao rpmDataDao;
    private SpeedDataDao speedDataDao;
    private OilTemperatureDataDao oilTemperatureDataDao;
    private FuelConsumptionRateDataDao fuelConsumptionRateDataDao;
    private FuelLevelDataDao fuelLevelDataDao;
    private EngineLoadDao engineLoadDataDao;
    private Set<String> oldCodes;

    private String numberVIN;

    private float previousFuelLevel = 0;


    public OBDInterface(Context con, SharedPreferences sharedPref, Long routeId) {
        context = con;
        sharedPreferences = sharedPref;
        oldCodes = new HashSet<>();
        this.routeId = routeId;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "main-db");
        database = helper.getWritableDb();
        daoSession = new DaoMaster(database).newSession();
        troubleCodesDataDao = daoSession.getTroubleCodesDataDao();
        rpmDataDao = daoSession.getRPMDataDao();
        speedDataDao = daoSession.getSpeedDataDao();
        oilTemperatureDataDao = daoSession.getOilTemperatureDataDao();
        fuelConsumptionRateDataDao = daoSession.getFuelConsumptionRateDataDao();
        fuelLevelDataDao = daoSession.getFuelLevelDataDao();
        engineLoadDataDao = daoSession.getEngineLoadDao();
    }

    /**
     * Saving new address of the device to shared preferences
     */
    private void saveNewAddress() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(deviceAddress, "previousDeviceAddress");
        editor.commit();
    }

    /**
     * Method that is responsible for connecting the device with OBD
     *
     * @param deviceAddress address of the OBD interface
     */
    public void connect_bt(String deviceAddress) {
        Intent intent = new Intent("OBDStatus");
        intent.putExtra("message", "Trying to connect with device");
        context.sendBroadcast(intent);
        disconnect();
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        btAdapter.cancelDiscovery();
        if (deviceAddress == null || deviceAddress.isEmpty()) {
            intent.putExtra("message", "Device not found");
            context.sendBroadcast(intent);
            return;
        }

        if (!btAdapter.isEnabled()) {
            intent.putExtra("message", "Bluetooth is disabled");
            context.sendBroadcast(intent);
            return;
        }

        BluetoothDevice device = btAdapter.getRemoteDevice(deviceAddress);

        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        try {
            socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            socket.connect();
            isConnected = true;
            intent.putExtra("message", "OBD connected");
            context.sendBroadcast(intent);
        } catch (IOException e) {
            Log.e("gping2", "There was an error while establishing Bluetooth connection. Falling back..", e);
            Class<?> clazz = socket.getRemoteDevice().getClass();
            Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};
            BluetoothSocket sockFallback = null;
            try {
                Method m = clazz.getMethod("createInsecureRfcommSocket", paramTypes);
                Object[] params = new Object[]{Integer.valueOf(1)};
                sockFallback = (BluetoothSocket) m.invoke(socket.getRemoteDevice(), params);
                sockFallback.connect();
                isConnected = true;
                socket = sockFallback;
                intent.putExtra("message", "OBD connected");
                context.sendBroadcast(intent);
            } catch (Exception e2) {
                intent.putExtra("message", "OBD Connection error");
                context.sendBroadcast(intent);
                Log.e("gping2", "BT connect error");
            }
        }
        this.deviceAddress = deviceAddress;
        saveNewAddress();
    }

    /**
     * End reading data
     */
    public void finishODBReadings(long timestamp) {
        readValues = false;
        for (String s : oldCodes) {
            if (s != null && !s.equals("")) {
                TroubleCodesData tdc = new TroubleCodesData(routeId, s, timestamp, 0);
                troubleCodesDataDao.insert(tdc);
            }
        }
        oldCodes = new HashSet<>();
    }

    /**
     * Disconnect from OBD interface
     */
    public void disconnect() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isConnected = false;
    }

    /**
     * Configuration of OBD interface and receiving data from it. Saving received information in
     * database. When any error occurred, the device is disconnected.
     */
    public void startODBReadings() {
        if (isConnected) {
            try {
                readValues = true;
                new Thread() {
                    public void run() {
                        boolean goodRPM = true;
                        boolean goodSpeed = true;
                        boolean goodConsumption = true;
                        boolean goodCodes = true;
                        boolean goodTemperature = true;
                        boolean goodLevel = true;
                        boolean goodLoad = true;
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

                            TimeoutCommand timeoutCommand = new TimeoutCommand(300);
                            timeoutCommand.setResponseTimeDelay(responseDelay);
                            timeoutCommand.run(socket.getInputStream(), socket.getOutputStream());


                        } catch (IOException e) {
                            closeConnection("Failed to connect");
                        } catch (InterruptedException e) {
                            closeConnection("Connection interrupted");
                        } catch (NullPointerException | NonNumericResponseException e) {
                            closeConnection("Restarting");
                        }

                        while (socket.isConnected() && readValues) {
                            try {

                                if (numberVIN == null || numberVIN.isEmpty()) {
                                    try {
                                        VinCommand vinCommand = new VinCommand();
                                        vinCommand.setResponseTimeDelay(responseDelay);
                                        vinCommand.run(socket.getInputStream(), socket.getOutputStream());
                                        numberVIN = vinCommand.getFormattedResult();

                                    } catch (NoDataException | IndexOutOfBoundsException | UnsupportedCommandException | NumberFormatException | NonNumericResponseException e) {
                                        Log.v("OBD vin", "nie otrzymano");
                                    }
                                }

                                Intent OBDReadings = new Intent("OBDReadings");

                                RPMCommand engineRpmCommand = new RPMCommand();
                                SpeedCommand speedCommand = new SpeedCommand();
                                TroubleCodesCommand troubleCodesCommand = new TroubleCodesCommand();
                                OilTempCommand oilTempCommand = new OilTempCommand();
                                FuelLevelCommand fuelLevelCommand = new FuelLevelCommand();
                                ConsumptionRateCommand consumptionRateCommand = new ConsumptionRateCommand();
                                LoadCommand engineLoad = new LoadCommand();


                                engineRpmCommand.setResponseTimeDelay(responseDelay);
                                speedCommand.setResponseTimeDelay(responseDelay);
                                troubleCodesCommand.setResponseTimeDelay(responseDelay);
                                oilTempCommand.setResponseTimeDelay(responseDelay);
                                fuelLevelCommand.setResponseTimeDelay(responseDelay);
                                consumptionRateCommand.setResponseTimeDelay(responseDelay);
                                engineLoad.setResponseTimeDelay(responseDelay);

                                try {
                                    troubleCodesCommand.run(socket.getInputStream(), socket.getOutputStream());
                                    String[] splitted = troubleCodesCommand.getCalculatedResult().split("\n");
                                    Set<String> newCodes = new HashSet<>();
                                    newCodes.add(context.getString(R.string.engine_runtime));
                                    Set<String> tempCodes = new HashSet<>();
                                    for (String s : splitted) {
                                        newCodes.add(s);
                                        tempCodes.add(s);
                                    }
                                    long timestamp = System.currentTimeMillis();
                                    newCodes.removeAll(oldCodes); // dodajemy nowe do bazy - dodajemy ze stanem 1
                                    for (String s : newCodes) {
                                        if (!s.equals("")) {
                                            TroubleCodesData tcd = new TroubleCodesData(routeId, s, timestamp, 1);
                                            troubleCodesDataDao.insert(tcd);
                                        }
                                    }
                                    oldCodes.removeAll(tempCodes); //usuwamy stare z bazy - dodajemy ze stanem 0
                                    for (String s : oldCodes) {
                                        if (!s.equals("")) {
                                            TroubleCodesData tcd = new TroubleCodesData(routeId, s, timestamp, 0);
                                            troubleCodesDataDao.insert(tcd);
                                        }
                                    }
                                    oldCodes.clear();
                                    oldCodes.addAll(tempCodes);
                                } catch (NoDataException e) {
                                    goodCodes = false;
                                } catch (IndexOutOfBoundsException | UnsupportedCommandException | NonNumericResponseException e) {
                                }
                                try {
                                    engineRpmCommand.run(socket.getInputStream(), socket.getOutputStream());
                                    RPMData rpmData = new RPMData(routeId, System.currentTimeMillis(), engineRpmCommand.getRPM());
                                    rpmDataDao.insert(rpmData);
                                    OBDReadings.putExtra("engineRpm", String.valueOf(engineRpmCommand.getRPM()));
                                    goodRPM = true;
                                } catch (NoDataException e) {
                                    OBDReadings.putExtra("engineRpm", "-");
                                    goodRPM = false;
                                } catch (IndexOutOfBoundsException | UnsupportedCommandException | NonNumericResponseException e) {
                                }
                                try {
                                    speedCommand.run(socket.getInputStream(), socket.getOutputStream());
                                    SpeedData speedData = new SpeedData(routeId, speedCommand.getMetricSpeed(), System.currentTimeMillis());
                                    speedDataDao.insert(speedData);
                                    OBDReadings.putExtra("speed", String.valueOf(speedCommand.getMetricSpeed()));
                                    goodSpeed = true;
                                } catch (NoDataException e) {
                                    goodSpeed = false;
                                    OBDReadings.putExtra("speed", "-");
                                } catch (IndexOutOfBoundsException | UnsupportedCommandException | NonNumericResponseException e) {
                                }
                                try {
                                    engineLoad.run(socket.getInputStream(), socket.getOutputStream());
                                    EngineLoad engineLoaddb = new EngineLoad(routeId,engineLoad.getPercentage(),System.currentTimeMillis());
                                    engineLoadDataDao.insert(engineLoaddb);
                                    goodLoad = true;
                                } catch (NoDataException e) {
                                    goodLoad = false;
                                    OBDReadings.putExtra("speed", "-");
                                } catch (IndexOutOfBoundsException | UnsupportedCommandException | NonNumericResponseException e) {
                                }
                                try {
                                    oilTempCommand.run(socket.getInputStream(), socket.getOutputStream());
                                    OilTemperatureData oilTemperatureData = new OilTemperatureData(routeId, oilTempCommand.getTemperature(), System.currentTimeMillis());
                                    oilTemperatureDataDao.insert(oilTemperatureData);
                                    goodTemperature = true;
                                    OBDReadings.putExtra("oil", oilTempCommand.getFormattedResult());
                                } catch (NoDataException e) {
                                    goodTemperature = false;
                                    OBDReadings.putExtra("oil", "-");
                                } catch (IndexOutOfBoundsException | UnsupportedCommandException | NonNumericResponseException e) {
                                }
                                try {
                                    fuelLevelCommand.run(socket.getInputStream(), socket.getOutputStream());
                                    float currentFuelLevel = fuelLevelCommand.getFuelLevel();
                                    if (previousFuelLevel != currentFuelLevel) {
                                        FuelLevelData fuelLevelData = new FuelLevelData(routeId, currentFuelLevel, System.currentTimeMillis());
                                        fuelLevelDataDao.insert(fuelLevelData);
                                        previousFuelLevel = currentFuelLevel;
                                    }
                                    OBDReadings.putExtra("level", fuelLevelCommand.getFormattedResult());
                                    goodLevel = true;
                                } catch (NoDataException e) {
                                    goodLevel = false;

                                    OBDReadings.putExtra("level", "-");
                                } catch (IndexOutOfBoundsException | UnsupportedCommandException | NonNumericResponseException e) {
                                }
                                try {
                                    consumptionRateCommand.run(socket.getInputStream(), socket.getOutputStream());
                                    FuelConsumptionRateData fuelConsumptionRateData = new FuelConsumptionRateData(routeId, consumptionRateCommand.getLitersPerHour(), System.currentTimeMillis());
                                    fuelConsumptionRateDataDao.insert(fuelConsumptionRateData);
                                    OBDReadings.putExtra("consumption", consumptionRateCommand.getFormattedResult());
                                    goodConsumption = true;
                                } catch (NoDataException e) {
                                    goodConsumption = false;
                                    OBDReadings.putExtra("consumption", "-");
                                } catch (IndexOutOfBoundsException | UnsupportedCommandException | NonNumericResponseException e) {
                                }

                                context.sendBroadcast(OBDReadings);
                                if ((!goodCodes) && (!goodRPM) && (!goodSpeed) && (!goodTemperature) && (!goodConsumption) && (!goodLevel) &&(!goodLoad)) {
                                    closeConnection("Reconnect");
                                }
                            } catch (IOException e) {
                                closeConnection("Failed to connect");
                            } catch (InterruptedException e) {
                                closeConnection("Connection interrupted");
                            } catch (UnableToConnectException e) {
                                closeConnection("Unable to connect");
                            } catch (NullPointerException e) {
                                closeConnection("Restarting");
                            }
                        }
                    }
                }.start();
            } catch (MisunderstoodCommandException e) {
                Log.e("gping2", "MisunderstoodCommandException: " + e.toString());
            }
        }
    }

    /**
     * Close connection and send message of problem
     *
     * @param message Message that shows on activity showing the problem
     */
    private void closeConnection(String message) {
        finishODBReadings(System.currentTimeMillis());
        disconnect();
        Intent intent = new Intent("OBDStatus");
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }

    /**
     * @return State of bluetooth connection
     */
    public boolean isConnected() {
        return this.isConnected;
    }

    public String getNumberVIN() {
        return numberVIN;
    }
}
