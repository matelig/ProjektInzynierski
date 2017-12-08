package com.polsl.projektinzynierski.cartrackerapi;

import com.polsl.projektinzynierski.cartrackerapi.Route;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-08T22:56:43")
@StaticMetamodel(FuelLevel.class)
public class FuelLevel_ { 

    public static volatile SingularAttribute<FuelLevel, Integer> idfuelLevel;
    public static volatile SingularAttribute<FuelLevel, Route> routeidRoute;
    public static volatile SingularAttribute<FuelLevel, Double> value;
    public static volatile SingularAttribute<FuelLevel, BigInteger> timestamp;

}