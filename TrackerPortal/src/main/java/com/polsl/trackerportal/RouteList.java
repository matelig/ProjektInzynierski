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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author m_lig
 */
@ManagedBean(name = "routeList")
@SessionScoped
public class RouteList implements Serializable {

    private List<Route> routeList;
    @PersistenceContext
    private EntityManager entityManager;

    @ManagedProperty("#{loggedUser}")
    private LoggedUser loggedUser;

    @PostConstruct
    public void init() {
        routeList = entityManager.createNamedQuery("Route.findAll").getResultList();
        for (int i = routeList.size() - 1; i >= 0; i--) {
            if (!routeList.get(i).getUseridUser().getPesel().equals(loggedUser.getPesel())) {
                routeList.remove(i);
            }
        }
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

}
