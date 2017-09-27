/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.trackerportal.util;

import com.polsl.trackerportal.database.entity.User;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author m_lig
 */
@ManagedBean(name = "loggedUser")
@SessionScoped
public class LoggedUser implements Serializable {

    private String userName;
    private String userSurname;
    private String pesel;
    private String email;
    private String phoneNumber;
    private String password;

    @PersistenceContext
    private EntityManager entityManager;

    public String login() {
        List<User> userList = entityManager.createNamedQuery("User.findByPesel").setParameter("pesel", pesel).getResultList();
        FacesContext context = FacesContext.getCurrentInstance();

        if (userList.isEmpty()) {
            context.addMessage(null, new FacesMessage("Unknown login, try again"));
            pesel = null;
            password = null;
            return null;
        } else {
            User currentUser = userList.get(0);
            if (password.equals(currentUser.getPassword())) {
                userName = currentUser.getName();
                userSurname = currentUser.getSurname();
                
                context.getExternalContext().getSessionMap().put("user", currentUser);
                return "index?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Login Failed","Incorrect password, try again"));
                password = null;
                return null;
            }
        }
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
