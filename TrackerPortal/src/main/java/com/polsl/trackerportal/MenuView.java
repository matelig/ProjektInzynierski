/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
 */
package com.polsl.trackerportal;

import com.polsl.trackerportal.util.LoggedUser;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.DefaultMenuItem;

/**
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@ManagedBean(name = "menuView")
@SessionScoped
public class MenuView implements Serializable {

    private MenuModel model;

    @ManagedProperty("#{loggedUser}")
    private LoggedUser loggedUser;

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
    private String name;

    public MenuView() {
        name = "Alalalala";
    }

    @PostConstruct
    public void init() {
        createMenuModel();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void createMenuModel() {
        model = new DefaultMenuModel();

//First submenu
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Menu");

        DefaultMenuItem item = new DefaultMenuItem("Home");
        item.setCommand("index.xhtml?faces-redirect=true");
        item.setIcon("fa fa-home");
        firstSubmenu.addElement(item);

        item = new DefaultMenuItem("Routes");
        item.setCommand("route-list-view.xhtml?faces-redirect=true");
        item.setIcon("fa fa-road");
        firstSubmenu.addElement(item);

        item = new DefaultMenuItem("Settings");
        item.setCommand("settings.xhtml?faces-redirect=true");
        item.setIcon("fa fa-gear");
        firstSubmenu.addElement(item);

        item = new DefaultMenuItem("Logout");
        item.setCommand("#{loggedUser.logout}");
        item.setIcon("fa fa-sign-out");
        firstSubmenu.addElement(item);

        model.addElement(firstSubmenu);

//Second submenu
        if (loggedUser.isAdministrator()) {
            DefaultSubMenu secondSubmenu = new DefaultSubMenu("Admin managment");

            item = new DefaultMenuItem("Users managment");
            item.setIcon("fa fa-users");
            item.setCommand("user-management.xhtml?faces-redirect=true");
            secondSubmenu.addElement(item);

            item = new DefaultMenuItem("Cars managment");
            item.setIcon("fa fa-car");
            item.setCommand("car-management.xhtml?faces-redirect=true");
            secondSubmenu.addElement(item);

            item = new DefaultMenuItem("Locations");
            item.setCommand("last-known-locations.xhtml?faces-redirect=true");
            item.setIcon("fa fa-location-arrow");
            secondSubmenu.addElement(item);

            model.addElement(secondSubmenu);
        }
    }

    public LoggedUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(LoggedUser loggedUser) {
        this.loggedUser = loggedUser;
    }

}
