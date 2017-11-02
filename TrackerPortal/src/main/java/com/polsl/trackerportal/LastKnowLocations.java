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
import org.primefaces.context.RequestContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@ManagedBean(name = "lastKnowLocations", eager = true)
@ViewScoped
public class LastKnowLocations implements Serializable {

    private List<CurrentLocation> currentLocationList;

    private CurrentLocation selectedUserLocation;
    private String centerOfMap;
    private DefaultMapModel mapModel;

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

    public void showLocationDialog(CurrentLocation location) {
        this.selectedUserLocation = location;
        this.centerOfMap = location.getLatitude() + "," + location.getLongitude();
        if (mapModel != null) {
            mapModel.getMarkers().clear();
        }
        initMapModel();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('locationDialog').show();");
    }

    private void initMapModel() {
        mapModel = new DefaultMapModel();
        LatLng latlng = new LatLng(selectedUserLocation.getLatitude(), selectedUserLocation.getLongitude());
        mapModel.addOverlay(new Marker(latlng));
    }

    public void updateMarkerLocation() {
        if (selectedUserLocation != null) {
            entityManager.getEntityManagerFactory().getCache().evictAll();
            selectedUserLocation = (CurrentLocation) entityManager
                    .createNamedQuery("CurrentLocation.findByIdcurrentLocation")
                    .setParameter("idcurrentLocation", selectedUserLocation.getIdcurrentLocation())
                    .getSingleResult();
            mapModel.getMarkers().clear();
            centerOfMap = selectedUserLocation.getLatitude() + "," + selectedUserLocation.getLongitude();
            LatLng latlng = new LatLng(selectedUserLocation.getLatitude(), selectedUserLocation.getLongitude());
            mapModel.addOverlay(new Marker(latlng));
        }
    }

    private void closeAddDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('locationDialog').hide();");
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

    public CurrentLocation getSelectedUserLocation() {
        return selectedUserLocation;
    }

    public void setSelectedUserLocation(CurrentLocation selectedUserLocation) {
        this.selectedUserLocation = selectedUserLocation;
    }

    public String getCenterOfMap() {
        return centerOfMap;
    }

    public void setCenterOfMap(String centerOfMap) {
        this.centerOfMap = centerOfMap;
    }

    public DefaultMapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel(DefaultMapModel mapModel) {
        this.mapModel = mapModel;
    }

}
