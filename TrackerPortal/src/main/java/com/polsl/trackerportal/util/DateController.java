/*
(c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
1:1 Realny obraz Twojej firmy
 */
package com.polsl.trackerportal.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author Mateusz Ligus <mateusz.ligus@spiid.pl>
 */
@ManagedBean(name = "dateController")
@SessionScoped
public class DateController implements Serializable {

    private Date startDate;

    public boolean filterByStartDate(Object value, Object filter, Locale locale) {
        if (filter == null) {
            return true;
        }
        if (value == null) {
            return false;
        }
        Date startDate = (Date) value;
        Date filterDate = (Date) filter;
        int val = startDate.compareTo(filterDate);
        return (val>=0);
    }

    public boolean filterByEndDate(Object value, Object filter, Locale locale) {
        if (filter == null) {
            return true;
        }
        if (value == null) {
            return false;
        }
        Date endDate = (Date) value;
        Date filterDate = (Date) filter;
        int val = endDate.compareTo(filterDate);
        return (val<=0);
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

}
