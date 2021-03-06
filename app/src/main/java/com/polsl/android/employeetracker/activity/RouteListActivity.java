package com.polsl.android.employeetracker.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.application.CarApp;
import com.polsl.android.employeetracker.entity.DaoMaster;
import com.polsl.android.employeetracker.entity.DaoSession;
import com.polsl.android.employeetracker.entity.RouteData;
import com.polsl.android.employeetracker.entity.RouteDataDao;
import com.polsl.android.employeetracker.entity.User;
import com.polsl.android.employeetracker.helper.ApiHelper;
import com.polsl.android.employeetracker.helper.UploadStatus;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.RESTApi.RESTServicesEndpoints;
import com.polsl.android.employeetracker.RESTApi.RetrofitClient;
import com.polsl.android.employeetracker.RESTApi.Route;
import com.polsl.android.employeetracker.adapter.RouteListAdapter;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteListActivity extends AppCompatActivity {


    RouteDataDao routeDataDao;
    private Context context = this;
    private List<RouteData> tracks = new ArrayList<>();
    private RouteListAdapter tAdapter;
    private RecyclerView routeListView;
    private Toast message;
    private DaoMaster daoMaster;
    private DaoSession daoSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);
        Intent intent = getIntent();
        Integer year = intent.getIntExtra("year", 0);
        String monthName = intent.getStringExtra("month");
        User user = Hawk.get(ApiHelper.USER);
        Long userId = user.getId();
        int month = 0;
        for (int i = 1; i <= 12; i++) {
            if (monthName.equals(ApiHelper.monthNames[i])) {
                month = i;
                break;
            }
        }
        daoSession = ((CarApp) getApplication()).getDaoSession();
        routeDataDao = daoSession.getRouteDataDao();
        ButterKnife.bind(this);
        tracks = routeDataDao.loadAll();
        for (int i = tracks.size() - 1; i >= 0; i--) {
            tracks.get(i).setToSend(false);
            if (tracks.get(i).getEndDate() == null || tracks.get(i).getUserId() != userId) {
                tracks.remove(i);
            }
        }
        for (int i = tracks.size() - 1; i >= 0; i--) {
            Date startDate = new Date(tracks.get(i).getStartDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            int routeYear = cal.get(Calendar.YEAR);
            int routeMonth = cal.get(Calendar.MONTH);
            if (routeYear != year || routeMonth != month) {
                tracks.remove(i);
            }

        }
        routeListView = (RecyclerView) findViewById(R.id.route_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        routeListView.setLayoutManager(layoutManager);
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(routeListView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.vertical_list_divider);
        verticalDecoration.setDrawable(verticalDivider);
        routeListView.addItemDecoration(verticalDecoration);
        tAdapter = new RouteListAdapter(tracks, RouteListActivity.this);
        routeListView.setAdapter(tAdapter);
        routeListView.invalidate();
    }


    public void sendRoutesClick(View view) {
        RESTServicesEndpoints endpoints = RetrofitClient.getApiService();

        for (RouteData r : tracks) {
            if (r.getToSend()) {
                Route route = new Route(r.getStartDate(), r.getEndDate(), r.getUserId(), r.getVinNumber(), r.getLocationDataList());
                route.setRpmDataList(r.getRpmDataList());
                route.setSpeedDataList(r.getSpeedDataList());
                route.setTroubleCodesList(r.getTroubleCodesDataList());
                route.setFuelConsumptionRateData(r.getFuelConsumptionRateDataList());
                route.setFuelLevelData(r.getFuelLevelDataList());
                route.setOilTemperatureData(r.getOilTemperatureDataList());
                route.setRoadLength(r.getRoadLength());
                route.setEngineLoadCollection(r.getEngineLoadDataList());
                Call<ResponseBody> call = endpoints.create(route);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 204) {
                            r.setUploadStatus(UploadStatus.UPLOADED);
                            r.setToSend(false);
                            routeDataDao.update(r);
                            Toast.makeText(context, "Route " + r.getId() + " has been send.", Toast.LENGTH_SHORT).show();
                            tAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        }

    }
}
