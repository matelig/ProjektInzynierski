package com.polsl.android.employeetracker.Activity;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;
import com.polsl.android.employeetracker.Helper.ApiHelper;
import com.polsl.android.employeetracker.R;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsActivityFragment extends Fragment {

    @BindView(R.id.obd_device_button)
    Button obdButton;

    @BindView(R.id.obd_device_name)
    TextView obdDeviceName;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_surname)
    TextView userSurname;

    @BindView(R.id.logout_button)
    Button logoutButton;

    public SettingsActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_settings_activity, container, false);

        ButterKnife.bind(this, rootView); //jedyne słuszne bindowanie widoku z polami

        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
        userName.setText(prefs.getString(ApiHelper.USER_NAME,""));
        userSurname.setText(prefs.getString(ApiHelper.USER_SURNAME,""));

        obdDeviceName.setText(prefs.getString(ApiHelper.OBD_DEVICE_ADDRESS, ""));

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
                prefs.edit().putString(ApiHelper.USER_NAME, "").apply();
                prefs.edit().putString(ApiHelper.USER_SURNAME, "").apply();
                prefs.edit().putString(ApiHelper.USER_PESEL, "").apply();
                prefs.edit().putLong(ApiHelper.USER_ID, 0).apply();
                Intent intent = new Intent (getActivity(),LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        obdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_singlechoice,
                            deviceStrs.toArray(new String[deviceStrs.size()]));
                    alertDialog.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            int position = ((android.app.AlertDialog) dialog).getListView().getCheckedItemPosition();
                            SharedPreferences prefs = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
                            prefs.edit().putString(ApiHelper.OBD_DEVICE_ADDRESS, devices.get(position)).apply();
                            prefs.edit().putString(ApiHelper.OBD_DEVICE_NAME, deviceStrs.get(position)).apply();
                            obdDeviceName.setText(devices.get(position));
                        }
                    });
                    alertDialog.setTitle("Choose Bluetooth device");
                    alertDialog.show();
                }
            }
        });

        return rootView;
    }

}
