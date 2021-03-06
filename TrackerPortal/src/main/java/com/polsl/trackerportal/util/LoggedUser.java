/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.trackerportal.util;

import com.polsl.trackerportal.database.entity.User;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

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
    private int phoneNumber;
    private String password;

    @Resource
    UserTransaction userTransaction;

    private boolean administrator;

    @PersistenceContext
    private EntityManager entityManager;

    public String login() {
        List<User> userList = entityManager.createNamedQuery("User.findByLogin").setParameter("login", pesel).getResultList();
        FacesContext context = FacesContext.getCurrentInstance();

        if (userList.isEmpty()) {
            context.addMessage(null, new FacesMessage("Unknown login, try again"));
            pesel = null;
            password = null;
            return null;
        } else {
            User currentUser = userList.get(0);
            String hashed;
            try {
                hashed = CryptoHash.hashPassword(password);
            } catch (NoSuchAlgorithmException ex) {
                hashed = password;
            }
            if (hashed.equals(currentUser.getPassword())) {
                userName = currentUser.getName();
                userSurname = currentUser.getSurname();
                pesel = currentUser.getLogin();
                if (currentUser.getPhoneNumber() != null) {
                    phoneNumber = currentUser.getPhoneNumber();
                }
                email = currentUser.getEmail();
                administrator = currentUser.getAdministrator();

                context.getExternalContext().getSessionMap().put("user", currentUser);
                return "index?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage("Incorrect password, try again"));
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
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

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

}
