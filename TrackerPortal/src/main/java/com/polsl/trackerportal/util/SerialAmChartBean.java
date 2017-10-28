/* 
   (c) Systemy Przetwarzania i Integracji Danych SPIID sp. z o.o.
       1:1 Realny obraz Twojej firmy
 */
package com.polsl.trackerportal.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import pl.spiid.lib.spiidcharts.events.ZoomEvent;
import pl.spiid.lib.spiidcharts.events.data.ZoomData;
import pl.spiid.lib.spiidcharts.models.SerialAmChartModel;
import pl.spiid.lib.spiidcharts.models.attributes.AmChart;
import pl.spiid.lib.spiidcharts.models.attributes.CategoryAxis;
import pl.spiid.lib.spiidcharts.models.attributes.ChartCursor;
import pl.spiid.lib.spiidcharts.models.attributes.ChartScrollbar;
import pl.spiid.lib.spiidcharts.models.attributes.Graph;
import pl.spiid.lib.spiidcharts.models.attributes.GraphAxes;
import pl.spiid.lib.spiidcharts.models.attributes.Legend;
import pl.spiid.lib.spiidcharts.models.attributes.ValueAxis;
import pl.spiid.lib.spiidcharts.models.data.DataMap;
import pl.spiid.lib.spiidcharts.models.enums.Position;
import pl.spiid.lib.spiidcharts.utils.RandomGenerator;

/**
 *
 * @author Damian Badura damian.badura@spiid.pl
 * @version 1.0
 * @since
 */
@ManagedBean
@SessionScoped
public class SerialAmChartBean {

    private SerialAmChartModel serialModel;
    private ArrayList<IntegerMinMax> valueList = new ArrayList<>();
    private RandomGenerator generator = new RandomGenerator(Boolean.TRUE);

    @PostConstruct
    public void init() {
        this.serialModel = initColumnChartWithDate();
        this.getValueList().add(new IntegerMinMax(10, 20));
    }

    public SerialAmChartModel initColumnChartWithDate() {
        RandomGenerator generator = new RandomGenerator(false);
        ArrayList<ValueAxis> vAxes = new ArrayList<>();

        ChartCursor cursor = new ChartCursor.ChartCursorBuilder()
                .setPan(Boolean.FALSE)
                .setValueLineEnabled(Boolean.FALSE)
                .setValueBalloonsEnabled(Boolean.TRUE)
                .setValueLineAlpha(0.0)
                .setCursorAlpha(0.2)
                .build();

        vAxes.add(new ValueAxis.ValueAxisBuilder()
                .setId("v1")
                .setAxisAlpha(0.0)
                .setPosition(Position.simplePosition.LEFT)
                .build());

        ArrayList<GraphAxes> graphs = new ArrayList<>();
        GraphAxes.Type type;
        Double fillAlpha;
        for (int j = 0; j < 4; j++) {
            if (j == 2) {
                type = GraphAxes.Type.SMOTTHED_LINE;
                fillAlpha = 0.0;
            } else {
                type = GraphAxes.Type.COLUMN;
                fillAlpha = generator.generateDouble(0.0, 0.9);
            }
            GraphAxes graph = new GraphAxes.GraphAxesBuilder()
                    .setId("v" + j)
                    .setBullet(Graph.Bullet.ROUND)
                    .setLineThickness(2)
                    .setValueField(generator.generateString(10))
                    .setType(type)
                    .setFillAlphas(fillAlpha)
                    .setFillColors(generator.generateColor())
                    .build();
            graphs.add(graph);

        }

        SerialAmChartModel model = new SerialAmChartModel.SerialAmChartModelBuilder()
                .setTheme(AmChart.Theme.LIGHT)
                .setGraphs(graphs)
                .setLanguage(Locale.forLanguageTag("pl"))
                .setDataDateFormat("YYYY-MM-DD")
                .setCategoryField("date")
                .setCategoryAxes(new CategoryAxis.CategoryAxisBuilder()
                        .setParseDates(Boolean.TRUE)
                        .setMinorGridEnabled(Boolean.TRUE)
                        .build())
                .setChartCursor(cursor)
                .setChartScrollbar(new ChartScrollbar.ChartScrollbarBuilder()
                        .setOffset(15)
                        .build()
                )
                .setValueAxes(vAxes)
                .setValueScrollBar(new ChartScrollbar.ChartScrollbarBuilder()
                        .setAutoGridCount(Boolean.TRUE)
                        .setOffset(15)
                        .build())
                .setLegend(new Legend.LegendBuilder()
                        .setAutoMargin(Boolean.TRUE)
                        .setPosition(Position.simplePosition.BOTTOM).setUseGraphSettings(Boolean.TRUE)
                        .build())
                .build();
        

        return model;
    }

    /**
     * @return the serialModel
     */
    public SerialAmChartModel getSerialModel() {
        ArrayList<GraphAxes> graphs = (ArrayList<GraphAxes>) serialModel.getGraphs();
        ArrayList<DataMap> dataProvider = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 2000; i++) {
            GregorianCalendar date = new GregorianCalendar(2017, 8, i);
            DataMap map = new DataMap();
            int graphsSize = graphs.size();
            for (int j = 0; j < graphsSize; j++) {
                map.put(graphs.get(j).getValueField(), generator.generateNumber(5, 20));
            }
            map.put("date", formatter.format(date.getTime()));
            dataProvider.add(map);
        }
        this.serialModel.updateDataProvider(dataProvider);
        return this.serialModel;
    }

    public void serialAmChartAction(ZoomEvent zoomEvent) {
        if (zoomEvent == null) {
            return;
        }
        ZoomData data = zoomEvent.getData();
        if (data != null) {
            int graphsSize = this.serialModel.getGraphs().size();
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            ArrayList<String> graphsId = new ArrayList<>();
            for (GraphAxes graph : this.serialModel.getGraphs()) {
                graphsId.add(graph.getValueField());
            }
            ArrayList<DataMap> dataProvider = (ArrayList<DataMap>) this.serialModel.getDataProvider();
            Iterator<DataMap> iterator = dataProvider.listIterator(data.getStartIndex());
            DataMap dataMap;
            int value;
            for (int i = data.getStartIndex(); i < data.getLastIndex(); i++) {
                dataMap = iterator.next();
                for (int j = 0; graphsId.size() > j; j++) {
                    value = (Integer) dataMap.get(graphsId.get(j));
                    if (value > max) {
                        max = value;
                    }
                    if (value < min) {
                        min = value;
                    }
                }
            }
            System.out.println("start index:" + data.getStartIndex() + "min:" + min + "\n"
                    + "last idnex:" + data.getLastIndex() + " max:" + max);
            IntegerMinMax tabValue = this.valueList.get(0);
            tabValue.setMax(max);
            tabValue.setMin(min);
        }
    }

    /**
     * @return the valueList
     */
    public ArrayList<IntegerMinMax> getValueList() {
        return valueList;
    }

}
