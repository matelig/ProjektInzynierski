/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.projektinzynierski.cartrackerapi;

import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author m_lig
 */
public class RouteData {
    private int idUser;
    private Long carVin;
    private BigInteger startDate;
    private BigInteger endDate;
    private List<Location> locationCollection;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int userId) {
        this.idUser = userId;
    }

    public Long getCarVin() {
        return carVin;
    }

    public void setCarVin(Long carVin) {
        this.carVin = carVin;
    }   

    public BigInteger getStartDate() {
        return startDate;
    }

    public void setStartDate(BigInteger startDate) {
        this.startDate = startDate;
    }

    public BigInteger getEndDate() {
        return endDate;
    }

    public void setEndDate(BigInteger endDate) {
        this.endDate = endDate;
    }

    public List<Location> getLocationCollection() {
        return locationCollection;
    }

    public void setLocationCollection(List<Location> locationCollection) {
        this.locationCollection = locationCollection;
    }
}
