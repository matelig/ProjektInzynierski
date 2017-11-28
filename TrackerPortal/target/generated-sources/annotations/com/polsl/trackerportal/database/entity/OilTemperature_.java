package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.Route;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-28T23:08:34")
@StaticMetamodel(OilTemperature.class)
public class OilTemperature_ { 

    public static volatile SingularAttribute<OilTemperature, Integer> idoilTemperature;
    public static volatile SingularAttribute<OilTemperature, Route> routeidRoute;
    public static volatile SingularAttribute<OilTemperature, Double> value;
    public static volatile SingularAttribute<OilTemperature, BigInteger> timestamp;

}