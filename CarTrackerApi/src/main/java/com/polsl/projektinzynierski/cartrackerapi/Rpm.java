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
@Table(name = "rpm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rpm.findAll", query = "SELECT r FROM Rpm r")
    , @NamedQuery(name = "Rpm.findByIdrpm", query = "SELECT r FROM Rpm r WHERE r.idrpm = :idrpm")
    , @NamedQuery(name = "Rpm.findByValue", query = "SELECT r FROM Rpm r WHERE r.value = :value")
    , @NamedQuery(name = "Rpm.findByTimestamp", query = "SELECT r FROM Rpm r WHERE r.timestamp = :timestamp")})
public class Rpm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrpm")
    private Integer idrpm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "value")
    private int value;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    private long timestamp;
    @JoinColumn(name = "route_idRoute", referencedColumnName = "idRoute")
    @ManyToOne(optional = false)
    private Route routeidRoute;

    public Rpm() {
    }

    public Rpm(Integer idrpm) {
        this.idrpm = idrpm;
    }

    public Rpm(Integer idrpm, int value, long timestamp) {
        this.idrpm = idrpm;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Integer getIdrpm() {
        return idrpm;
    }

    public void setIdrpm(Integer idrpm) {
        this.idrpm = idrpm;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
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
        hash += (idrpm != null ? idrpm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rpm)) {
            return false;
        }
        Rpm other = (Rpm) object;
        if ((this.idrpm == null && other.idrpm != null) || (this.idrpm != null && !this.idrpm.equals(other.idrpm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polsl.projektinzynierski.cartrackerapi.Rpm[ idrpm=" + idrpm + " ]";
    }

}
