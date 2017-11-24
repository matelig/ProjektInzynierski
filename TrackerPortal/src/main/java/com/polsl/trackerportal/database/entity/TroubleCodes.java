/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@Entity
@Table(name = "troubleCodes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TroubleCodes.findAll", query = "SELECT t FROM TroubleCodes t")
    , @NamedQuery(name = "TroubleCodes.findByIdtroubleCodes", query = "SELECT t FROM TroubleCodes t WHERE t.idtroubleCodes = :idtroubleCodes")
    , @NamedQuery(name = "TroubleCodes.findByCode", query = "SELECT t FROM TroubleCodes t WHERE t.code = :code")
    , @NamedQuery(name = "TroubleCodes.findByTimestamp", query = "SELECT t FROM TroubleCodes t WHERE t.timestamp = :timestamp")
    , @NamedQuery(name = "TroubleCodes.findByState", query = "SELECT t FROM TroubleCodes t WHERE t.state = :state")})
public class TroubleCodes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtroubleCodes")
    private Integer idtroubleCodes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timestamp")
    private long timestamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "state")
    private int state;
    @JoinColumn(name = "route_idRoute", referencedColumnName = "idRoute")
    @ManyToOne(optional = false)
    private Route routeidRoute;

    public TroubleCodes() {
    }

    public TroubleCodes(Integer idtroubleCodes) {
        this.idtroubleCodes = idtroubleCodes;
    }

    public TroubleCodes(Integer idtroubleCodes, String code, long timestamp, int state) {
        this.idtroubleCodes = idtroubleCodes;
        this.code = code;
        this.timestamp = timestamp;
        this.state = state;
    }

    public Integer getIdtroubleCodes() {
        return idtroubleCodes;
    }

    public void setIdtroubleCodes(Integer idtroubleCodes) {
        this.idtroubleCodes = idtroubleCodes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
        hash += (idtroubleCodes != null ? idtroubleCodes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TroubleCodes)) {
            return false;
        }
        TroubleCodes other = (TroubleCodes) object;
        if ((this.idtroubleCodes == null && other.idtroubleCodes != null) || (this.idtroubleCodes != null && !this.idtroubleCodes.equals(other.idtroubleCodes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polsl.trackerportal.database.entity.TroubleCodes[ idtroubleCodes=" + idtroubleCodes + " ]";
    }

}
