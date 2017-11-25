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
@Table(name = "troubleCodesNames")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TroubleCodesNames.findAll", query = "SELECT t FROM TroubleCodesNames t")
    , @NamedQuery(name = "TroubleCodesNames.findByIdTroubleCodesNames", query = "SELECT t FROM TroubleCodesNames t WHERE t.idTroubleCodesNames = :idTroubleCodesNames")
    , @NamedQuery(name = "TroubleCodesNames.findByCode", query = "SELECT t FROM TroubleCodesNames t WHERE t.code = :code")
    , @NamedQuery(name = "TroubleCodesNames.findByDescription", query = "SELECT t FROM TroubleCodesNames t WHERE t.description = :description")})
public class TroubleCodesNames implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTroubleCodesNames")
    private Integer idTroubleCodesNames;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 10)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;

    public TroubleCodesNames() {
    }

    public TroubleCodesNames(Integer idTroubleCodesNames) {
        this.idTroubleCodesNames = idTroubleCodesNames;
    }

    public TroubleCodesNames(Integer idTroubleCodesNames, String code, String description) {
        this.idTroubleCodesNames = idTroubleCodesNames;
        this.code = code;
        this.description = description;
    }

    public Integer getIdTroubleCodesNames() {
        return idTroubleCodesNames;
    }

    public void setIdTroubleCodesNames(Integer idTroubleCodesNames) {
        this.idTroubleCodesNames = idTroubleCodesNames;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTroubleCodesNames != null ? idTroubleCodesNames.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TroubleCodesNames)) {
            return false;
        }
        TroubleCodesNames other = (TroubleCodesNames) object;
        if ((this.idTroubleCodesNames == null && other.idTroubleCodesNames != null) || (this.idTroubleCodesNames != null && !this.idTroubleCodesNames.equals(other.idTroubleCodesNames))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polsl.projektinzynierski.cartrackerapi.TroubleCodesNames[ idTroubleCodesNames=" + idTroubleCodesNames + " ]";
    }

}
