/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.trackerportal.database.entity;

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
@Table(name = "oilTemperature")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OilTemperature.findAll", query = "SELECT o FROM OilTemperature o")
    , @NamedQuery(name = "OilTemperature.findByIdoilTemperature", query = "SELECT o FROM OilTemperature o WHERE o.idoilTemperature = :idoilTemperature")
    , @NamedQuery(name = "OilTemperature.findByValue", query = "SELECT o FROM OilTemperature o WHERE o.value = :value")
    , @NamedQuery(name = "OilTemperature.findByTimestamp", query = "SELECT o FROM OilTemperature o WHERE o.timestamp = :timestamp")})
public class OilTemperature implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idoilTemperature")
    private Integer idoilTemperature;
    @Basic(optional = false)
    @NotNull
    @Column(name = "value")
    private double value;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    private Long timestamp;
    @JoinColumn(name = "route_idRoute", referencedColumnName = "idRoute")
    @ManyToOne(optional = false)
    private Route routeidRoute;

    public OilTemperature() {
    }

    public OilTemperature(Integer idoilTemperature) {
        this.idoilTemperature = idoilTemperature;
    }

    public OilTemperature(Integer idoilTemperature, double value, long timestamp) {
        this.idoilTemperature = idoilTemperature;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Integer getIdoilTemperature() {
        return idoilTemperature;
    }

    public void setIdoilTemperature(Integer idoilTemperature) {
        this.idoilTemperature = idoilTemperature;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Route getRouteidRoute() {
        return routeidRoute;
    }

    public void setRouteidRoute(Route routeidRoute) {
        this.routeidRoute = routeidRoute;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idoilTemperature != null ? idoilTemperature.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OilTemperature)) {
            return false;
        }
        OilTemperature other = (OilTemperature) object;
        if ((this.idoilTemperature == null && other.idoilTemperature != null) || (this.idoilTemperature != null && !this.idoilTemperature.equals(other.idoilTemperature))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polsl.trackerportal.database.entity.OilTemperature[ idoilTemperature=" + idoilTemperature + " ]";
    }
    
}
