package com.polsl.android.employeetracker.fragment;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.entity.User;
import com.polsl.android.employeetracker.helper.ApiHelper;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.services.LocationService;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by m_lig on 04.09.2017.
 */

public class MainActivityFragment extends Fragment {

    @BindView(R.id.service_button)
    Button serviceButton;

    @BindView(R.id.bluetooth_status)
    TextView bluetoothStatusText;

    @BindView(R.id.obd_status)
    TextView OBDStatus;

    @BindView(R.id.car_speed)
    TextView carSpeed;

    @BindView(R.id.main_time)
    TextView serviceTime;

    @BindView(R.id.road_length)
    TextView roadLength;

    @BindView(R.id.car_rpm)
    TextView carRpm;

    @BindView(R.id.location_status)
    TextView locationStatus;

    private DecimalFormat timeFormat = new DecimalFormat("00");

    private final BroadcastReceiver bluetoothStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                setupBluetoothDeviceState(state);
            }
        }
    };

    private final BroadcastReceiver GPSStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(LocationManager.MODE_CHANGED_ACTION)) {
                ContentResolver contentResolver = getContext().getContentResolver();
                final int state = Settings.Secure.getInt(contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
                setupGPSState(state);
            }
        }
    };

    private final BroadcastReceiver OBDStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String message = intent.getStringExtra("message");
            OBDStatus.setText(message);
        }
    };

    private final BroadcastReceiver OBDReadings = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String speed = intent.getStringExtra("speed");
            String engineRpm = intent.getStringExtra("engineRpm");
            carSpeed.setText(speed);
            carRpm.setText(engineRpm);
        }
    };

    private final BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Double distance = intent.getDoubleExtra("distance", 0.0);
            distance = distance / 1000.0;
            roadLength.setText(String.format("%d", Math.round(distance)));
        }
    };

    private final BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long hour = intent.getLongExtra("hour", 0);
            long minute = intent.getLongExtra("minute", 0);
            serviceTime.setText(timeFormat.format(hour) + ":" + timeFormat.format(minute));
        }
    };

    private void setupBluetoothDeviceState(int state) {
        switch (state) {
            case BluetoothAdapter.STATE_OFF:
                bluetoothStatusText.setText(ApiHelper.BluetoothStatus.DEVICE_OFF);
                bluetoothStatusText.setTextColor(Color.RED);
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                bluetoothStatusText.setText(ApiHelper.BluetoothStatus.TURNING_OFF);
                break;
            case BluetoothAdapter.STATE_ON:
                bluetoothStatusText.setText(ApiHelper.BluetoothStatus.DEVICE_ON);
                bluetoothStatusText.setTextColor(Color.GREEN);
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                bluetoothStatusText.setText(ApiHelper.BluetoothStatus.TURING_ON);
                break;
        }
    }

    private void setupGPSState(int state) {
        switch (state) {
            case Settings.Secure.LOCATION_MODE_BATTERY_SAVING:
                locationStatus.setTextColor(Color.RED);
                locationStatus.setText("OFF");
                break;
            case Settings.Secure.LOCATION_MODE_OFF:
                locationStatus.setTextColor(Color.RED);
                locationStatus.setText("OFF");
                break;
            case Settings.Secure.LOCATION_MODE_HIGH_ACCURACY:
                locationStatus.setTextColor(Color.GREEN);
                locationStatus.setText("ON");
                break;
            case Settings.Secure.LOCATION_MODE_SENSORS_ONLY:
                locationStatus.setTextColor(Color.RED);
                locationStatus.setText("OFF");
                break;

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_activity_main, container, false);

        ButterKnife.bind(this, rootView);
        ContentResolver contentResolver = getContext().getContentResolver();
        int state = Settings.Secure.getInt(contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
        setupGPSState(state);
        state = BluetoothAdapter.getDefaultAdapter().getState();
        setupBluetoothDeviceState(state);

        if (isServiceRunning(LocationService.class))
            serviceButton.setText(R.string.stop_service);
        else serviceButton.setText(R.string.start_service);

        IntentFilter filter1 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        getActivity().registerReceiver(bluetoothStateReceiver, filter1);

        IntentFilter obdFilter = new IntentFilter("OBDStatus");
        getActivity().registerReceiver(OBDStatusReceiver, obdFilter);

        IntentFilter obdGetReadings = new IntentFilter("OBDReadings");
        getActivity().registerReceiver(OBDReadings, obdGetReadings);

        IntentFilter locationFilter = new IntentFilter("LocationData");
        getActivity().registerReceiver(locationReceiver, locationFilter);

        IntentFilter timeFilter = new IntentFilter("elapsedTime");
        getActivity().registerReceiver(timeReceiver, timeFilter);

        IntentFilter gpsFilter = new IntentFilter(LocationManager.MODE_CHANGED_ACTION);
        getActivity().registerReceiver(GPSStateReceiver, gpsFilter);

        serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startServiceText = getResources().getString(R.string.start_service);
                if (serviceButton.getText().equals(startServiceText)) {
                    serviceButton.setText(R.string.stop_service);
                    Intent intent = new Intent(getActivity(), LocationService.class);
//                    SharedPreferences prefs = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
                    intent.putExtra(ApiHelper.OBD_DEVICE_ADDRESS, Hawk.get(ApiHelper.OBD_DEVICE_ADDRESS, ""));
                    intent.putExtra("frequency", Hawk.get("frequency", 1));
                    intent.putExtra("sendLocation", Hawk.get("sendLocation", true));
                    User user = Hawk.get(ApiHelper.USER);
                    intent.putExtra(ApiHelper.USER, user);
                    intent.setAction(ApiHelper.START_SERVICE);
                    getActivity().startService(intent);
                } else {
                    serviceButton.setText(R.string.start_service);
                    Intent intent = new Intent(getActivity(), LocationService.class);
                    intent.setAction(ApiHelper.STOP_SERVICE);
                    getActivity().startService(intent);
                }
            }
        });

        return rootView;
    }


    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(bluetoothStateReceiver);
        getActivity().unregisterReceiver(OBDStatusReceiver);
        getActivity().unregisterReceiver(OBDReadings);
        getActivity().unregisterReceiver(locationReceiver);
        getActivity().unregisterReceiver(timeReceiver);
    }
}
