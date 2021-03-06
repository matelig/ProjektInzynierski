package com.polsl.android.employeetracker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.polsl.android.employeetracker.entity.User;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.RESTApi.RESTServicesEndpoints;
import com.polsl.android.employeetracker.RESTApi.RetrofitClient;
import com.polsl.android.employeetracker.util.CryptoHash;

import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    @BindView(R.id.register_activity_name)
    EditText name;
    @BindView(R.id.register_activity_surname)
    EditText surname;
    @BindView(R.id.register_activity_pesel)
    EditText pesel;
    @BindView(R.id.register_activity_password)
    EditText password;

    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

    }


    public void onRegisterClick(View view) {
        User user = new User();
        user.setName(name.getText().toString());
        user.setSurname(surname.getText().toString());
        String hashedPassword;
        try {
            hashedPassword = CryptoHash.hashPassword(password.getText().toString());
        } catch (NoSuchAlgorithmException e) {
            hashedPassword = password.getText().toString();
        }
        user.setPassword(hashedPassword);
        user.setPesel(pesel.getText().toString());
        RESTServicesEndpoints endpoints = RetrofitClient.getApiService();
        Call<ResponseBody> call = endpoints.create(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
