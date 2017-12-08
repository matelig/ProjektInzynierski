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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.entity.User;
import com.polsl.android.employeetracker.helper.ApiHelper;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.RESTApi.RESTServicesEndpoints;
import com.polsl.android.employeetracker.RESTApi.RetrofitClient;
import com.polsl.android.employeetracker.util.CryptoHash;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
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
        if (Hawk.contains(ApiHelper.USER)) {
            Intent intent = new Intent(this, SlideActivityPager.class);
            startActivity(intent);
        }
    }

    public void onLoginButtonClick(View view) {
        RESTServicesEndpoints endpoints = RetrofitClient.getApiService();
        User user = new User();
        user.setPesel(pesel.getText().toString());
        String hashedPassword;
        try {
            hashedPassword = CryptoHash.hashPassword(password.getText().toString());
        } catch (NoSuchAlgorithmException e) {
            hashedPassword = password.getText().toString();
        }
        user.setPassword(hashedPassword);

        Call<ResponseBody> calledUser = endpoints.login(user);
        calledUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 404) {
                        Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } else {
                        String loggedUserJSON = response.body().string();
                        Gson gson = new GsonBuilder().create();
                        User loggedUser = gson.fromJson(loggedUserJSON,User.class);
                        Toast.makeText(context, "Logged as " + loggedUser.getName() + " " + loggedUser.getSurname(), Toast.LENGTH_SHORT).show();
                        Hawk.put(ApiHelper.USER, loggedUser);
                        Intent intent = new Intent(context, SlideActivityPager.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, R.string.login_failure, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
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
