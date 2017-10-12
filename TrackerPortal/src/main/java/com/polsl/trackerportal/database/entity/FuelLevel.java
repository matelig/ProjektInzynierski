/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
*/

package com.polsl.trackerportal.database.entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@Entity
@Table(name = "fuelLevel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FuelLevel.findAll", query = "SELECT f FROM FuelLevel f")
    , @NamedQuery(name = "FuelLevel.findByIdfuelLevel", query = "SELECT f FROM FuelLevel f WHERE f.idfuelLevel = :idfuelLevel")
    , @NamedQuery(name = "FuelLevel.findByValue", query = "SELECT f FROM FuelLevel f WHERE f.value = :value")
    , @NamedQuery(name = "FuelLevel.findByTimestamp", query = "SELECT f FROM FuelLevel f WHERE f.timestamp = :timestamp")})
public class FuelLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idfuelLevel")
    private Integer idfuelLevel;
    @Column(name = "value")
    private Integer value;
    @Column(name = "timestamp")
    private BigInteger timestamp;
    @JoinColumn(name = "route_idRoute", referencedColumnName = "idRoute")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Route routeidRoute;

    public FuelLevel() {
    }

    public FuelLevel(Integer idfuelLevel) {
        this.idfuelLevel = idfuelLevel;
    }

    public Integer getIdfuelLevel() {
        return idfuelLevel;
    }

    public void setIdfuelLevel(Integer idfuelLevel) {
        this.idfuelLevel = idfuelLevel;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public BigInteger getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(BigInteger timestamp) {
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
        hash += (idfuelLevel != null ? idfuelLevel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuelLevel)) {
            return false;
        }
        FuelLevel other = (FuelLevel) object;
        if ((this.idfuelLevel == null && other.idfuelLevel != null) || (this.idfuelLevel != null && !this.idfuelLevel.equals(other.idfuelLevel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polsl.trackerportal.database.entity.FuelLevel[ idfuelLevel=" + idfuelLevel + " ]";
    }

}
