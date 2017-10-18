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
import java.util.Date;
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
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;
import pl.spiid.lib.spiidcharts.models.GanttAmChartModel;
import pl.spiid.lib.spiidcharts.models.SerialAmChartModel;

/**
 *
 * @author m_lig
 */
@ManagedBean(name = "routeDetails", eager = true)
@ViewScoped
public class RouteDetails implements Serializable {

    private SerialAmChartModel speedChartModel;

    private GanttAmChartModel troubleCodesChartModel;

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
    private List<TroubleCodes> troubleCodes;
    private Route route;
    private Car car;
    private String centerOfMap;

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
        speedChartModel = ChartModeler.initSpeedModel(speedList);

        troubleCodesChartModel = ChartModeler.initTroubleCodesChartModel(new Date(route.getStartDate().longValue()),troubleCodes);

        rpmChartModel = ChartModeler.initRPMModel(RPMList);
        rpmChartModel.setTitle("RPM");
        rpmChartModel.setLegendPosition("e");
        rpmChartModel.setZoom(true);
        Axis xxAxis = rpmChartModel.getAxis(AxisType.X);
        xxAxis.setTickCount(10);

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

    public SerialAmChartModel getSpeedChartModel() {
        return speedChartModel;
    }

    public void setSpeedChartModel(SerialAmChartModel speedChartModel) {
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

    public String getCenterOfMap() {
        return centerOfMap;
    }

    public void setCenterOfMap(String centerOfMap) {
        this.centerOfMap = centerOfMap;
    }

    public GanttAmChartModel getTroubleCodesChartModel() {
        return troubleCodesChartModel;
    }

    public void setTroubleCodesChartModel(GanttAmChartModel troubleCodesChartModel) {
        this.troubleCodesChartModel = troubleCodesChartModel;
    }

}
