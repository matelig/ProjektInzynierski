package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.Car;
import com.polsl.trackerportal.database.entity.EngineLoad;
import com.polsl.trackerportal.database.entity.FuelComsumptionRate;
import com.polsl.trackerportal.database.entity.FuelLevel;
import com.polsl.trackerportal.database.entity.Location;
import com.polsl.trackerportal.database.entity.OilTemperature;
import com.polsl.trackerportal.database.entity.Rpm;
import com.polsl.trackerportal.database.entity.Speed;
import com.polsl.trackerportal.database.entity.TroubleCodes;
import com.polsl.trackerportal.database.entity.User;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-10T23:19:57")
@StaticMetamodel(Route.class)
public class Route_ { 

    public static volatile CollectionAttribute<Route, FuelComsumptionRate> fuelComsumptionRateCollection;
    public static volatile CollectionAttribute<Route, TroubleCodes> troubleCodesCollection;
    public static volatile SingularAttribute<Route, Integer> idRoute;
    public static volatile SingularAttribute<Route, BigInteger> endDate;
    public static volatile CollectionAttribute<Route, EngineLoad> engineLoadCollection;
    public static volatile SingularAttribute<Route, Car> caridCar;
    public static volatile SingularAttribute<Route, Double> roadLength;
    public static volatile CollectionAttribute<Route, FuelLevel> fuelLevelCollection;
    public static volatile CollectionAttribute<Route, OilTemperature> oilTemperatureCollection;
    public static volatile SingularAttribute<Route, User> useridUser;
    public static volatile CollectionAttribute<Route, Rpm> rpmCollection;
    public static volatile CollectionAttribute<Route, Speed> speedCollection;
    public static volatile CollectionAttribute<Route, Location> locationCollection;
    public static volatile SingularAttribute<Route, BigInteger> startDate;

}