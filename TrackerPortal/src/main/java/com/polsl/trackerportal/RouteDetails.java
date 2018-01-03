/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.trackerportal;

import com.polsl.trackerportal.database.entity.Car;
import com.polsl.trackerportal.database.entity.FuelComsumptionRate;
import com.polsl.trackerportal.database.entity.FuelLevel;
import com.polsl.trackerportal.database.entity.Location;
import com.polsl.trackerportal.database.entity.OilTemperature;
import com.polsl.trackerportal.database.entity.Route;
import com.polsl.trackerportal.database.entity.Rpm;
import com.polsl.trackerportal.database.entity.Speed;
import com.polsl.trackerportal.database.entity.TroubleCodes;
import com.polsl.trackerportal.database.entity.TroubleCodesNames;
import com.polsl.trackerportal.database.entity.User;
import com.polsl.trackerportal.util.ChartModeler;
import com.polsl.trackerportal.util.LoggedUser;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.json.JSONArray;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SlideEndEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
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

    @Resource
    private UserTransaction userTransaction;

    @ManagedProperty("#{loggedUser}")
    private LoggedUser loggedUser;

    private String routeId;

    private List<Speed> speedList;
    private List<Location> locationList;
    private List<Rpm> RPMList;
    private List<TroubleCodes> troubleCodes;
    private List<FuelLevel> fuelList;
    private List<FuelComsumptionRate> fuelComsumptionRate;
    private List<OilTemperature> oilTemperature;
    private List<TroubleCodesNames> troubleCodesNames;
    private Route route;
    private Car car;
    private String centerOfMap;

    private TroubleCodesNames selectedTroubleCodeId;

    private JSONArray speedProvider;
    private int speedChartSeries;

    private JSONArray rpmProvider;
    private int rpmChartSeries;

    private JSONArray troubleCodesProvider;
    private boolean troubleCodesExists;

    private JSONArray fuelLevelProvider;
    private int fuelLevelSeries;

    private JSONArray fuelConsumptionRateProvider;
    private int fuelConsumptionRateSeries;

    private JSONArray oilTemperatureProvider;
    private int oilTemperatureSeries;

    private int sliderValue;

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
            Collections.sort(speedList, (Speed t, Speed t1) -> t.getTimestamp().compareTo(t1.getTimestamp()));
        }
        if (RPMList.size() > 0) {
            Collections.sort(RPMList, (Rpm t, Rpm t1) -> t.getTimestamp().compareTo(t1.getTimestamp()));
        }
        if (!fuelList.isEmpty()) {
            Collections.sort(fuelList, (FuelLevel t, FuelLevel t1) -> t.getTimestamp().compareTo(t1.getTimestamp()));
        }
        if (!fuelComsumptionRate.isEmpty()) {
            Collections.sort(fuelComsumptionRate, (FuelComsumptionRate t, FuelComsumptionRate t1) -> {
                Long tt = t.getTimestamp();
                Long tt1 = t1.getTimestamp();
                return tt.compareTo(tt1);
            });
        }
        if (!oilTemperature.isEmpty()) {
            Collections.sort(oilTemperature, (OilTemperature t, OilTemperature t1) -> t.getTimestamp().compareTo(t1.getTimestamp()));
        }
        speedProvider = ChartModeler.initSpeedProvider(speedList);
        speedChartSeries = ChartModeler.SERIES_COUNT;

        rpmProvider = ChartModeler.initRPMProvider(RPMList);
        rpmChartSeries = ChartModeler.SERIES_COUNT;

        troubleCodesProvider = ChartModeler.initTroubleCodesProvider(troubleCodes);
        troubleCodesExists = !troubleCodes.isEmpty();

        fuelLevelProvider = ChartModeler.initFuelLevelProvider(fuelList);
        fuelLevelSeries = ChartModeler.SERIES_COUNT;

        fuelConsumptionRateProvider = ChartModeler.initFuelConsumptionProvider(fuelComsumptionRate);
        fuelConsumptionRateSeries = ChartModeler.SERIES_COUNT;

        oilTemperatureProvider = ChartModeler.initOilTemperatureProvider(oilTemperature);
        oilTemperatureSeries = ChartModeler.SERIES_COUNT;
    }

    private void createMapModel() {
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        Date startDate = new Date(locationList.get(0).getTimestamp().longValue());
        Date endDate = new Date(locationList.get(locationList.size() - 1).getTimestamp().longValue());
        mapModel.addOverlay(new Marker(new LatLng(locationList.get(0).getLatitude(), locationList.get(0).getLongitude()), "Start \n" + sfd.format(startDate)));
        mapModel.addOverlay(new Marker(new LatLng(locationList.get(locationList.size() - 1).getLatitude(), locationList.get(locationList.size() - 1).getLongitude()), "End \n" + sfd.format(endDate)));

    }

    private void initData() {
        route = (Route) entityManager.createNamedQuery("Route.findByIdRoute").setParameter("idRoute", Integer.valueOf(routeId)).getSingleResult();
        car = route.getCaridCar();
        //route = temp.get(0);
        speedList = new ArrayList<>(route.getSpeedCollection());
        locationList = new ArrayList<>(route.getLocationCollection());
        RPMList = new ArrayList(route.getRpmCollection());
        troubleCodes = new ArrayList<>(route.getTroubleCodesCollection());
        fuelList = new ArrayList<>(route.getFuelLevelCollection());
        fuelComsumptionRate = new ArrayList<>(route.getFuelComsumptionRateCollection());
        oilTemperature = new ArrayList<>(route.getOilTemperatureCollection());
        if (locationList.size() > 0) {
            Collections.sort(locationList, (Location o1, Location o2) -> o1.getTimestamp().compareTo(o2.getTimestamp()));
        }
        Set<String> codeNames = new HashSet<>();
        troubleCodes.forEach((tc) -> {
            codeNames.add(tc.getCode());
        });
        troubleCodesNames = new ArrayList<>();
        for (String code : codeNames) {
            TroubleCodesNames tcn = (TroubleCodesNames) entityManager.createNamedQuery("TroubleCodesNames.findByCode").setParameter("code", code).getSingleResult();
            TroubleCodesNames copy = new TroubleCodesNames();
            copy.setCode(code);
            copy.setDescription(tcn.getDescription());
            troubleCodesNames.add(copy);
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

    public void openEditUserDialog(TroubleCodesNames troubleCode) {
        this.selectedTroubleCodeId = troubleCode;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('changeTroubleCodeDialog').show();");
    }

    public void saveTroubleCodeDescription() {
        try {
            userTransaction.begin();
            TroubleCodesNames troubleCode = (TroubleCodesNames) entityManager.createNamedQuery("TroubleCodesNames.findByCode").setParameter("code", selectedTroubleCodeId.getCode()).getSingleResult();
            troubleCode.setDescription(selectedTroubleCodeId.getDescription());
            entityManager.merge(troubleCode);
            userTransaction.commit();
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('changeTroubleCodeDialog').hide();");
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex) {
            Logger.getLogger(RouteDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void markerPositionChanged() {
        System.out.println(getSliderValue());
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

    public boolean isTroubleCodesExists() {
        return troubleCodesExists;
    }

    public void setTroubleCodesExists(boolean troubleCodesExists) {
        this.troubleCodesExists = troubleCodesExists;
    }

    public JSONArray getFuelLevelProvider() {
        return fuelLevelProvider;
    }

    public void setFuelLevelProvider(JSONArray fuelLevelProvider) {
        this.fuelLevelProvider = fuelLevelProvider;
    }

    public int getFuelLevelSeries() {
        return fuelLevelSeries;
    }

    public void setFuelLevelSeries(int fuelLevelSeries) {
        this.fuelLevelSeries = fuelLevelSeries;
    }

    public JSONArray getFuelConsumptionRateProvider() {
        return fuelConsumptionRateProvider;
    }

    public void setFuelConsumptionRateProvider(JSONArray fuelConsumptionRateProvider) {
        this.fuelConsumptionRateProvider = fuelConsumptionRateProvider;
    }

    public int getFuelConsumptionRateSeries() {
        return fuelConsumptionRateSeries;
    }

    public void setFuelConsumptionRateSeries(int fuelConsumptionRateSeries) {
        this.fuelConsumptionRateSeries = fuelConsumptionRateSeries;
    }

    public JSONArray getOilTemperatureProvider() {
        return oilTemperatureProvider;
    }

    public void setOilTemperatureProvider(JSONArray oilTemperatureProvider) {
        this.oilTemperatureProvider = oilTemperatureProvider;
    }

    public int getOilTemperatureSeries() {
        return oilTemperatureSeries;
    }

    public void setOilTemperatureSeries(int oilTemperatureSeries) {
        this.oilTemperatureSeries = oilTemperatureSeries;
    }

    public List<TroubleCodesNames> getTroubleCodesNames() {
        return troubleCodesNames;
    }

    public void setTroubleCodesNames(List<TroubleCodesNames> troubleCodesNames) {
        this.troubleCodesNames = troubleCodesNames;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getSliderValue() {
        return sliderValue;
    }

    public void setSliderValue(int sliderValue) {
        this.sliderValue = sliderValue;
    }

    public TroubleCodesNames getSelectedTroubleCodeId() {
        return selectedTroubleCodeId;
    }

    public void setSelectedTroubleCodeId(TroubleCodesNames selectedTroubleCodeId) {
        this.selectedTroubleCodeId = selectedTroubleCodeId;
    }

}
