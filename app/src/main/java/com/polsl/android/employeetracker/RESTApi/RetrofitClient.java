package com.polsl.android.employeetracker.RESTApi;

import com.polsl.android.employeetracker.Helper.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by m_lig on 19.09.2017.
 */

public class RetrofitClient {

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RESTServicesEndpoints getApiService() {
        return getRetrofitInstance().create(RESTServicesEndpoints.class);
    }

}
