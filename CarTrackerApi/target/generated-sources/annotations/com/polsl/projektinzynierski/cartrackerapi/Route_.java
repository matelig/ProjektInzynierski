package com.polsl.projektinzynierski.cartrackerapi;

import com.polsl.projektinzynierski.cartrackerapi.Car;
import com.polsl.projektinzynierski.cartrackerapi.FuelComsumptionRate;
import com.polsl.projektinzynierski.cartrackerapi.FuelLevel;
import com.polsl.projektinzynierski.cartrackerapi.Location;
import com.polsl.projektinzynierski.cartrackerapi.OilTemperature;
import com.polsl.projektinzynierski.cartrackerapi.Rpm;
import com.polsl.projektinzynierski.cartrackerapi.Speed;
import com.polsl.projektinzynierski.cartrackerapi.TroubleCodes;
import com.polsl.projektinzynierski.cartrackerapi.User;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-28T15:28:58")
@StaticMetamodel(Route.class)
public class Route_ { 

    public static volatile CollectionAttribute<Route, FuelComsumptionRate> fuelComsumptionRateCollection;
    public static volatile CollectionAttribute<Route, TroubleCodes> troubleCodesCollection;
    public static volatile CollectionAttribute<Route, OilTemperature> oilTemperatureCollection;
    public static volatile CollectionAttribute<Route, FuelLevel> fuelLevelCollection;
    public static volatile SingularAttribute<Route, User> useridUser;
    public static volatile SingularAttribute<Route, Integer> idRoute;
    public static volatile SingularAttribute<Route, BigInteger> endDate;
    public static volatile CollectionAttribute<Route, Rpm> rpmCollection;
    public static volatile SingularAttribute<Route, Car> caridCar;
    public static volatile CollectionAttribute<Route, Speed> speedCollection;
    public static volatile CollectionAttribute<Route, Location> locationCollection;
    public static volatile SingularAttribute<Route, BigInteger> startDate;

}