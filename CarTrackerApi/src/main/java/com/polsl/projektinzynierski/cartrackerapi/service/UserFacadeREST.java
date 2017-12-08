/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.projektinzynierski.cartrackerapi.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.polsl.projektinzynierski.cartrackerapi.Route;
import com.polsl.projektinzynierski.cartrackerapi.User;
import java.util.Collection;
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
import javax.ws.rs.core.Response;

/**
 *
 * @author m_lig
 */
@Stateless
@Path("user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "com.polsl.projektInzynierski_CarTrackerApi_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Path("create")
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(User entity) {
        super.create(entity);
    }

    @GET
    @Path("routes")
    @Consumes({MediaType.APPLICATION_JSON})
    public Collection<Route> getRouteList(User user) {
        return user.getRouteCollection();
    }

    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response checkUser(User entity) {
        em.getEntityManagerFactory().getCache().evictAll();
        
        List<User> users = em.createNamedQuery("User.findByPesel").setParameter("pesel", entity.getPesel()).getResultList();
        if (users.isEmpty()) {
            //return Response.serverError().entity("User not found in database").build();
            return Response.status(Response.Status.NOT_FOUND).entity("User not found in database").build();
        } else {
            User user = users.get(0);
            if (entity.getPassword().equals(user.getPassword())) {
                User returnedUser = new User();
                returnedUser.setIdUser(user.getIdUser());
                returnedUser.setName(user.getName());
                returnedUser.setSurname(user.getSurname());
                returnedUser.setPesel(user.getPesel());
                Gson g = new Gson();
                String json = g.toJson(returnedUser);
                return Response.ok(json,MediaType.APPLICATION_JSON).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("User's password is incorrect").build();
            }
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, User entity) {
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
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
