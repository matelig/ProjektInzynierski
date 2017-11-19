package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.Route;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-19T17:53:10")
@StaticMetamodel(Location.class)
public class Location_ { 

    public static volatile SingularAttribute<Location, Integer> idLocation;
    public static volatile SingularAttribute<Location, Double> latitude;
    public static volatile SingularAttribute<Location, Route> routeidRoute;
    public static volatile SingularAttribute<Location, Double> longitude;
    public static volatile SingularAttribute<Location, BigInteger> timestamp;

}