package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.Route;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-10T23:51:25")
@StaticMetamodel(FuelLevel.class)
public class FuelLevel_ { 

    public static volatile SingularAttribute<FuelLevel, Integer> idfuelLevel;
    public static volatile SingularAttribute<FuelLevel, Route> routeidRoute;
    public static volatile SingularAttribute<FuelLevel, Double> value;
    public static volatile SingularAttribute<FuelLevel, BigInteger> timestamp;

}