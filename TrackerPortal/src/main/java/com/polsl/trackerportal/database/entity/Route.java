/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.trackerportal.database.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
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
    , @NamedQuery(name = "Route.findByEndDate", query = "SELECT r FROM Route r WHERE r.endDate = :endDate")
    , @NamedQuery(name = "Route.findByRoadLength", query = "SELECT r FROM Route r WHERE r.roadLength = :roadLength")})
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "roadLength")
    private double roadLength;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private Collection<FuelLevel> fuelLevelCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private Collection<TroubleCodes> troubleCodesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private Collection<Rpm> rpmCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private Collection<Speed> speedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private Collection<OilTemperature> oilTemperatureCollection;
    @JoinColumn(name = "User_idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User useridUser;
    @JoinColumn(name = "car_idCar", referencedColumnName = "idCar")
    @ManyToOne
    private Car caridCar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private Collection<FuelComsumptionRate> fuelComsumptionRateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private Collection<EngineLoad> engineLoadCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeidRoute")
    private Collection<Location> locationCollection;

    public Route() {
    }

    public Route(Integer idRoute) {
        this.idRoute = idRoute;
    }

    public Route(Integer idRoute, double roadLength) {
        this.idRoute = idRoute;
        this.roadLength = roadLength;
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

    public double getRoadLength() {
        return roadLength;
    }

    public void setRoadLength(double roadLength) {
        this.roadLength = roadLength;
    }

    @XmlTransient
    public Collection<FuelLevel> getFuelLevelCollection() {
        return fuelLevelCollection;
    }

    public void setFuelLevelCollection(Collection<FuelLevel> fuelLevelCollection) {
        this.fuelLevelCollection = fuelLevelCollection;
    }

    @XmlTransient
    public Collection<TroubleCodes> getTroubleCodesCollection() {
        return troubleCodesCollection;
    }

    public void setTroubleCodesCollection(Collection<TroubleCodes> troubleCodesCollection) {
        this.troubleCodesCollection = troubleCodesCollection;
    }

    @XmlTransient
    public Collection<Rpm> getRpmCollection() {
        return rpmCollection;
    }

    public void setRpmCollection(Collection<Rpm> rpmCollection) {
        this.rpmCollection = rpmCollection;
    }

    @XmlTransient
    public Collection<Speed> getSpeedCollection() {
        return speedCollection;
    }

    public void setSpeedCollection(Collection<Speed> speedCollection) {
        this.speedCollection = speedCollection;
    }

    @XmlTransient
    public Collection<OilTemperature> getOilTemperatureCollection() {
        return oilTemperatureCollection;
    }

    public void setOilTemperatureCollection(Collection<OilTemperature> oilTemperatureCollection) {
        this.oilTemperatureCollection = oilTemperatureCollection;
    }

    public User getUseridUser() {
        return useridUser;
    }

    public void setUseridUser(User useridUser) {
        this.useridUser = useridUser;
    }

    public Car getCaridCar() {
        return caridCar;
    }

    public void setCaridCar(Car caridCar) {
        this.caridCar = caridCar;
    }

    @XmlTransient
    public Collection<FuelComsumptionRate> getFuelComsumptionRateCollection() {
        return fuelComsumptionRateCollection;
    }

    public void setFuelComsumptionRateCollection(Collection<FuelComsumptionRate> fuelComsumptionRateCollection) {
        this.fuelComsumptionRateCollection = fuelComsumptionRateCollection;
    }

    @XmlTransient
    public Collection<EngineLoad> getEngineLoadCollection() {
        return engineLoadCollection;
    }

    public void setEngineLoadCollection(Collection<EngineLoad> engineLoadCollection) {
        this.engineLoadCollection = engineLoadCollection;
    }

    @XmlTransient
    public Collection<Location> getLocationCollection() {
        return locationCollection;
    }

    public void setLocationCollection(Collection<Location> locationCollection) {
        this.locationCollection = locationCollection;
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
