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
    private String carVin;
    private BigInteger startDate;
    private BigInteger endDate;
    private Double roadLength;
    private List<Location> locationCollection;
    private List<Speed> speedCollection;
    private List<Rpm> RPMCollection;
    private List<TroubleCodes> troubleCodesCollection;
    private List<FuelComsumptionRate> fuelConsumptionCollection;
    private List<FuelLevel> fuelLevelCollection;
    private List<OilTemperature> oilTemperatureCollection;

    public List<Speed> getSpeedCollection() {
        return speedCollection;
    }

    public void setSpeedCollection(List<Speed> speedCollection) {
        this.speedCollection = speedCollection;
    }

    public List<Rpm> getRPMCollection() {
        return RPMCollection;
    }

    public void setRPMCollection(List<Rpm> RPMCollection) {
        this.RPMCollection = RPMCollection;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int userId) {
        this.idUser = userId;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
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

    public List<TroubleCodes> getTroubleCodesCollection() {
        return troubleCodesCollection;
    }

    public void setTroubleCodesCollection(List<TroubleCodes> troubleCodesCollection) {
        this.troubleCodesCollection = troubleCodesCollection;
    }

    public Double getRoadLength() {
        return roadLength;
    }

    public void setRoadLength(Double roadLength) {
        this.roadLength = roadLength;
    }

    public List<FuelComsumptionRate> getFuelConsumptionCollection() {
        return fuelConsumptionCollection;
    }

    public void setFuelConsumptionCollection(List<FuelComsumptionRate> fuelConsumptionCollection) {
        this.fuelConsumptionCollection = fuelConsumptionCollection;
    }

    public List<FuelLevel> getFuelLevelCollection() {
        return fuelLevelCollection;
    }

    public void setFuelLevelCollection(List<FuelLevel> fuelLevelCollection) {
        this.fuelLevelCollection = fuelLevelCollection;
    }

    public List<OilTemperature> getOilTemperatureCollection() {
        return oilTemperatureCollection;
    }

    public void setOilTemperatureCollection(List<OilTemperature> oilTemperatureCollection) {
        this.oilTemperatureCollection = oilTemperatureCollection;
    }
    
    

}
