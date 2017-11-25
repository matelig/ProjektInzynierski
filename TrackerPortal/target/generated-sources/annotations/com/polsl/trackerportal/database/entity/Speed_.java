package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.Route;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-25T20:41:11")
@StaticMetamodel(Speed.class)
public class Speed_ { 

    public static volatile SingularAttribute<Speed, Integer> idSpeed;
    public static volatile SingularAttribute<Speed, Route> routeidRoute;
    public static volatile SingularAttribute<Speed, Double> value;
    public static volatile SingularAttribute<Speed, Long> timestamp;

}