package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.Route;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-03T19:44:15")
@StaticMetamodel(TroubleCodes.class)
public class TroubleCodes_ { 

    public static volatile SingularAttribute<TroubleCodes, String> code;
    public static volatile SingularAttribute<TroubleCodes, Integer> idtroubleCodes;
    public static volatile SingularAttribute<TroubleCodes, Integer> state;
    public static volatile SingularAttribute<TroubleCodes, Route> routeidRoute;
    public static volatile SingularAttribute<TroubleCodes, Long> timestamp;

}