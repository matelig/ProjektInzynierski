/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
*/

package com.polsl.trackerportal;

import com.polsl.trackerportal.database.entity.CurrentLocation;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@ManagedBean(name = "lastKnowLocations", eager = true)
@ViewScoped
public class LastKnowLocations implements Serializable{
    List<CurrentLocation> currentLocationList;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource
    private UserTransaction userTransaction;
    
    @PostConstruct
    public void init() {
        updateLocationList();
    }
    
    public void updateLocationList() {          
        entityManager.getEntityManagerFactory().getCache().evictAll();
        currentLocationList = entityManager.createNamedQuery("CurrentLocation.findAll").getResultList();      
        
    }
    


    public List<CurrentLocation> getCurrentLocationList() {
        return currentLocationList;
    }

    public void setCurrentLocationList(List<CurrentLocation> currentLocationList) {
        this.currentLocationList = currentLocationList;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    
}
