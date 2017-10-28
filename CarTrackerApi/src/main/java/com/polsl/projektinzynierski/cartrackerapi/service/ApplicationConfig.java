/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.projektinzynierski.cartrackerapi.service;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author m_lig
 */
@ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.polsl.projektinzynierski.cartrackerapi.service.CarFacadeREST.class);
        resources.add(com.polsl.projektinzynierski.cartrackerapi.service.CurrentLocationREST.class);
        resources.add(com.polsl.projektinzynierski.cartrackerapi.service.FuelComsumptionRateFacadeREST.class);
        resources.add(com.polsl.projektinzynierski.cartrackerapi.service.FuelLevelFacadeREST.class);
        resources.add(com.polsl.projektinzynierski.cartrackerapi.service.LocationFacadeREST.class);
        resources.add(com.polsl.projektinzynierski.cartrackerapi.service.OilTemperatureFacadeREST.class);
        resources.add(com.polsl.projektinzynierski.cartrackerapi.service.RouteFacadeREST.class);
        resources.add(com.polsl.projektinzynierski.cartrackerapi.service.RpmFacadeREST.class);
        resources.add(com.polsl.projektinzynierski.cartrackerapi.service.SpeedFacadeREST.class);
        resources.add(com.polsl.projektinzynierski.cartrackerapi.service.TroubleCodesFacadeREST.class);
        resources.add(com.polsl.projektinzynierski.cartrackerapi.service.UserFacadeREST.class);
    }
    
}
