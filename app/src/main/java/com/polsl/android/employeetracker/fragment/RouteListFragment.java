package com.polsl.android.employeetracker.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.application.CarApp;
import com.polsl.android.employeetracker.entity.DaoMaster;
import com.polsl.android.employeetracker.entity.DaoSession;
import com.polsl.android.employeetracker.entity.RouteData;
import com.polsl.android.employeetracker.entity.RouteDataDao;
import com.polsl.android.employeetracker.entity.User;
import com.polsl.android.employeetracker.helper.ApiHelper;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.adapter.RouteListYearAdapter;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Calendar;
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
        User user = Hawk.get(ApiHelper.USER);
        Long userId = user.getId();
        Set<Integer> usedYears = new HashSet<>();
        daoSession = ((CarApp) getActivity().getApplication()).getDaoSession();
        routeDataDao = daoSession.getRouteDataDao();
        ButterKnife.bind(rootView);
        tracks = routeDataDao.loadAll();
        for (int i = tracks.size() - 1; i >= 0; i--) {
            tracks.get(i).setToSend(false);
            if (tracks.get(i).getEndDate() == null || tracks.get(i).getUserId() != userId) {
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        routeListView.setLayoutManager(layoutManager);
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(routeListView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable verticalDivider = ContextCompat.getDrawable(routeListView.getContext(), R.drawable.vertical_list_divider);
        verticalDecoration.setDrawable(verticalDivider);
        routeListView.addItemDecoration(verticalDecoration);
        yearAdapter = new RouteListYearAdapter(usedYearsNames, rootView.getContext());
        routeListView.setAdapter(yearAdapter);
        return rootView;
    }

}
