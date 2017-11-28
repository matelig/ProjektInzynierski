/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
 */
package com.polsl.trackerportal;

import com.polsl.trackerportal.database.entity.Route;
import com.polsl.trackerportal.database.entity.User;
import com.polsl.trackerportal.util.LoggedUser;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@ManagedBean(name = "userManagement")
@ViewScoped
public class UserManagement implements Serializable {

    List<User> userList;

    @PersistenceContext
    EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    private User user;
    
    private Integer clickedUserId;

    @PostConstruct
    public void init() {
        userList = entityManager.createNamedQuery("User.findAll").getResultList();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void removeUser(User user) {
        for (User u : userList) {
            if (Objects.equals(user.getIdUser(), u.getIdUser())) {
                try {
                    userTransaction.begin();
                    user = entityManager.merge(user);
                    entityManager.remove(user);
                    userTransaction.commit();
                    break;
                } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex) {
                    // Logger.getLogger(AddNewUser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ConstraintViolationException e) {
                    // System.out.println(e);
                }
            }
        }
        userList = entityManager.createNamedQuery("User.findAll").getResultList();
    }

    public void addUser() {
        if (checkRequiredFields()) {
            printMessageForDialog(FacesMessage.SEVERITY_ERROR, "Some required fields are empty", null, "newUserDialogPanel");
            return;
        }
        if (checkUniquePesel()) {
            printMessageForDialog(FacesMessage.SEVERITY_ERROR, "User having that pesel is already registered", null, "newUserDialogPanel");
            return;
        }
        try {
            userTransaction.begin();
            entityManager.persist(user);
            userTransaction.commit();
            printMessageForDialog(FacesMessage.SEVERITY_INFO, "User has been added", "User has been added", "userManagementForm");
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex) {
            
        } catch (ConstraintViolationException e) {
            System.out.println(e);
        }
        userList = entityManager.createNamedQuery("User.findAll").getResultList();
        closeAddDialog();
    }

    public void editUser() {
        
        try {
            userTransaction.begin();
            User user = (User) entityManager.createNamedQuery("User.findByPesel").setParameter("pesel", this.user.getPesel()).getSingleResult();
            user.setPhoneNumber(this.user.getPhoneNumber());
            user.setEmail(this.user.getEmail());
            user.setName(this.user.getName());
            user.setSurname(this.user.getSurname());
            user.setAdministrator(this.user.getAdministrator());
            entityManager.merge(user);
            userTransaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(LoggedUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        userList = entityManager.createNamedQuery("User.findAll").getResultList();
        closeEditDialog();

    }
    
    public String showRouteList(User user) {
        clickedUserId = user.getIdUser();
        return "route-list-view.xhtml?faces-redirect=true&includeViewParams=true";
    }

    private void closeAddDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('addUserDialog').hide();");
    }

    private void closeEditDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('editUserDialog').hide();");
    }

    private boolean checkRequiredFields() {
        return (user.getName() == null || user.getName().isEmpty()
                || user.getSurname() == null || user.getSurname().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getPesel() == null || user.getPesel().isEmpty());
    }

    public void openAddUserDialog() {
        this.user = new User();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('addUserDialog').show();");
    }

    public void openEditUserDialog(User user) {
        this.user = user;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('editUserDialog').show();");
    }

    private boolean checkUniquePesel() {
        List<User> users = entityManager.createNamedQuery("User.findByPesel").setParameter("pesel", user.getPesel()).getResultList();
        return (!users.isEmpty());
    }

    private void printMessageForDialog(FacesMessage.Severity severity, String title, String messageContent, String formIdUpdate) {
        FacesMessage msg = new FacesMessage(severity, title, messageContent);
        FacesContext.getCurrentInstance().addMessage(formIdUpdate, msg);
    }

    public void updateList() {
        System.out.println("dshf");
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getClickedUserId() {
        return clickedUserId;
    }

    public void setClickedUserId(Integer clickedUserId) {
        this.clickedUserId = clickedUserId;
    }  

}
