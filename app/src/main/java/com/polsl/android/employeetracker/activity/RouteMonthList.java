package com.polsl.android.employeetracker.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.application.CarApp;
import com.polsl.android.employeetracker.entity.DaoMaster;
import com.polsl.android.employeetracker.entity.DaoSession;
import com.polsl.android.employeetracker.entity.RouteData;
import com.polsl.android.employeetracker.entity.RouteDataDao;
import com.polsl.android.employeetracker.entity.User;
import com.polsl.android.employeetracker.helper.ApiHelper;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.adapter.RouteListMonthAdapter;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;

public class RouteMonthList extends AppCompatActivity {


    RouteDataDao routeDataDao;

    private List<RouteData> tracks = new ArrayList<>();
    private RouteListMonthAdapter tAdapter;
    private RecyclerView routeListView;
    private DaoSession daoSession;
    private int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_month_list);
        User user = Hawk.get(ApiHelper.USER);
        Long userId = user.getId();
        Intent intent = getIntent();
        year = Integer.parseInt(intent.getStringExtra("year"));
        daoSession = ((CarApp) getApplication()).getDaoSession();
        routeDataDao = daoSession.getRouteDataDao();
        ButterKnife.bind(this);
        tracks = routeDataDao.loadAll();
        RouteData routeData1 = new RouteData();
        routeData1.start();
        routeData1.finish();
        tracks.add(routeData1);
        for (int i = tracks.size() - 1; i >= 0; i--) {
            if (tracks.get(i).getEndDate() == null || tracks.get(i).getUserId() != userId) {
                tracks.remove(i);
            }
        }
        Set<Integer> months = new HashSet<>();
        for (int i = tracks.size() - 1; i >= 0; i--) {
            Date startDate = new Date(tracks.get(i).getStartDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            int routeYear = cal.get(Calendar.YEAR);
            if (routeYear == year) {
                months.add(cal.get(Calendar.MONTH));
            }

        }
        List<String> monthNames = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            if (months.contains(i)) {
                monthNames.add(ApiHelper.monthNames[i]);
            }
        }
        routeListView = (RecyclerView) findViewById(R.id.route_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        routeListView.setLayoutManager(layoutManager);
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(routeListView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.vertical_list_divider);
        verticalDecoration.setDrawable(verticalDivider);
        routeListView.addItemDecoration(verticalDecoration);
        tAdapter = new RouteListMonthAdapter(monthNames, RouteMonthList.this, year);
        routeListView.setAdapter(tAdapter);
        routeListView.invalidate();
    }

}
