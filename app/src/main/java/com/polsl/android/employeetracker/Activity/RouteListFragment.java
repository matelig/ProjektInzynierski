package com.polsl.android.employeetracker.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.polsl.android.employeetracker.Entity.DaoMaster;
import com.polsl.android.employeetracker.Entity.DaoSession;
import com.polsl.android.employeetracker.Entity.RouteData;
import com.polsl.android.employeetracker.Entity.RouteDataDao;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.adapter.RouteListAdapter;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RouteListFragment extends Fragment {


    RouteDataDao routeDataDao;

    private List<RouteData> tracks = new ArrayList<>();
    private RouteListAdapter tAdapter;
    private RecyclerView routeListView;
    private Toast message;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public RouteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_route_list, container, false);
        routeListView = (RecyclerView) rootView.findViewById(R.id.route_recycler);

//        setContentView(R.layout.activity_route_list);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(rootView.getContext(), "main-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        routeDataDao = daoSession.getRouteDataDao();
        ButterKnife.bind(rootView);
        tracks = routeDataDao.loadAll();
        for (int i = tracks.size() - 1; i >= 0; i--) {
            if (tracks.get(i).getEndDate() == null) {
                tracks.remove(i);
            }
        }
        RouteData routeData = new RouteData();
        routeData.start();
        routeData.finish();
        tracks.add(routeData);
//        routeListView = (RecyclerView) findViewById(R.id.route_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        routeListView.setLayoutManager(layoutManager);
        tAdapter = new RouteListAdapter(tracks, rootView.getContext());
        routeListView.setAdapter(tAdapter);
//        // Inflate the layout for this fragment
        return rootView;
    }

}
