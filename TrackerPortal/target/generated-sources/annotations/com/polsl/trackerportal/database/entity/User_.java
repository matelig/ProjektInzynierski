package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.CurrentLocation;
import com.polsl.trackerportal.database.entity.Route;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-28T01:25:51")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Integer> idUser;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Short> administrator;
    public static volatile SingularAttribute<User, Integer> phoneNumber;
    public static volatile CollectionAttribute<User, Route> routeCollection;
    public static volatile SingularAttribute<User, String> surname;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, String> pesel;
    public static volatile CollectionAttribute<User, CurrentLocation> currentLocationCollection;
    public static volatile SingularAttribute<User, String> email;

}