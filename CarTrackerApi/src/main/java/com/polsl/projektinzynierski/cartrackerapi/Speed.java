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
@Table(name = "speed")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Speed.findAll", query = "SELECT s FROM Speed s")
    , @NamedQuery(name = "Speed.findByIdSpeed", query = "SELECT s FROM Speed s WHERE s.idSpeed = :idSpeed")
    , @NamedQuery(name = "Speed.findByTimestamp", query = "SELECT s FROM Speed s WHERE s.timestamp = :timestamp")
    , @NamedQuery(name = "Speed.findByValue", query = "SELECT s FROM Speed s WHERE s.value = :value")})
public class Speed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSpeed")
    private Integer idSpeed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    private long timestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "value")
    private double value;
    @JoinColumn(name = "route_idRoute", referencedColumnName = "idRoute")
    @ManyToOne(optional = false)
    private Route routeidRoute;

    public Speed() {
    }

    public Speed(Integer idSpeed) {
        this.idSpeed = idSpeed;
    }

    public Speed(Integer idSpeed, long timestamp, double value) {
        this.idSpeed = idSpeed;
        this.timestamp = timestamp;
        this.value = value;
    }

    public Integer getIdSpeed() {
        return idSpeed;
    }

    public void setIdSpeed(Integer idSpeed) {
        this.idSpeed = idSpeed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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
        hash += (idSpeed != null ? idSpeed.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Speed)) {
            return false;
        }
        Speed other = (Speed) object;
        if ((this.idSpeed == null && other.idSpeed != null) || (this.idSpeed != null && !this.idSpeed.equals(other.idSpeed))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polsl.projektinzynierski.cartrackerapi.Speed[ idSpeed=" + idSpeed + " ]";
    }
    
}
