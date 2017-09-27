package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.Car;
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
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-27T19:51:27")
@StaticMetamodel(Route.class)
public class Route_ { 

    public static volatile SingularAttribute<Route, Car> carvinNumber;
    public static volatile ListAttribute<Route, TroubleCodes> troubleCodesList;
    public static volatile ListAttribute<Route, Speed> speedList;
    public static volatile ListAttribute<Route, Location> locationList;
    public static volatile ListAttribute<Route, OilTemperature> oilTemperatureList;
    public static volatile SingularAttribute<Route, User> useridUser;
    public static volatile SingularAttribute<Route, Integer> idRoute;
    public static volatile ListAttribute<Route, Rpm> rpmList;
    public static volatile SingularAttribute<Route, BigInteger> endDate;
    public static volatile ListAttribute<Route, FuelComsumptionRate> fuelComsumptionRateList;
    public static volatile ListAttribute<Route, FuelLevel> fuelLevelList;
    public static volatile SingularAttribute<Route, BigInteger> startDate;

}