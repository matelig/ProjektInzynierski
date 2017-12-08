package com.polsl.android.employeetracker.RESTApi;

import com.polsl.android.employeetracker.entity.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by m_lig on 19.09.2017.
 */

public interface RESTServicesEndpoints {

    @Headers("Content-Type: application/json")
    @POST("user/login")
        //<- trzeba mieć w klasie User w api jakas deklaracje login żeby się odwołać
    Call<ResponseBody> login(@Body User user);

    @Headers("Content-Type: application/json")
    @POST("user/create")
    Call<ResponseBody> create(@Body User user);


    @Headers("Content-Type: application/json")
    @GET("user/{id}")
        //pobieranie usera po ID
    Call<User> user(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @GET("user")
        //pobieranie listy userów
    Call<List<User>> usersList();

    @Headers("Content-Type: application/json")
    @POST("route/create")
    Call<ResponseBody> create(@Body Route route);

    @Headers("Content-Type: application/json")
    @POST("route/insert")
    Call<ResponseBody> insert(@Body List<Route> routeData);

    @Headers("Content-Type: application/json")
    @POST("currentLocation/update")
    Call<ResponseBody> updateCurrentLocation(@Body CurrentLocation currentLocation);
}
