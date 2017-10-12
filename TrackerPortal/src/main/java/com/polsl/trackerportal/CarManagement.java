/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
*/

package com.polsl.trackerportal;

import com.polsl.trackerportal.database.entity.Car;
import com.polsl.trackerportal.database.entity.User;import java.util.Objects;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@ManagedBean(name = "carManagement", eager = true)
@ViewScoped
public class CarManagement implements Serializable{

    private List<Car> carList;
    
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
            if (Objects.equals(car.getVinNumber(),c.getVinNumber())) {
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
    
    
    
}
