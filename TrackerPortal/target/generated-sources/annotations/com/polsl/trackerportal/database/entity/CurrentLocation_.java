package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.Car;
import com.polsl.trackerportal.database.entity.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-25T20:41:11")
@StaticMetamodel(CurrentLocation.class)
public class CurrentLocation_ { 

    public static volatile SingularAttribute<CurrentLocation, User> useridUser;
    public static volatile SingularAttribute<CurrentLocation, Integer> idcurrentLocation;
    public static volatile SingularAttribute<CurrentLocation, Double> latitude;
    public static volatile SingularAttribute<CurrentLocation, Car> caridCar;
    public static volatile SingularAttribute<CurrentLocation, Double> longitude;
    public static volatile SingularAttribute<CurrentLocation, Long> timestamp;

}