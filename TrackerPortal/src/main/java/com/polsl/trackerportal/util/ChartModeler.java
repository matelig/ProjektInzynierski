/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.trackerportal.util;

import com.polsl.trackerportal.database.entity.Location;
import com.polsl.trackerportal.database.entity.Rpm;
import com.polsl.trackerportal.database.entity.Speed;
import com.polsl.trackerportal.database.entity.TroubleCodes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import pl.spiid.lib.spiidcharts.models.GanttAmChartModel;
import pl.spiid.lib.spiidcharts.models.SerialAmChartModel;
import pl.spiid.lib.spiidcharts.models.attributes.AmChart;
import pl.spiid.lib.spiidcharts.models.attributes.CategoryAxis;
import pl.spiid.lib.spiidcharts.models.attributes.ChartCursor;
import pl.spiid.lib.spiidcharts.models.attributes.ChartScrollbar;
import pl.spiid.lib.spiidcharts.models.attributes.Color;
import pl.spiid.lib.spiidcharts.models.attributes.Graph;
import pl.spiid.lib.spiidcharts.models.attributes.GraphAxes;
import pl.spiid.lib.spiidcharts.models.attributes.Legend;
import pl.spiid.lib.spiidcharts.models.attributes.ValueAxis;
import pl.spiid.lib.spiidcharts.models.data.DataMap;
import pl.spiid.lib.spiidcharts.models.data.DateSegment;
import pl.spiid.lib.spiidcharts.models.data.GanttDataProvider;
import pl.spiid.lib.spiidcharts.models.enums.Position;
import pl.spiid.lib.spiidcharts.models.enums.Time;

/**
 *
 * @author m_lig
 */
public class ChartModeler {

    public static SerialAmChartModel initSpeedModel(List<Speed> speedData) {
//        LineChartModel model = new LineChartModel();
//
//        LineChartSeries series = new LineChartSeries();
//        series.setLabel("Series 1");
//        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        for (int i = 0;i<speedData.size();i++) {
//            Date date = new Date(speedData.get(i).getTimestamp().longValue());
//            String dateString = sfd.format(date);
//            series.set(dateString, speedData.get(i).getValue());
//        }
//        model.addSeries(series);
//
//        return model;

        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<GraphAxes> graphs = new ArrayList<>();
        List<DataMap> dataProvider = new ArrayList<>();
        long lastFrame = 0;
        for (int i = 0; i < speedData.size(); i++) {
            long x;
            if ((x = speedData.get(i).getTimestamp() - lastFrame) > 10000) {
                GraphAxes graph = new GraphAxes.GraphAxesBuilder()
                        .setId("speed" + i)
                        .setLineThickness(2)
                        .setValueField("y" + i)
                        .setType(GraphAxes.Type.LINE)  
                        
                        .setBalloonText("<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> km/h [[additional]]</span>")
                        .setFillColors(new Color(255, 0, 0))
                        .build();
                graphs.add(graph);
                
            }
            lastFrame = speedData.get(i).getTimestamp();
            DataMap map = new DataMap();
            map.put(graphs.get(graphs.size() - 1).getValueField(), speedData.get(i).getValue());
            Date date = new Date(speedData.get(i).getTimestamp().longValue());
            map.put("date", sfd.format(date));
            dataProvider.add(map);
        }
        SerialAmChartModel model = new SerialAmChartModel.SerialAmChartModelBuilder()
                .setTheme(AmChart.Theme.LIGHT)
                .setGraphs(graphs)
                .setLanguage(Locale.forLanguageTag("pl"))
                .setDataDateFormat("YYYY-MM-DD HH:NN:SS")
                .setChartCursor(new ChartCursor.ChartCursorBuilder()
                        .setValueBalloonsEnabled(Boolean.TRUE)
                        .setValueLineEnabled(Boolean.TRUE)
                        .setCategoryBalloonDateFormat("HH:NN:SS ")
                        .build())
                .setCategoryField("date")
                .setCategoryAxes(new CategoryAxis.CategoryAxisBuilder()
                        .setParseDates(Boolean.TRUE)
                        .setMinorGridEnabled(Boolean.TRUE)
                        .setMinPeriod(Time.Period.SECONDS)
                        .setMinorGridEnabled(Boolean.TRUE)
                        .build())
                .setChartScrollbar(new ChartScrollbar.ChartScrollbarBuilder()
                        .setOffset(15)
                        .setAutoGridCount(Boolean.TRUE)
                        .build()
                )
                .setValueScrollBar(new ChartScrollbar.ChartScrollbarBuilder()
                        .setAutoGridCount(Boolean.TRUE)
                        .setOffset(15)
                        .build())
                .setLegend(new Legend.LegendBuilder()
                        .setAutoMargin(Boolean.TRUE)
                        .setPosition(Position.simplePosition.BOTTOM).setUseGraphSettings(Boolean.TRUE)
                        .build())
                .build();
        model.updateData(dataProvider);

        return model;
    }

