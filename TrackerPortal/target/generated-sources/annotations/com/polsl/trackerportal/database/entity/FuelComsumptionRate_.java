package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.Route;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-28T23:08:34")
@StaticMetamodel(FuelComsumptionRate.class)
public class FuelComsumptionRate_ { 

    public static volatile SingularAttribute<FuelComsumptionRate, Integer> idfuelComsumptionRate;
    public static volatile SingularAttribute<FuelComsumptionRate, Route> routeidRoute;
    public static volatile SingularAttribute<FuelComsumptionRate, Double> value;
    public static volatile SingularAttribute<FuelComsumptionRate, Long> timestamp;

}