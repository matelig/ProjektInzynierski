package com.polsl.android.employeetracker.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.Entity.DaoMaster;
import com.polsl.android.employeetracker.Entity.DaoSession;
import com.polsl.android.employeetracker.Entity.LocationData;
import com.polsl.android.employeetracker.Entity.RouteData;
import com.polsl.android.employeetracker.Entity.RouteDataDao;
import com.polsl.android.employeetracker.Entity.User;
import com.polsl.android.employeetracker.Helper.ApiHelper;
import com.polsl.android.employeetracker.Helper.UploadStatus;
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
        Integer year = intent.getIntExtra("year",0);
        String monthName = intent.getStringExtra("month");
//        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
//        Long userId = prefs.getLong(ApiHelper.USER_ID,0);
        User user = Hawk.get(ApiHelper.USER);
        Long userId = user.getId();
        int month = 0;
        for (int i=1;i<=12;i++) {
            if (monthName.equals(ApiHelper.monthNames[i-1])) {
                month = i;
                break;
            }
        }
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "main-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        routeDataDao = daoSession.getRouteDataDao();
        ButterKnife.bind(this);
        tracks = routeDataDao.loadAll();
        for (int i = tracks.size() - 1; i >= 0; i--) {
            tracks.get(i).setToSend(false);
            if (tracks.get(i).getEndDate() == null|| tracks.get(i).getUserId()!=userId) {
                tracks.remove(i);
            }
        }
        for (int i = tracks.size()-1;i>=0;i--) {
            Date startDate = new Date(tracks.get(i).getStartDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            int routeYear = cal.get(Calendar.YEAR);
            int routeMonth = cal.get(Calendar.MONTH);
            if (routeYear != year || routeMonth!=month) {
                tracks.remove(i);
            }

        }
        routeListView = (RecyclerView) findViewById(R.id.route_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        routeListView.setLayoutManager(layoutManager);
        tAdapter = new RouteListAdapter(tracks, RouteListActivity.this);
        routeListView.setAdapter(tAdapter);
        routeListView.invalidate();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.list_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int clickedItemInd = item.getItemId();
//        switch (clickedItemInd) {
//            //Info icon
//            case R.id.btn_info:
//                Toast.makeText(this, R.string.list_info, Toast.LENGTH_LONG).show();
//                break;
//            //Back icon
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                break;
//        }
//        return true;
//    }

    //TODO match with actual activity(currently: matching with MainActivity)
//    public void onMenuItemMapClick(MenuItem w) {
//        Intent intent = new Intent(RouteListActivity.this, MapActivity.class);
//        startActivity(intent);
//    }

    public void onMenuItemListClick(MenuItem w) {

    }

    public void onMenuItemSendClick(MenuItem w) {
        message = Toast.makeText(this, "Wysle dane", Toast.LENGTH_SHORT);
        message.show();
    }

//    public void testClick(MenuItem w) {
//        Intent intent = new Intent(RouteListActivity.this, ExampleActivity.class);
//        startActivity(intent);
//    }

    private void prepareView() {

    }

    public void sendRoutesClick(View view) {
        RESTServicesEndpoints endpoints = RetrofitClient.getApiService();

        for (RouteData r : tracks) {
            if (r.getToSend()) {
                Route route = new Route(r.getStartDate(),r.getEndDate(),r.getUserId(),r.getVinNumber(),r.getLocationDataList());
                route.setRpmDataList(r.getRpmDataList());
                route.setSpeedDataList(r.getSpeedDataList());
                route.setTroubleCodesList(r.getTroubleCodesDataList());
                String json = new Gson().toJson(route);
                Call<ResponseBody> call = endpoints.create(route);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==204) {
                            r.setUploadStatus(UploadStatus.UPLOADED);
                            Toast.makeText(context,"Wysłano;o",Toast.LENGTH_SHORT).show();
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
