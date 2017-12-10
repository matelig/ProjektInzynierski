/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.projektinzynierski.cartrackerapi.service;

import com.polsl.projektinzynierski.cartrackerapi.Car;
import com.polsl.projektinzynierski.cartrackerapi.CurrentLocation;
import com.polsl.projektinzynierski.cartrackerapi.CurrentLocationJSON;
import com.polsl.projektinzynierski.cartrackerapi.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@Stateless
@Path("currentLocation")
public class CurrentLocationREST extends AbstractFacade<CurrentLocation> {

    @PersistenceContext(unitName = "com.polsl.projektInzynierski_CarTrackerApi_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     * Creates a new instance of GenericResource
     */
    public CurrentLocationREST() {
        super(CurrentLocation.class);
    }

    @POST
    @Path("update")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(CurrentLocationJSON cl) {
        //dodawanie samochodu todo

        List<User> users = em.createNamedQuery("User.findByIdUser").setParameter("idUser", cl.getUserId()).getResultList();
        List<CurrentLocation> usersLocation = new ArrayList<>(users.get(0).getCurrentLocationCollection());
        Car usedCar = null;
        if (cl.getCarVin()==null ||cl.getCarVin().isEmpty()) {
            usedCar = null;
        } else {
            List<Car> cars = em.createNamedQuery("Car.findByVinNumber").setParameter("vinNumber", cl.getCarVin()).getResultList();
            if (cars.isEmpty()) {
            usedCar = new Car();
            usedCar.setMake("unknown");
            usedCar.setModel("unknown");
            usedCar.setVinNumber(cl.getCarVin());
            em.persist(usedCar);
        }           
        cars = em.createNamedQuery("Car.findByVinNumber").setParameter("vinNumber", cl.getCarVin()).getResultList();
        }
        if (usersLocation.isEmpty()) {
            CurrentLocation currentLocation = new CurrentLocation();
            currentLocation.setUseridUser(users.get(0));
            currentLocation.setLatitude(cl.getLatitude());
            currentLocation.setLongitude(cl.getLongitude());
            currentLocation.setCaridCar(usedCar);
            currentLocation.setTimestamp(cl.getTimestamp());
            super.create(currentLocation);
            
            usersLocation.add(currentLocation);
            users.get(0).setCurrentLocationCollection(usersLocation);
            em.merge(users.get(0));
        } else {
            CurrentLocation currentLocation = usersLocation.get(0);
            currentLocation.setLatitude(cl.getLatitude());
            currentLocation.setLongitude(cl.getLongitude());
            currentLocation.setCaridCar(usedCar);
            currentLocation.setTimestamp(cl.getTimestamp());
            super.edit(currentLocation);
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
