package com.polsl.projektinzynierski.cartrackerapi;

import com.polsl.projektinzynierski.cartrackerapi.Route;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-10T23:44:04")
@StaticMetamodel(TroubleCodes.class)
public class TroubleCodes_ { 

    public static volatile SingularAttribute<TroubleCodes, String> code;
    public static volatile SingularAttribute<TroubleCodes, Integer> idtroubleCodes;
    public static volatile SingularAttribute<TroubleCodes, Integer> state;
    public static volatile SingularAttribute<TroubleCodes, Route> routeidRoute;
    public static volatile SingularAttribute<TroubleCodes, Long> timestamp;

}