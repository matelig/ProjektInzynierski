/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
 */
package com.polsl.trackerportal;

import com.polsl.trackerportal.database.entity.Car;
import com.polsl.trackerportal.database.entity.Route;
import com.polsl.trackerportal.database.entity.Speed;
import com.polsl.trackerportal.util.LoggedUser;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@ManagedBean(name = "userStatistics")
@ViewScoped
public class UserStatistics implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    private Double totalLength;
    private Long totalTime;
    private int numberOfRoads;
    private int numberOfCars;
    private String mostCommonCar;
    private Double averageSpeed;

    @ManagedProperty("#{loggedUser}")
    private LoggedUser loggedUser;

    @PostConstruct
    public void init() {
        initData();
    }

    private void initData() {
        totalLength = 0.0;
        totalTime = 0L;
        numberOfCars = 0;
        numberOfRoads = 0;
        averageSpeed = 0.0;
        List<Route> routeList = entityManager.createNamedQuery("Route.findAll").getResultList();
        for (int i = routeList.size() - 1; i >= 0; i--) {
            if (!routeList.get(i).getUseridUser().getPesel().equals(loggedUser.getPesel())) {
                routeList.remove(i);
            }
        }
        int speedCounter = 0;
        Map<String, Integer> carsMap = new HashMap<>();
        for (Route route : routeList) {
            List<Speed> speedList = new ArrayList(route.getSpeedCollection());
            for (Speed s : speedList) {
                averageSpeed += s.getValue();
                speedCounter++;
            }
            Car car = route.getCaridCar();
            if (carsMap.containsKey(car.getVinNumber())) {
                carsMap.put(car.getVinNumber(), carsMap.get(car.getVinNumber()) + 1);
            } else {
                carsMap.put(car.getVinNumber(), 1);
            }
            totalLength += route.getLength();
            totalTime += (route.getEndDate().longValue() - route.getStartDate().longValue());
        }
        averageSpeed /= speedCounter;
        numberOfCars = carsMap.size();
        numberOfRoads = routeList.size();
        totalLength /= 1000.0;
        int carNumberUsage = 0;
        for (Map.Entry<String, Integer> entry : carsMap.entrySet()) {
            if (entry.getValue() > carNumberUsage) {
                mostCommonCar = entry.getKey();
            }
        }
        Locale currentLocale = Locale.getDefault();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("####.00", otherSymbols);
        averageSpeed = Double.valueOf(df.format(averageSpeed));
        totalLength = Double.valueOf(df.format(totalLength));

    }

    public String getRouteDuration() {

        long second = (totalTime / 1000) % 60;
        long minute = (totalTime / (1000 * 60)) % 60;
        long hour = (totalTime / (1000 * 60 * 60)) % 24;

        String time = String.format("%02dh %02dmin %02dsec", hour, minute, second);

        return time;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Double getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Double totalLength) {
        this.totalLength = totalLength;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public int getNumberOfRoads() {
        return numberOfRoads;
    }

    public void setNumberOfRoads(int numberOfRoads) {
        this.numberOfRoads = numberOfRoads;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }

    public String getMostCommonCar() {
        return mostCommonCar;
    }

    public void setMostCommonCar(String mostCommonCar) {
        this.mostCommonCar = mostCommonCar;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public LoggedUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;
    }

}
