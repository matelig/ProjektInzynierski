package com.polsl.projektinzynierski.cartrackerapi;

import com.polsl.projektinzynierski.cartrackerapi.Route;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-19T14:59:39")
@StaticMetamodel(FuelLevel.class)
public class FuelLevel_ { 

    public static volatile SingularAttribute<FuelLevel, Integer> idfuelLevel;
    public static volatile SingularAttribute<FuelLevel, Route> routeidRoute;
    public static volatile SingularAttribute<FuelLevel, Integer> value;
    public static volatile SingularAttribute<FuelLevel, BigInteger> timestamp;

}