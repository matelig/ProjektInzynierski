/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polsl.trackerportal.util;

import com.polsl.trackerportal.database.entity.Rpm;
import com.polsl.trackerportal.database.entity.Speed;
import com.polsl.trackerportal.database.entity.TroubleCodes;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author m_lig
 */
public class ChartModeler {

    public static int SERIES_COUNT = 0;    

    public static JSONArray initSpeedProvider(List<Speed> speedData) {
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONArray dataProvider = new JSONArray();
        long lastFrame = 0;
        int graphID = 0;
        for (int i = 0; i < speedData.size(); i++) {
            long x;
            if ((x = speedData.get(i).getTimestamp() - lastFrame) > 10000) {
                graphID++;
            }
            lastFrame = speedData.get(i).getTimestamp();
            JSONObject singleObject = new JSONObject();
            Date date = new Date(speedData.get(i).getTimestamp().longValue());
            singleObject.put("date", sfd.format(date));
            singleObject.put("y" + graphID, speedData.get(i).getValue());
            dataProvider.put(singleObject);
        }
        SERIES_COUNT = graphID;
        return dataProvider;
    }

    public static JSONArray initRPMProvider(List<Rpm> rpmData) {
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONArray dataProvider = new JSONArray();
        long lastFrame = 0;
        int graphID = 0;
        for (int i = 0; i < rpmData.size(); i++) {
            long x;
            if ((x = rpmData.get(i).getTimestamp() - lastFrame) > 10000) {
                graphID++;
            }
            lastFrame = rpmData.get(i).getTimestamp();
            JSONObject singleObject = new JSONObject();
            Date date = new Date(rpmData.get(i).getTimestamp().longValue());
            singleObject.put("date", sfd.format(date));
            singleObject.put("y" + graphID, rpmData.get(i).getValue());
            dataProvider.put(singleObject);
        }
        SERIES_COUNT = graphID;
        return dataProvider;
    }
    
    public static JSONArray initTroubleCodesProvider(List<TroubleCodes> troubleCodes) {
        JSONArray provider = new JSONArray();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Set<String> codes = new HashSet<>();
        for (TroubleCodes tc : troubleCodes) {
            codes.add(tc.getCode());
        }
        for (String s : codes) {
            List<TroubleCodes> singleCodeEntries = troubleCodes.stream()
                    .filter(object -> object.getCode().equals(s))
                    .sorted(Comparator.comparing(TroubleCodes::getTimestamp))
                    .collect(Collectors.toList());
            JSONObject singleObject = new JSONObject();
            JSONArray segments = new JSONArray();
            for (int i = 0; i < singleCodeEntries.size(); i += 2) {
                JSONObject singleSerie = new JSONObject();
                singleSerie.put("start", sdf.format(new Date(singleCodeEntries.get(i).getTimestamp())));
                singleSerie.put("end", sdf.format(new Date(singleCodeEntries.get(i + 1).getTimestamp())));
                segments.put(singleSerie);
            }
            singleObject.put("category", s);
            singleObject.put("segments", segments);
            provider.put(singleObject);
        }

        return provider;
    }    
}
