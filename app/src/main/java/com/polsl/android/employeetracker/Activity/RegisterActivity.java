package com.polsl.android.employeetracker.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.polsl.android.employeetracker.Entity.DaoMaster;
import com.polsl.android.employeetracker.Entity.DaoSession;
import com.polsl.android.employeetracker.Entity.User;
import com.polsl.android.employeetracker.Entity.UserDao;
import com.polsl.android.employeetracker.R;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    @BindView(R.id.login_input)
    EditText login;
    @BindView(R.id.password_input)
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        checkLocationPermission();
    }

    public void onButtonClick(View view) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "main-db");
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        UserDao userDao = daoSession.getUserDao();
        List<User> users = userDao.loadAll();
        boolean notUnique = false;
        for (User u : users) {
            if (u.getLogin().equals(login.getText().toString())) {
                Toast.makeText(this, "Wybrany użytkownik jest już zarejestrowany", Toast.LENGTH_SHORT).show();
                notUnique = true;
                break;
            }
        }
        if (!notUnique) {
            User user = new User();
            user.setLogin(login.getText().toString());
            user.setPassword(password.getText().toString());
            userDao.insert(user);
        }
    }

    public void OnListClicl(View view) {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "main-db");
//        Database db = helper.getWritableDb();
//        DaoSession daoSession = new DaoMaster(db).newSession();
//        UserDao userDao = daoSession.getUserDao();
//        List<User> users = userDao.loadAll();
//        System.out.println("cos");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user asynchronously -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
}
