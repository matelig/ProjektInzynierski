package com.polsl.android.employeetracker.helper;

/**
 * Created by m_lig on 25.07.2017.
 */

public class ApiHelper {
    public static final String START_SERVICE = "START";
    public static final String STOP_SERVICE = "STOP";
    public static final String USER = "user";
    public static final String USER_NAME = "userName";
    public static final String USER_SURNAME = "userSurname";
    public static final String USER_PESEL = "userPesel";
    public static final String USER_ID = "userId";
    public static final String OBD_DEVICE_ADDRESS = "deviceAddress";
    public static final String OBD_DEVICE_NAME = "deviceName";
    public static final String ROUTE_ID = "routeId";

    public static final String[] monthNames = {"January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};

    public class BluetoothStatus{
        public static final String DEVICE_ON = "ON";
        public static final String DEVICE_OFF = "OFF";
        public static final String TURING_ON = "TURNING ON";
        public static final String TURNING_OFF = "TURNING OFF";
    }

}
