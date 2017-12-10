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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author m_lig
 */
@Entity
@Table(name = "troubleCodesNames")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TroubleCodesNames.findAll", query = "SELECT t FROM TroubleCodesNames t")
    , @NamedQuery(name = "TroubleCodesNames.findByIdtroubleCodeName", query = "SELECT t FROM TroubleCodesNames t WHERE t.idtroubleCodeName = :idtroubleCodeName")
    , @NamedQuery(name = "TroubleCodesNames.findByCode", query = "SELECT t FROM TroubleCodesNames t WHERE t.code = :code")
    , @NamedQuery(name = "TroubleCodesNames.findByDescription", query = "SELECT t FROM TroubleCodesNames t WHERE t.description = :description")})
public class TroubleCodesNames implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtroubleCodeName")
    private Integer idtroubleCodeName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;

    public TroubleCodesNames() {
    }

    public TroubleCodesNames(Integer idtroubleCodeName) {
        this.idtroubleCodeName = idtroubleCodeName;
    }

    public TroubleCodesNames(Integer idtroubleCodeName, String code, String description) {
        this.idtroubleCodeName = idtroubleCodeName;
        this.code = code;
        this.description = description;
    }

    public Integer getIdtroubleCodeName() {
        return idtroubleCodeName;
    }

    public void setIdtroubleCodeName(Integer idtroubleCodeName) {
        this.idtroubleCodeName = idtroubleCodeName;
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
        hash += (idtroubleCodeName != null ? idtroubleCodeName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TroubleCodesNames)) {
            return false;
        }
        TroubleCodesNames other = (TroubleCodesNames) object;
        if ((this.idtroubleCodeName == null && other.idtroubleCodeName != null) || (this.idtroubleCodeName != null && !this.idtroubleCodeName.equals(other.idtroubleCodeName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.polsl.trackerportal.database.entity.TroubleCodesNames[ idtroubleCodeName=" + idtroubleCodeName + " ]";
    }
    
}