    public static SerialAmChartModel initRPMModel(List<Rpm> rpmData) {        
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<GraphAxes> graphs = new ArrayList<>();
        List<DataMap> dataProvider = new ArrayList<>();
        long lastFrame = 0;
        for (int i = 0; i < rpmData.size(); i++) {
            long x;
            if ((x = rpmData.get(i).getTimestamp() - lastFrame) > 10000) {
                GraphAxes graph = new GraphAxes.GraphAxesBuilder()
                        .setId("rpm" + i)
                        .setLineThickness(2)
                        .setValueField("y" + i)
                        .setType(GraphAxes.Type.LINE)
                        .setBalloonText("<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> RPM [[additional]]</span>")
                        .setFillColors(new Color(255, 0, 0))
                        .build();
                graphs.add(graph);
                
            }
            lastFrame = rpmData.get(i).getTimestamp();
            DataMap map = new DataMap();
            map.put(graphs.get(graphs.size() - 1).getValueField(), rpmData.get(i).getValue());
            Date date = new Date(rpmData.get(i).getTimestamp());
            map.put("date", sfd.format(date));
            dataProvider.add(map);
        }
        SerialAmChartModel model = new SerialAmChartModel.SerialAmChartModelBuilder()
                .setTheme(AmChart.Theme.LIGHT)
                .setGraphs(graphs)
                .setLanguage(Locale.forLanguageTag("pl"))
                .setDataDateFormat("YYYY-MM-DD HH:NN:SS")
                .setCategoryField("date")
                .setChartCursor(new ChartCursor.ChartCursorBuilder()
                        .setValueBalloonsEnabled(Boolean.TRUE)
                        .setValueLineEnabled(Boolean.TRUE)
                        .setCategoryBalloonDateFormat("HH:NN:SS ")
                        .build())
                .setCategoryAxes(new CategoryAxis.CategoryAxisBuilder()
                        .setParseDates(Boolean.TRUE)
                        .setMinorGridEnabled(Boolean.TRUE)
                        .setMinPeriod(Time.Period.SECONDS)
                        .build())
                .setChartScrollbar(new ChartScrollbar.ChartScrollbarBuilder()
                        .setOffset(15)
                        .setAutoGridCount(Boolean.TRUE)
                        .build()                )
                .setValueScrollBar(new ChartScrollbar.ChartScrollbarBuilder()
                        .setAutoGridCount(Boolean.TRUE)
                        .setOffset(15)
                        .build())
                .setLegend(new Legend.LegendBuilder()
                        .setAutoMargin(Boolean.TRUE)
                        .setPosition(Position.simplePosition.BOTTOM).setUseGraphSettings(Boolean.TRUE)
                        .build())
                .build();

        model.updateData(dataProvider);

        return model;
    }

    public static GanttAmChartModel initTroubleCodesChartModel(Date routeStartDate, List<TroubleCodes> troubleCodes) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Set<String> codes = new HashSet<>();
        for (TroubleCodes tc : troubleCodes) {
            codes.add(tc.getCode());
        }
        List<GanttDataProvider> gantDataList = new ArrayList<>();
        for (String s : codes) {
            List<TroubleCodes> singleCodeEntries = troubleCodes.stream()
                    .filter(object -> object.getCode().equals(s))
                    .sorted(Comparator.comparing(TroubleCodes::getTimestamp))
                    .collect(Collectors.toList());
            List<DateSegment> seriesSegments = new ArrayList<>();
            for (int i = 0; i < singleCodeEntries.size(); i += 2) {
                DateSegment ds = new DateSegment.DateSegmentBuilder()
                        .setStart(sdf.format(new Date(singleCodeEntries.get(i).getTimestamp())))
                        .setEnd(sdf.format(new Date(singleCodeEntries.get(i + 1).getTimestamp())))
                        .build();
                seriesSegments.add(ds);
            }
            gantDataList.add(new GanttDataProvider(s, seriesSegments));
        }
        Graph graph = new Graph.GraphBuilder()
                .setFillAlphas(1.0)
                .setLineAlpha(1.0)
                .setLineColor(new Color("#000000"))
                .setBalloonText("<b>Przedział</b>: [[open]] - [[value]]")
                .setDateFormat("JJ:NN:SS")
                .setFillColorsField("color")
                .build();

        GanttAmChartModel ganttModel = new GanttAmChartModel.GanttAmChartModelBuilder()
                .setStartDate(sdf.format(routeStartDate))
                .setDataDateFormat("YYYY-MM-DD HH:NN:SS")
                .setChartScrollbar(new ChartScrollbar.ChartScrollbarBuilder()
                        .setOffset(15)
                        .setAutoGridCount(Boolean.TRUE)
                        .build()
                )
                .setValueScrollBar(new ChartScrollbar.ChartScrollbarBuilder()
                        .setAutoGridCount(Boolean.TRUE)
                        .setOffset(15)
                        .build())
                .setPeriod(Time.Period.SECONDS)
                .setDataProvider(gantDataList)
                .setGraph(graph)
                .setValueAxis(new ValueAxis.ValueAxisBuilder()
                        .setType(ValueAxis.Type.DATE)
                        .build())
                .setRotate(Boolean.TRUE)
                .build();

        return ganttModel;
    }
}
