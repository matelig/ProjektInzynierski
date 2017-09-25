/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.projektinzynierski.cartrackerapi.service;

import com.polsl.projektinzynierski.cartrackerapi.Car;
import com.polsl.projektinzynierski.cartrackerapi.Location;
import com.polsl.projektinzynierski.cartrackerapi.Route;
import com.polsl.projektinzynierski.cartrackerapi.RouteData;
import com.polsl.projektinzynierski.cartrackerapi.Rpm;
import com.polsl.projektinzynierski.cartrackerapi.Speed;
import com.polsl.projektinzynierski.cartrackerapi.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author m_lig
 */
@Stateless
@Path("route")
public class RouteFacadeREST extends AbstractFacade<Route> {

    @PersistenceContext(unitName = "com.polsl.projektInzynierski_CarTrackerApi_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public RouteFacadeREST() {
        super(Route.class);
    }

    @POST
    @Path("create")
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(RouteData entity) {
        List<Location> locations = entity.getLocationCollection();
        List<Speed> speed = entity.getSpeedCollection();
        List<Rpm> rpm = entity.getRPMCollection();
        Route route = new Route();
        List<Car> cars = em.createNamedQuery("Car.findByVinNumber").setParameter("vinNumber", entity.getCarVin()).getResultList();
        //przyda się sprawdzenie, czy istnieje dany samochód, jeśli nie istnieje - stwórz go z jakimiś defaultowymi danymi, które potem administrator może edytować (na stronce)
        route.setCarvinNumber(cars.get(0));
        List<User> users = em.createNamedQuery("User.findByIdUser").setParameter("idUser", entity.getIdUser()).getResultList();
        route.setUseridUser(users.get(0));
        route.setEndDate(entity.getEndDate());
        route.setStartDate(entity.getStartDate());
        for (Location l : locations) {
            l.setRouteidRoute(route);
        }
        for (Rpm r : rpm) {
            r.setRouteidRoute(route);
        }
        for (Speed s : speed) {
            s.setRouteidRoute(route);
        }
        route.setSpeedCollection(speed);
        route.setRpmCollection(rpm);
        route.setLocationCollection(locations);
        super.create(route);
    }

    @POST
    @Path("insert")
    @Consumes({MediaType.APPLICATION_JSON})
    public void insert(List<Route> routeList) {
        for (Route r : routeList) {
            create(r);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Route entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Route find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Route> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Route> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
