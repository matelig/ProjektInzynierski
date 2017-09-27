/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.trackerportal.database.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author m_lig
 */
@Entity
@Table(name = "route")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Route.findAll", query = "SELECT r FROM Route r")
    , @NamedQuery(name = "Route.findByIdRoute", query = "SELECT r FROM Route r WHERE r.idRoute = :idRoute")
    , @NamedQuery(name = "Route.findByStartDate", query = "SELECT r FROM Route r WHERE r.startDate = :startDate")
    , @NamedQuery(name = "Route.findByEndDate", query = "SELECT r FROM Route r WHERE r.endDate = :endDate")})
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRoute")
    private Integer idRoute;
    @Column(name = "startDate")
    private BigInteger startDate;
    @Column(name = "endDate")
    private BigInteger endDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private List<OilTemperature> oilTemperatureList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private List<FuelLevel> fuelLevelList;
    @JoinColumn(name = "Car_vinNumber", referencedColumnName = "vinNumber")
    @ManyToOne(optional = false)
    private Car carvinNumber;
    @JoinColumn(name = "User_idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User useridUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private List<FuelComsumptionRate> fuelComsumptionRateList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private List<TroubleCodes> troubleCodesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private List<Location> locationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private List<Rpm> rpmList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private List<Speed> speedList;

    public Route() {
    }

    public Route(Integer idRoute) {
        this.idRoute = idRoute;
    }

    public Integer getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(Integer idRoute) {
        this.idRoute = idRoute;
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

    @XmlTransient
    public List<OilTemperature> getOilTemperatureList() {
        return oilTemperatureList;
    }

    public void setOilTemperatureList(List<OilTemperature> oilTemperatureList) {
        this.oilTemperatureList = oilTemperatureList;
    }

    @XmlTransient
    public List<FuelLevel> getFuelLevelList() {
        return fuelLevelList;
    }

    public void setFuelLevelList(List<FuelLevel> fuelLevelList) {
        this.fuelLevelList = fuelLevelList;
    }

    public Car getCarvinNumber() {
        return carvinNumber;
    }

    public void setCarvinNumber(Car carvinNumber) {
        this.carvinNumber = carvinNumber;
    }

    public User getUseridUser() {
        return useridUser;
    }

    public void setUseridUser(User useridUser) {
        this.useridUser = useridUser;
    }

    @XmlTransient
    public List<FuelComsumptionRate> getFuelComsumptionRateList() {
        return fuelComsumptionRateList;
    }

    public void setFuelComsumptionRateList(List<FuelComsumptionRate> fuelComsumptionRateList) {
        this.fuelComsumptionRateList = fuelComsumptionRateList;
    }

    @XmlTransient
    public List<TroubleCodes> getTroubleCodesList() {
        return troubleCodesList;
    }

    public void setTroubleCodesList(List<TroubleCodes> troubleCodesList) {
        this.troubleCodesList = troubleCodesList;
    }

    @XmlTransient
    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    @XmlTransient
    public List<Rpm> getRpmList() {
        return rpmList;
    }

    public void setRpmList(List<Rpm> rpmList) {
        this.rpmList = rpmList;
    }

    @XmlTransient
    public List<Speed> getSpeedList() {
        return speedList;
    }

    public void setSpeedList(List<Speed> speedList) {
        this.speedList = speedList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRoute != null ? idRoute.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Route)) {
            return false;
        }
        Route other = (Route) object;
        if ((this.idRoute == null && other.idRoute != null) || (this.idRoute != null && !this.idRoute.equals(other.idRoute))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polsl.trackerportal.database.entity.Route[ idRoute=" + idRoute + " ]";
    }
    
}
