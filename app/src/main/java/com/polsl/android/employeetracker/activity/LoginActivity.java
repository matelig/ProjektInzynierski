package com.polsl.android.employeetracker.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.entity.User;
import com.polsl.android.employeetracker.helper.ApiHelper;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.RESTApi.RESTServicesEndpoints;
import com.polsl.android.employeetracker.RESTApi.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    final Context context = this;
    @BindView(R.id.login_activity_name)
    EditText pesel;


    @BindView(R.id.login_activity_password)
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        checkLocationPermission();
//        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
//        if (!prefs.getString(ApiHelper.USER_PESEL,"").isEmpty()) {
//            Intent intent = new Intent(this,SlideActivityPager.class);
//            startActivity(intent);
//        }
        if (Hawk.contains(ApiHelper.USER)) {
            Intent intent = new Intent(this, SlideActivityPager.class);
            startActivity(intent);
        }
    }

    public void onLoginButtonClick(View view) {
        RESTServicesEndpoints endpoints = RetrofitClient.getApiService();
        User user = new User();
        user.setPesel(pesel.getText().toString());
        user.setPassword(password.getText().toString());

        Call<User> calledUser = endpoints.login(user);
        calledUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User loggedUser = response.body();
                if (loggedUser == null) {
                    Toast.makeText(context, R.string.wrong_user, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "logged as " + loggedUser.getName() + " " + loggedUser.getSurname(), Toast.LENGTH_SHORT).show();
//                    SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
//                    prefs.edit().putString(ApiHelper.USER_NAME, loggedUser.getName()).apply();
//                    prefs.edit().putString(ApiHelper.USER_SURNAME, loggedUser.getSurname()).apply();
//                    prefs.edit().putString(ApiHelper.USER_PESEL, loggedUser.getPesel()).apply();
//                    prefs.edit().putLong(ApiHelper.USER_ID, loggedUser.getId()).apply();
                    Hawk.put(ApiHelper.USER, loggedUser);
                    Intent intent = new Intent(context, SlideActivityPager.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, R.string.login_failure, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void offlineModeCLick(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        //set title
        //alertDialogBuilder.setTitle("Your Title");

        // set dialog message
        alertDialogBuilder
                .setMessage(R.string.offline_mode_dialog)
                .setCancelable(false)
                .setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, SlideActivityPager.class);
                        startActivity(intent);
                        //TODO: Zapamiętanie, że pracujemy offline
                    }
                })
                .setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
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

    public void registerNewUser(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
