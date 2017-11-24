/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
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
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@Entity
@Table(name = "fuelComsumptionRate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FuelComsumptionRate.findAll", query = "SELECT f FROM FuelComsumptionRate f")
    , @NamedQuery(name = "FuelComsumptionRate.findByIdfuelComsumptionRate", query = "SELECT f FROM FuelComsumptionRate f WHERE f.idfuelComsumptionRate = :idfuelComsumptionRate")
    , @NamedQuery(name = "FuelComsumptionRate.findByValue", query = "SELECT f FROM FuelComsumptionRate f WHERE f.value = :value")
    , @NamedQuery(name = "FuelComsumptionRate.findByTimestamp", query = "SELECT f FROM FuelComsumptionRate f WHERE f.timestamp = :timestamp")})
public class FuelComsumptionRate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfuelComsumptionRate")
    private Integer idfuelComsumptionRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "value")
    private double value;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    private long timestamp;
    @JoinColumn(name = "route_idRoute", referencedColumnName = "idRoute")
    @ManyToOne(optional = false)
    private Route routeidRoute;

    public FuelComsumptionRate() {
    }

    public FuelComsumptionRate(Integer idfuelComsumptionRate) {
        this.idfuelComsumptionRate = idfuelComsumptionRate;
    }

    public FuelComsumptionRate(Integer idfuelComsumptionRate, double value, long timestamp) {
        this.idfuelComsumptionRate = idfuelComsumptionRate;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Integer getIdfuelComsumptionRate() {
        return idfuelComsumptionRate;
    }

    public void setIdfuelComsumptionRate(Integer idfuelComsumptionRate) {
        this.idfuelComsumptionRate = idfuelComsumptionRate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
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
        hash += (idfuelComsumptionRate != null ? idfuelComsumptionRate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuelComsumptionRate)) {
            return false;
        }
        FuelComsumptionRate other = (FuelComsumptionRate) object;
        if ((this.idfuelComsumptionRate == null && other.idfuelComsumptionRate != null) || (this.idfuelComsumptionRate != null && !this.idfuelComsumptionRate.equals(other.idfuelComsumptionRate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polsl.projektinzynierski.cartrackerapi.FuelComsumptionRate[ idfuelComsumptionRate=" + idfuelComsumptionRate + " ]";
    }

}
