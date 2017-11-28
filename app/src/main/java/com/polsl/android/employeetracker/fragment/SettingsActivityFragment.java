package com.polsl.android.employeetracker.fragment;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.activity.LoginActivity;
import com.polsl.android.employeetracker.application.CarApp;
import com.polsl.android.employeetracker.entity.DaoSession;
import com.polsl.android.employeetracker.entity.FuelConsumptionRateData;
import com.polsl.android.employeetracker.entity.FuelConsumptionRateDataDao;
import com.polsl.android.employeetracker.entity.FuelLevelData;
import com.polsl.android.employeetracker.entity.FuelLevelDataDao;
import com.polsl.android.employeetracker.entity.LocationData;
import com.polsl.android.employeetracker.entity.LocationDataDao;
import com.polsl.android.employeetracker.entity.OilTemperatureData;
import com.polsl.android.employeetracker.entity.OilTemperatureDataDao;
import com.polsl.android.employeetracker.entity.RPMData;
import com.polsl.android.employeetracker.entity.RPMDataDao;
import com.polsl.android.employeetracker.entity.RouteData;
import com.polsl.android.employeetracker.entity.RouteDataDao;
import com.polsl.android.employeetracker.entity.SpeedData;
import com.polsl.android.employeetracker.entity.SpeedDataDao;
import com.polsl.android.employeetracker.entity.TroubleCodesData;
import com.polsl.android.employeetracker.entity.TroubleCodesDataDao;
import com.polsl.android.employeetracker.entity.User;
import com.polsl.android.employeetracker.helper.ApiHelper;
import com.polsl.android.employeetracker.helper.UploadStatus;
import com.polsl.android.employeetracker.util.SpinnerListener;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsActivityFragment extends Fragment {

    @BindView(R.id.obd_device_name)
    TextView obdDeviceName;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.logout_button)
    Button logoutButton;

    @BindView(R.id.obd_layout)
    LinearLayout obdLayout;

    @BindView(R.id.frequency_spinner)
    Spinner locationFrequencySpinner;

    @BindView(R.id.location_switch)
    Switch locationSwitch;

    public SettingsActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_settings_activity, container, false);

        ButterKnife.bind(this, rootView); //jedyne słuszne bindowanie widoku z polami
        if (Hawk.contains(ApiHelper.USER)) {
            User user = Hawk.get(ApiHelper.USER);
            userName.setText(String.format("%s %s", user.getName(), user.getSurname()));
        }

        obdDeviceName.setText("Current address:\n" + Hawk.get(ApiHelper.OBD_DEVICE_ADDRESS, ""));

        locationFrequencySpinner.setLayoutMode(Spinner.MODE_DIALOG);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sending_frequency, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        locationFrequencySpinner.setAdapter(adapter);
        locationFrequencySpinner.setOnItemSelectedListener(new SpinnerListener());
        locationFrequencySpinner.setSelection(Hawk.get("selectedSpinnerItem",0));
        locationSwitch.setChecked(Hawk.get("sendLocation",true));
        locationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Hawk.put("sendLocation",isChecked);
        });

        logoutButton.setOnClickListener(v -> {
            Hawk.deleteAll();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        });

        obdLayout.setOnClickListener(v -> {

            final ArrayList<String> deviceStrs = new ArrayList<>();
            final ArrayList<String> devices = new ArrayList<>();
            boolean useOldAddress = false;
            BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
            int REQUEST_ENABLE_BT = 99;
            if (!btAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

                if (pairedDevices.size() > 0) {
                    for (Object device : pairedDevices) {
                        BluetoothDevice device1 = (BluetoothDevice) device;
                        Log.d("gping2", "BT: " + device1.getName() + " - " + device1.getAddress());
                        deviceStrs.add(device1.getName() + "\n" + device1.getAddress());
                        devices.add(device1.getAddress());

                    }
                }
                final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getContext());

                final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_singlechoice,
                        deviceStrs.toArray(new String[deviceStrs.size()]));
                int selectedPosition = -1;
                for (int i = 0 ; i<devices.size();i++) {
                    if (devices.get(i).equals(Hawk.get(ApiHelper.OBD_DEVICE_ADDRESS))) {
                        selectedPosition = i;
                        break;
                    }
                }
                alertDialog.setSingleChoiceItems(adapter1, selectedPosition, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int position = ((android.app.AlertDialog) dialog).getListView().getCheckedItemPosition();
                        Hawk.put(ApiHelper.OBD_DEVICE_ADDRESS, devices.get(position));
                        Hawk.put(ApiHelper.OBD_DEVICE_NAME, deviceStrs.get(position));
                        obdDeviceName.setText("Current address:\n" + devices.get(position));
                    }
                });
                alertDialog.setTitle("Choose Bluetooth device");
                alertDialog.show();
            }
        });

        return rootView;
    }

    private void addToDatabase() {
        User user = Hawk.get(ApiHelper.USER);
        DaoSession daoSession = ((CarApp) getActivity().getApplication()).getDaoSession();
        RouteDataDao routeDataDao = daoSession.getRouteDataDao();
        RouteData routeData = new RouteData();
        routeData.setUserId(user.getId());
        routeData.setStartDate(System.currentTimeMillis());
        routeData.setRoadLength(2410.2154);
        routeData.setVinNumber("123");

        routeData.setUploadStatus(UploadStatus.READY_TO_UPLOAD);
        routeDataDao.insert(routeData);


        LocationDataDao locationDataDao = daoSession.getLocationDataDao();
        LocationData locationData = new LocationData(System.currentTimeMillis(), 11.12, 12.11, routeData.getId());
        locationDataDao.insert(locationData);

        FuelConsumptionRateDataDao fuelConsumptionRateDataDao = daoSession.getFuelConsumptionRateDataDao();
        FuelConsumptionRateData fuelConsumptionRateData = new FuelConsumptionRateData(routeData.getId(), 12.5f, System.currentTimeMillis());
        fuelConsumptionRateDataDao.insert(fuelConsumptionRateData);

        FuelLevelDataDao fuelLevelDataDao = daoSession.getFuelLevelDataDao();
        FuelLevelData fuelLevelData = new FuelLevelData(routeData.getId(), 87.5f, System.currentTimeMillis());
        fuelLevelDataDao.insert(fuelLevelData);

        OilTemperatureDataDao oilTemperatureDataDao = daoSession.getOilTemperatureDataDao();
        OilTemperatureData oilTemperatureData = new OilTemperatureData(routeData.getId(), 37.5f, System.currentTimeMillis());
        oilTemperatureDataDao.insert(oilTemperatureData);

        RPMDataDao rpmDataDao = daoSession.getRPMDataDao();
        RPMData rpmData = new RPMData(routeData.getId(), System.currentTimeMillis(), 987);
        rpmDataDao.insert(rpmData);

        SpeedDataDao speedDataDao = daoSession.getSpeedDataDao();
        SpeedData speedData = new SpeedData(routeData.getId(), 15, System.currentTimeMillis());
        speedDataDao.insert(speedData);

        TroubleCodesDataDao troubleCodesDataDao = daoSession.getTroubleCodesDataDao();
        TroubleCodesData troubleCodesData = new TroubleCodesData(routeData.getId(), "15", System.currentTimeMillis(), 1);
        troubleCodesDataDao.insert(troubleCodesData);
        troubleCodesData = new TroubleCodesData(routeData.getId(), "15", System.currentTimeMillis() + 10000, 0);
        troubleCodesDataDao.insert(troubleCodesData);

        routeData.setEndDate(System.currentTimeMillis() + 100000);
        routeDataDao.update(routeData);
    }

}
