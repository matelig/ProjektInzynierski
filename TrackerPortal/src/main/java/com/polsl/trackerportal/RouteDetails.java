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
import com.polsl.trackerportal.util.ChartModeler;
import com.polsl.trackerportal.util.LoggedUser;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;

/**
 *
 * @author m_lig
 */
@ManagedBean(name = "routeDetails")
@ViewScoped
public class RouteDetails implements Serializable{

    private LineChartModel speedChartModel;

    private LineChartModel rpmChartModel;

    private MapModel mapModel;

    @PersistenceContext
    private EntityManager entityManager;

    @ManagedProperty("#{loggedUser}")
    private LoggedUser loggedUser;

    private String routeId;

    private List<Speed> speedList;
    private List<Location> locationList;
    private List<Rpm> RPMList;
    private Route route;
    private Car car;

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
//       lineModel1 = initLinearModel();
//        lineModel1.setTitle("Linear Chart");
//        lineModel1.setLegendPosition("e");
//        Axis yAxis = lineModel1.getAxis(AxisType.Y);
//        yAxis.setMin(0);
//        yAxis.setMax(10);
        speedChartModel = ChartModeler.initSpeedModel(speedList);
        speedChartModel.setTitle("Speed");
        speedChartModel.setLegendPosition("e");
        Axis xAxis = speedChartModel.getAxis(AxisType.X);
        xAxis.setTickCount(10);

        rpmChartModel = ChartModeler.initRPMModel(RPMList);
        rpmChartModel.setTitle("RPM");
        rpmChartModel.setLegendPosition("e");
        Axis xxAxis = rpmChartModel.getAxis(AxisType.X);
        xxAxis.setTickCount(10);

    }

    private void createMapModel() {
        mapModel = new DefaultMapModel();
        Polyline polyline = new Polyline();
        for (Location l : locationList) {
            LatLng coord = new LatLng(l.getLatitude(), l.getLongitude());
            polyline.getPaths().add(coord);
        }
        polyline.setStrokeWeight(10);
        polyline.setStrokeColor("#FF9900");
        polyline.setStrokeOpacity(0.7);
        mapModel.addOverlay(polyline);
    }

    private void initData() {
        route = (Route) entityManager.createNamedQuery("Route.findByIdRoute").setParameter("idRoute", Integer.valueOf(routeId)).getSingleResult();
        //route = temp.get(0);
        speedList = route.getSpeedList();
        locationList = route.getLocationList();
        RPMList = route.getRpmList();
        if (locationList.size() > 0) {
            Collections.sort(locationList,new Comparator<Location>() {
                @Override
                public int compare(Location o1, Location o2) {
                    return o1.getTimestamp().compareTo(o2.getTimestamp());
                }
            });
            }


    }

    public LineChartModel getSpeedChartModel() {
        return speedChartModel;
    }

    public void setSpeedChartModel(LineChartModel speedChartModel) {
        this.speedChartModel = speedChartModel;
    }

    public LineChartModel getRpmChartModel() {
        return rpmChartModel;
    }

    public void setRpmChartModel(LineChartModel rpmChartModel) {
        this.rpmChartModel = rpmChartModel;
    }

    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }

}
