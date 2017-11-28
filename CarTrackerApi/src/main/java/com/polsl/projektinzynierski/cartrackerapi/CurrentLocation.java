/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.projektinzynierski.cartrackerapi;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author m_lig
 */
@Entity
@Table(name = "currentLocation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CurrentLocation.findAll", query = "SELECT c FROM CurrentLocation c")
    , @NamedQuery(name = "CurrentLocation.findByIdcurrentLocation", query = "SELECT c FROM CurrentLocation c WHERE c.idcurrentLocation = :idcurrentLocation")
    , @NamedQuery(name = "CurrentLocation.findByLatitude", query = "SELECT c FROM CurrentLocation c WHERE c.latitude = :latitude")
    , @NamedQuery(name = "CurrentLocation.findByLongitude", query = "SELECT c FROM CurrentLocation c WHERE c.longitude = :longitude")
    , @NamedQuery(name = "CurrentLocation.findByTimestamp", query = "SELECT c FROM CurrentLocation c WHERE c.timestamp = :timestamp")})
public class CurrentLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcurrentLocation")
    private Integer idcurrentLocation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private double latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private double longitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    private long timestamp;
    @JoinColumn(name = "car_idCar", referencedColumnName = "idCar")
    @ManyToOne
    private Car caridCar;
    @JoinColumn(name = "user_idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User useridUser;

    public CurrentLocation() {
    }

    public CurrentLocation(Integer idcurrentLocation) {
        this.idcurrentLocation = idcurrentLocation;
    }

    public CurrentLocation(Integer idcurrentLocation, double latitude, double longitude, long timestamp) {
        this.idcurrentLocation = idcurrentLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public Integer getIdcurrentLocation() {
        return idcurrentLocation;
    }

    public void setIdcurrentLocation(Integer idcurrentLocation) {
        this.idcurrentLocation = idcurrentLocation;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Car getCaridCar() {
        return caridCar;
    }

    public void setCaridCar(Car caridCar) {
        this.caridCar = caridCar;
    }

    public User getUseridUser() {
        return useridUser;
    }

    public void setUseridUser(User useridUser) {
        this.useridUser = useridUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcurrentLocation != null ? idcurrentLocation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurrentLocation)) {
            return false;
        }
        CurrentLocation other = (CurrentLocation) object;
        if ((this.idcurrentLocation == null && other.idcurrentLocation != null) || (this.idcurrentLocation != null && !this.idcurrentLocation.equals(other.idcurrentLocation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polsl.projektinzynierski.cartrackerapi.CurrentLocation[ idcurrentLocation=" + idcurrentLocation + " ]";
    }
    
}
