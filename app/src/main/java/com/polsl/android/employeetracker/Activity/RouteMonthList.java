package com.polsl.android.employeetracker.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.Entity.DaoMaster;
import com.polsl.android.employeetracker.Entity.DaoSession;
import com.polsl.android.employeetracker.Entity.RouteData;
import com.polsl.android.employeetracker.Entity.RouteDataDao;
import com.polsl.android.employeetracker.Entity.User;
import com.polsl.android.employeetracker.Helper.ApiHelper;
import com.polsl.android.employeetracker.R;
import com.polsl.android.employeetracker.adapter.RouteListAdapter;
import com.polsl.android.employeetracker.adapter.RouteListMonthAdapter;
import com.polsl.android.employeetracker.adapter.RouteListYearAdapter;

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
    private Toast message;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_month_list);
//        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
//        Long userId = prefs.getLong(ApiHelper.USER_ID,0);
        User user = Hawk.get(ApiHelper.USER);
        Long userId = user.getId();
        Intent intent = getIntent();
        year = Integer.parseInt(intent.getStringExtra("year"));
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "main-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        routeDataDao = daoSession.getRouteDataDao();
        ButterKnife.bind(this);
        tracks = routeDataDao.loadAll();
        RouteData routeData1 = new RouteData();
        routeData1.start();
        routeData1.finish();
        tracks.add(routeData1);
        for (int i = tracks.size() - 1; i >= 0; i--) {
            if (tracks.get(i).getEndDate() == null|| tracks.get(i).getUserId()!=userId) {
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
                monthNames.add(ApiHelper.monthNames[i - 1]);
            }
        }
        routeListView = (RecyclerView) findViewById(R.id.route_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        routeListView.setLayoutManager(layoutManager);
        tAdapter = new RouteListMonthAdapter(monthNames, RouteMonthList.this, year);
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
}
