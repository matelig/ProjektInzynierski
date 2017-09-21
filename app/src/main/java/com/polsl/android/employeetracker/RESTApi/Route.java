package com.polsl.android.employeetracker.RESTApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.polsl.android.employeetracker.Entity.LocationData;
import com.polsl.android.employeetracker.Entity.RouteData;
import com.polsl.android.employeetracker.Entity.User;

import java.util.List;

/**
 * Created by m_lig on 21.09.2017.
 */

public class Route {

    @Expose
    private Long startDate;
    @Expose
    private Long endDate;
    @Expose
    @SerializedName("userId")
    private Long idUser;
    @Expose
    private Long carVin;

    @SerializedName("locationCollection")
    @Expose
    private List<LocationData> locationDataList;


    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public Route(Long startDate, Long endDate, Long idUser, Long carVin, List<LocationData> locationDataList) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.idUser = idUser;
        this.carVin = carVin;
        this.locationDataList = locationDataList;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getCarVin() {
        return carVin;
    }

    public void setCarVin(Long carVin) {
        this.carVin = carVin;
    }

    public List<LocationData> getLocationDataList() {
        return locationDataList;
    }

    public void setLocationDataList(List<LocationData> locationDataList) {
        this.locationDataList = locationDataList;
    }

    public void sendRoute(RouteData routeData) {
        //najlepiej to zaimplementować tutaj
    }

}
