package com.polsl.android.employeetracker.Activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.Entity.DaoMaster;
import com.polsl.android.employeetracker.Entity.DaoSession;
import com.polsl.android.employeetracker.Entity.RouteData;
import com.polsl.android.employeetracker.Entity.RouteDataDao;
import com.polsl.android.employeetracker.Entity.User;
import com.polsl.android.employeetracker.Helper.ApiHelper;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.adapter.RouteListAdapter;
import com.polsl.android.employeetracker.adapter.RouteListYearAdapter;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteListFragment extends Fragment {


    RouteDataDao routeDataDao;

    private List<RouteData> tracks = new ArrayList<>();
    private RouteListYearAdapter yearAdapter;
    private RecyclerView routeListView;
    private DaoSession daoSession;

    public RouteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_route_list, container, false);
        routeListView = (RecyclerView) rootView.findViewById(R.id.route_recycler);
//        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
//        Long userId = prefs.getLong(ApiHelper.USER_ID,0);
        User user = Hawk.get(ApiHelper.USER);
        Long userId = user.getId();
        Set<Integer> usedYears = new HashSet<>();
//        setContentView(R.layout.activity_route_list);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(rootView.getContext(), "main-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        routeDataDao = daoSession.getRouteDataDao();
        ButterKnife.bind(rootView);
        tracks = routeDataDao.loadAll();
        for (int i = tracks.size() - 1; i >= 0; i--) {
            tracks.get(i).setToSend(false);
            if (tracks.get(i).getEndDate() == null || tracks.get(i).getUserId()!=userId) {
                tracks.remove(i);
            }
        }
        for (RouteData routeData : tracks) {
            Date startDate = new Date(routeData.getStartDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            usedYears.add(cal.get(Calendar.YEAR));
        }
        List<Integer> tempYears = new ArrayList<>();
        tempYears.addAll(usedYears);
        Collections.sort(tempYears);
        List<String> usedYearsNames = new ArrayList<>();
        for (int year : tempYears) {
            usedYearsNames.add(Integer.toString(year));
        }
//        routeListView = (RecyclerView) findViewById(R.id.route_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        routeListView.setLayoutManager(layoutManager);
        //tAdapter = new RouteListAdapter(tracks, rootView.getContext());
        yearAdapter = new RouteListYearAdapter(usedYearsNames,rootView.getContext());
        routeListView.setAdapter(yearAdapter);
//        // Inflate the layout for this fragment
        return rootView;
    }

}
