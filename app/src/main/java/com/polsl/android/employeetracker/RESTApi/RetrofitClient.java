package com.polsl.android.employeetracker.RESTApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.polsl.android.employeetracker.Helper.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by m_lig on 19.09.2017.
 */

public class RetrofitClient {

    private static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RESTServicesEndpoints getApiService() {
        return getRetrofitInstance().create(RESTServicesEndpoints.class);
    }

}
