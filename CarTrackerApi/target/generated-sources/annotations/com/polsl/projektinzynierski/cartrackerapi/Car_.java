package com.polsl.projektinzynierski.cartrackerapi;

import com.polsl.projektinzynierski.cartrackerapi.Route;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-18T13:35:16")
@StaticMetamodel(Car.class)
public class Car_ { 

    public static volatile SingularAttribute<Car, String> vinNumber;
    public static volatile CollectionAttribute<Car, Route> routeCollection;
    public static volatile SingularAttribute<Car, String> model;
    public static volatile SingularAttribute<Car, String> make;
    public static volatile SingularAttribute<Car, Long> idCar;

}