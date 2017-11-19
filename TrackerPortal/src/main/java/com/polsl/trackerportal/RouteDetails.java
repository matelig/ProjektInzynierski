/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.trackerportal;

import com.polsl.trackerportal.database.entity.Car;
import com.polsl.trackerportal.database.entity.Location;
import com.polsl.trackerportal.database.entity.Route;
import com.polsl.trackerportal.database.entity.Rpm;
import com.polsl.trackerportal.database.entity.Speed;
import com.polsl.trackerportal.database.entity.TroubleCodes;
import com.polsl.trackerportal.util.ChartModeler;
import com.polsl.trackerportal.util.LoggedUser;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.json.JSONArray;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;

/**
 *
 * @author m_lig
 */
@ManagedBean(name = "routeDetails", eager = true)
@ViewScoped
public class RouteDetails implements Serializable {
    private MapModel mapModel;

    @PersistenceContext
    private EntityManager entityManager;

    @ManagedProperty("#{loggedUser}")
    private LoggedUser loggedUser;

    private String routeId;

    private List<Speed> speedList;
    private List<Location> locationList;
    private List<Rpm> RPMList;
    private List<TroubleCodes> troubleCodes;
    private Route route;
    private Car car;
    private String centerOfMap;

    private JSONArray speedProvider;
    private int speedChartSeries;

    private JSONArray rpmProvider;
    private int rpmChartSeries;
    
    private JSONArray troubleCodesProvider;

    @PostConstruct
    public void init() {
        routeId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("routeID");
        initData();
        prepareCharts();
        createMapModel();
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

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    private void prepareCharts() {
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (speedList.size() > 0) {
            Collections.sort(speedList, new Comparator<Speed>() {
                @Override
                public int compare(Speed t, Speed t1) {
                    return t.getTimestamp().compareTo(t1.getTimestamp());
                }
            });
        }
        if (RPMList.size() > 0) {
            Collections.sort(RPMList, new Comparator<Rpm>() {
                @Override
                public int compare(Rpm t, Rpm t1) {
                    return t.getTimestamp().compareTo(t1.getTimestamp());
                }
            });
        }      
        speedProvider = ChartModeler.initSpeedProvider(speedList);
        speedChartSeries = ChartModeler.SERIES_COUNT;

        rpmProvider = ChartModeler.initRPMProvider(RPMList);
        rpmChartSeries = ChartModeler.SERIES_COUNT;
        
        troubleCodesProvider = ChartModeler.initTroubleCodesProvider(troubleCodes);
    }

    private void createMapModel() {
        mapModel = new DefaultMapModel();
        Polyline polyline = new Polyline();
        for (int i = 0; i < locationList.size(); i++) {
            LatLng coord = new LatLng(locationList.get(i).getLatitude(), locationList.get(i).getLongitude());
            polyline.getPaths().add(coord);
        }
        polyline.setStrokeWeight(10);
        polyline.setStrokeColor("#FF9900");
        polyline.setStrokeOpacity(0.7);
        Point2D.Double center = calculateCentroid(polyline.getPaths());
        centerOfMap = center.getX() + "," + center.getY();
        mapModel.addOverlay(polyline);

    }

    private void initData() {
        route = (Route) entityManager.createNamedQuery("Route.findByIdRoute").setParameter("idRoute", Integer.valueOf(routeId)).getSingleResult();
        //route = temp.get(0);
        speedList = new ArrayList<>(route.getSpeedCollection());
        locationList = new ArrayList<>(route.getLocationCollection());
        RPMList = new ArrayList(route.getRpmCollection());
        troubleCodes = new ArrayList<>(route.getTroubleCodesCollection());
        if (locationList.size() > 0) {
            Collections.sort(locationList, new Comparator<Location>() {
                @Override
                public int compare(Location o1, Location o2) {
                    return o1.getTimestamp().compareTo(o2.getTimestamp());
                }
            });
        }
    }

    public Point2D.Double calculateCentroid(List<LatLng> points) {
        double x = 0.;
        double y = 0.;
        double pointCount = points.size();
        for (int i = 0; i < pointCount; i++) {
            x += points.get(i).getLat();
            y += points.get(i).getLng();
        }

        x = x / pointCount;
        y = y / pointCount;

        return new Point2D.Double(x, y);
    }
 
    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public String getCenterOfMap() {
        return centerOfMap;
    }

    public void setCenterOfMap(String centerOfMap) {
        this.centerOfMap = centerOfMap;
    }  

    public JSONArray getSpeedProvider() {
        return speedProvider;
    }

    public void setSpeedProvider(JSONArray speedProvider) {
        this.speedProvider = speedProvider;
    }

    public int getSpeedChartSeries() {
        return speedChartSeries;
    }

    public void setSpeedChartSeries(int speedChartSeries) {
        this.speedChartSeries = speedChartSeries;
    }

    public JSONArray getRpmProvider() {
        return rpmProvider;
    }

    public void setRpmProvider(JSONArray rpmProvider) {
        this.rpmProvider = rpmProvider;
    }

    public int getRpmChartSeries() {
        return rpmChartSeries;
    }

    public void setRpmChartSeries(int rpmChartSeries) {
        this.rpmChartSeries = rpmChartSeries;
    }

    public JSONArray getTroubleCodesProvider() {
        return troubleCodesProvider;
    }

    public void setTroubleCodesProvider(JSONArray troubleCodesProvider) {
        this.troubleCodesProvider = troubleCodesProvider;
    }    
    
}
