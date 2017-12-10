/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.trackerportal;

import com.polsl.trackerportal.database.entity.Location;
import com.polsl.trackerportal.database.entity.Route;
import com.polsl.trackerportal.util.LoggedUser;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author m_lig
 */
@ManagedBean(name = "routeList")
@ViewScoped
public class RouteList implements Serializable {

    private List<Route> routeList;
    @PersistenceContext
    private EntityManager entityManager;
    private int clickedRouteId;

    @ManagedProperty("#{loggedUser}")
    private LoggedUser loggedUser;

    @PostConstruct
    public void init() {        
        routeList = entityManager.createNamedQuery("Route.findAll").getResultList();
        String userID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID");
        if (userID == null || userID.isEmpty()) {
            String carID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("carID");
            if (carID == null || carID.isEmpty()) {
                for (int i = routeList.size() - 1; i >= 0; i--) {
                    if (!routeList.get(i).getUseridUser().getLogin().equals(loggedUser.getPesel())) {
                        routeList.remove(i);
                    }
                }
            } else {
                //pobieramy po aucie
                Long id = Long.valueOf(carID);
                for (int i = routeList.size() - 1; i >= 0; i--) {
                    if (routeList.get(i).getCaridCar()==null || !routeList.get(i).getCaridCar().getIdCar().equals(id)) {
                        routeList.remove(i);
                    }
                }
            }
        } else {
            //pobieramy po ID jakiegos usera
            int id = Integer.valueOf(userID);
            for (int i = routeList.size() - 1; i >= 0; i--) {
                if (!routeList.get(i).getUseridUser().getIdUser().equals(id)) {
                    routeList.remove(i);
                }
            }
        }

    }

    public String showRouteDetails(Route route) {
        clickedRouteId = route.getIdRoute();
        return "route-details.xhtml?faces-redirect=true&includeViewParams=true";
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public LoggedUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    public int getClickedRouteId() {
        return clickedRouteId;
    }

    public void setClickedRouteId(int clickedRouteId) {
        this.clickedRouteId = clickedRouteId;
    }

}
