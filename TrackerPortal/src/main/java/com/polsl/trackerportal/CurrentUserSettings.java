/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
 */
package com.polsl.trackerportal;

import com.polsl.trackerportal.database.entity.User;
import com.polsl.trackerportal.util.Const;
import com.polsl.trackerportal.util.CryptoHash;
import com.polsl.trackerportal.util.LoggedUser;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@ManagedBean(name = "currentUserSettings")
@ViewScoped
public class CurrentUserSettings {

    @ManagedProperty("#{loggedUser}")
    private LoggedUser loggedUser;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;

    private String userName;
    private String userSurname;
    private String pesel;
    private String email;
    private int phoneNumber;
    private String password;
    private String newPassword;
    private String confirmNewPassword;
    private boolean administrator;

    @PostConstruct
    public void init() {
        this.userName = loggedUser.getUserName();
        this.userSurname = loggedUser.getUserSurname();
        this.pesel = loggedUser.getPesel();
        this.email = loggedUser.getEmail();
        this.phoneNumber = loggedUser.getPhoneNumber();
        this.password = loggedUser.getPassword();
        this.administrator = loggedUser.isAdministrator();
    }

    public void update() {
        User user = (User) entityManager.createNamedQuery("User.findByPesel").setParameter("pesel", pesel).getSingleResult();
        if (!email.matches(Const.EMAIL_PATTERN)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Email is incorrect."));
            return;
        }
        try {
            userTransaction.begin();
            user.setPhoneNumber(phoneNumber);
            user.setEmail(email);
            user.setName(userName);
            user.setSurname(userSurname);
            entityManager.merge(user);
            userTransaction.commit();
            loggedUser.setPhoneNumber(phoneNumber);
            loggedUser.setEmail(email);
            loggedUser.setUserName(userName);
            loggedUser.setUserSurname(userSurname);
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(LoggedUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Changes saved."));
    }

    public void openChangePasswordDialog() {
        password = "";
        newPassword = "";
        confirmNewPassword = "";
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('changePasswordDialog').show();");
    }

    private void closeChangePasswordDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('changePasswordDialog').hide();");
    }

    public void changeUserPassword() {
        User currentUser = (User) entityManager.createNamedQuery("User.findByPesel").setParameter("pesel", loggedUser.getPesel()).getSingleResult();
        try {
            String hashedPassword = CryptoHash.hashPassword(password);
            if (hashedPassword.equals(currentUser.getPassword())) {
                if (newPassword.equals(confirmNewPassword)) {
                    hashedPassword = CryptoHash.hashPassword(newPassword);
                    try {
                        userTransaction.begin();
                        currentUser.setPassword(hashedPassword);
                        entityManager.merge(currentUser);
                        userTransaction.commit();
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage("Password has been changed"));
                        closeChangePasswordDialog();
                    } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                        Logger.getLogger(CurrentUserSettings.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("New passwords' input does not match."));
                }
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Wrong old password"));
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CurrentUserSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printMessageForDialog(FacesMessage.Severity severity, String title, String messageContent, String formIdUpdate) {
        FacesMessage msg = new FacesMessage(severity, title, messageContent);
        FacesContext.getCurrentInstance().addMessage(formIdUpdate, msg);
    }

    public LoggedUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserTransaction getUserTransaction() {
        return userTransaction;
    }

    public void setUserTransaction(UserTransaction userTransaction) {
        this.userTransaction = userTransaction;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

}
