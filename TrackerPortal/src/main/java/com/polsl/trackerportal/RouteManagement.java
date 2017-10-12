/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
 */
package com.polsl.trackerportal;

import com.polsl.trackerportal.database.entity.Route;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@ManagedBean(name = "routeManagement", eager = true)
@ViewScoped
public class RouteManagement implements Serializable {

    private List<Route> routeList;
    
}
