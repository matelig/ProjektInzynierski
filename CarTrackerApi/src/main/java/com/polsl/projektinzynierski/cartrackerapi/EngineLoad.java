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
@Table(name = "engineLoad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EngineLoad.findAll", query = "SELECT e FROM EngineLoad e")
    , @NamedQuery(name = "EngineLoad.findByIdengineLoad", query = "SELECT e FROM EngineLoad e WHERE e.idengineLoad = :idengineLoad")
    , @NamedQuery(name = "EngineLoad.findByValue", query = "SELECT e FROM EngineLoad e WHERE e.value = :value")
    , @NamedQuery(name = "EngineLoad.findByTimestamp", query = "SELECT e FROM EngineLoad e WHERE e.timestamp = :timestamp")})
public class EngineLoad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idengineLoad")
    private Integer idengineLoad;
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

    public EngineLoad() {
    }

    public EngineLoad(Integer idengineLoad) {
        this.idengineLoad = idengineLoad;
    }

    public EngineLoad(Integer idengineLoad, double value, long timestamp) {
        this.idengineLoad = idengineLoad;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Integer getIdengineLoad() {
        return idengineLoad;
    }

    public void setIdengineLoad(Integer idengineLoad) {
        this.idengineLoad = idengineLoad;
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
        hash += (idengineLoad != null ? idengineLoad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EngineLoad)) {
            return false;
        }
        EngineLoad other = (EngineLoad) object;
        if ((this.idengineLoad == null && other.idengineLoad != null) || (this.idengineLoad != null && !this.idengineLoad.equals(other.idengineLoad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polsl.projektinzynierski.cartrackerapi.EngineLoad[ idengineLoad=" + idengineLoad + " ]";
    }

}
