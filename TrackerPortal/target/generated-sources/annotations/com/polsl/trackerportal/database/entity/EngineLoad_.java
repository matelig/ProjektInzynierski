package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.Route;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-03T19:44:15")
@StaticMetamodel(EngineLoad.class)
public class EngineLoad_ { 

    public static volatile SingularAttribute<EngineLoad, Integer> idengineLoad;
    public static volatile SingularAttribute<EngineLoad, Route> routeidRoute;
    public static volatile SingularAttribute<EngineLoad, Double> value;
    public static volatile SingularAttribute<EngineLoad, Long> timestamp;

}