package com.polsl.android.employeetracker.RESTApi;

import com.google.gson.annotations.Expose;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by m_lig on 28.10.2017.
 */

public class CurrentLocation {
    @Expose
    private Double latitude;
    @Expose
    private Double longitude;
    @Expose
    private long timestamp;
    @Expose
    private String carVin;
    @Expose
    private long userId;

    public CurrentLocation(Double latitude, Double longitude, long timestamp, String carVin, long userId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.carVin = carVin;
        this.userId = userId;
    }

    public CurrentLocation() {
    }

    public void send() {
        RESTServicesEndpoints endpoints = RetrofitClient.getApiService();
//        Call<ResponseBody> call = endpoints.create(route);
//        call.enqueue(new Callback<ResponseBody>() {
        Call<ResponseBody> call = endpoints.updateCurrentLocation(this);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
