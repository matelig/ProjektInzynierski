package com.polsl.trackerportal.database.entity;

import com.polsl.trackerportal.database.entity.Route;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-11-28T22:47:47")
@StaticMetamodel(TroubleCodes.class)
public class TroubleCodes_ { 

    public static volatile SingularAttribute<TroubleCodes, String> code;
    public static volatile SingularAttribute<TroubleCodes, Integer> idtroubleCodes;
    public static volatile SingularAttribute<TroubleCodes, Integer> state;
    public static volatile SingularAttribute<TroubleCodes, Route> routeidRoute;
    public static volatile SingularAttribute<TroubleCodes, Long> timestamp;

}