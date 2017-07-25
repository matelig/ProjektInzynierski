package com.polsl.android.employeetracker.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.polsl.android.employeetracker.Entity.DaoMaster;
import com.polsl.android.employeetracker.Entity.DaoSession;
import com.polsl.android.employeetracker.Entity.User;
import com.polsl.android.employeetracker.Entity.UserDao;
import com.polsl.android.employeetracker.MyService;
import com.polsl.android.employeetracker.R;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_view)
    TextView name;
    @BindView(R.id.secondText)
    TextView second;
    @BindView(R.id.vin_number)
    TextView vinNumber;
    @BindView(R.id.speed_view)
    TextView speedText;
    @BindView(R.id.button_main)
    Button button;

    Context context = this;
    private static Database db;
    private static DaoSession daoSession;

    private BroadcastReceiver dataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String RPM = intent.getStringExtra("engineRpmCommand");
            String position = intent.getStringExtra("position");
            String vin = intent.getStringExtra("vinNumber");
            String speed = intent.getStringExtra("speed");
            name.setText(RPM);
            second.setText(position);
            vinNumber.setText(vin);
            speedText.setText(speed);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(dataReceiver, new IntentFilter("GETDATA"));
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("finish", false);
        editor.putBoolean("obdConnected", true);
        editor.putInt("engineRpmCommand", 0);
        editor.putLong("position", 0);
        editor.putString("vinNumber", "");
        editor.apply();
    }

    public void onButtonClick(View view) {

        final ArrayList<String> deviceStrs = new ArrayList<>();
        final ArrayList<String> devices = new ArrayList<>();
        boolean useOldAddress = false;
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        int REQUEST_ENABLE_BT = 99;
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                for (Object device : pairedDevices) {
                    BluetoothDevice device1 = (BluetoothDevice) device;
                    Log.d("gping2", "BT: " + device1.getName() + " - " + device1.getAddress());
                    deviceStrs.add(device1.getName() + "\n" + device1.getAddress());
                    devices.add(device1.getAddress());

                }
            }
            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice,
                    deviceStrs.toArray(new String[deviceStrs.size()]));
            alertDialog.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    int position = ((android.app.AlertDialog) dialog).getListView().getCheckedItemPosition();
                    editor.putString("deviceAddress", devices.get(position));
                    editor.putString("deviceName", deviceStrs.get(position));
                    editor.apply();
                }
            });
            alertDialog.setTitle("Choose Bluetooth device");
            alertDialog.show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("button", String.valueOf(second.getId()));
            editor.putBoolean("break", false);
            editor.apply();
        }
    }


    public void onSecondButtonClick(View view) {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    public void onButtonThreeClick(View view) {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("finish",true);
//        editor.apply();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "main-db");
        db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        UserDao userDao = daoSession.getUserDao();
        List<User> users = userDao.loadAll();
        boolean notUnique = false;
        for (User u : users) {
            if (u.getLogin().equals("Mateusz")) {
                Toast.makeText(this,"Wybrany użytkownik jest już zarejestrowany", Toast.LENGTH_SHORT).show();
                notUnique = true;
                break;
            }
        }
        if (!notUnique) {
            User user = new User();
            user.setLogin("Mateusz");
            user.setPassword("1234");
            userDao.insert(user);
        }
    }

    public void onResetButtonClick(View view) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "main-db");
        db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        UserDao userDao = daoSession.getUserDao();

        List<User> users = userDao.loadAll();
        System.out.println("WYKONANE");
    }
}
