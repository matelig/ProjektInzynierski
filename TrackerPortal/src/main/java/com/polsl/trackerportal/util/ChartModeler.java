/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.trackerportal.util;

import com.polsl.trackerportal.database.entity.Rpm;
import com.polsl.trackerportal.database.entity.Speed;
import java.util.Date;
import java.util.List;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author m_lig
 */
public class ChartModeler {
    
    public static LineChartModel  initSpeedModel(List<Speed> speedData) {
        LineChartModel  model = new LineChartModel ();
 
        LineChartSeries series = new LineChartSeries();
        series.setLabel("Series 1");
        
        for (Speed s : speedData) {
            Date date = new Date(s.getTimestamp());
            series.set(s.getTimestamp(), s.getValue());
        }
        
        model.addSeries(series);
         
        return model;
    }
    
    public static LineChartModel initRPMModel(List<Rpm> rpmData) {
        LineChartModel  model = new LineChartModel ();
 
        LineChartSeries series = new LineChartSeries();
        series.setLabel("Series 1");
        for (Rpm s : rpmData) {
            series.set(s.getTimestamp(), s.getValue());
        }
        
        model.addSeries(series);
         
        return model;
    }
}
