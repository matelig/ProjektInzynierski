/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
 */
package com.polsl.trackerportal;

import com.polsl.trackerportal.database.entity.Car;
import com.polsl.trackerportal.database.entity.User;
import java.util.Objects;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@ManagedBean(name = "carManagement", eager = true)
@ViewScoped
public class CarManagement implements Serializable {

    private List<Car> carList;

    private Car car;
    
    private int clickedCarId;

    @PersistenceContext
    EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @PostConstruct
    public void init() {
        carList = entityManager.createNamedQuery("Car.findAll").getResultList();
    }

    public void removeCar(Car car) {
        for (Car c : carList) {
            if (Objects.equals(car.getVinNumber(), c.getVinNumber())) {
                try {
                    userTransaction.begin();
                    car = entityManager.merge(car);
                    entityManager.remove(car);
                    userTransaction.commit();
                    break;
                } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex) {
                    // Logger.getLogger(AddNewUser.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ConstraintViolationException e) {
                    // System.out.println(e);
                }
            }
        }
        carList = entityManager.createNamedQuery("Car.findAll").getResultList();
    }

    public void editCar() {
        try {
            userTransaction.begin();
            Car car = (Car) entityManager.createNamedQuery("Car.findByIdCar").setParameter("idCar", this.car.getIdCar()).getSingleResult();
            car.setVinNumber(this.car.getVinNumber());
            car.setMake(this.car.getMake());
            car.setModel(this.car.getModel());
            entityManager.merge(car);
            userTransaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {

        }
        carList = entityManager.createNamedQuery("Car.findAll").getResultList();
        closeEditDialog();

    }

    private void closeEditDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('editCarDialog').hide();");
    }

    public void openEditUserDialog(Car car) {
        this.car = car;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('editCarDialog').show();");
    }

    public String showRouteList(Car car) {
        clickedCarId = car.getIdCar();
        return "route-list-view.xhtml?faces-redirect=true&includeViewParams=true";
    }

    public void addCar() {
        try {
            userTransaction.begin();
            entityManager.persist(car);
            userTransaction.commit();
            printMessageForDialog(FacesMessage.SEVERITY_INFO, "Car has been added", "Car has been added", "userManagementForm");
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | SystemException | NotSupportedException ex) {

        } catch (ConstraintViolationException e) {
            System.out.println(e);
        }
        carList = entityManager.createNamedQuery("Car.findAll").getResultList();
        closeAddDialog();
    }

    private void closeAddDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('addCarDialog').hide();");
    }

    private void printMessageForDialog(FacesMessage.Severity severity, String title, String messageContent, String formIdUpdate) {
        FacesMessage msg = new FacesMessage(severity, title, messageContent);
        FacesContext.getCurrentInstance().addMessage(formIdUpdate, msg);
    }

    public void openAddCarDialog() {
        this.car = new Car();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('addCarDialog').show();");
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getClickedCarId() {
        return clickedCarId;
    }

    public void setClickedCarId(int clickedCarId) {
        this.clickedCarId = clickedCarId;
    }
    
    

}
