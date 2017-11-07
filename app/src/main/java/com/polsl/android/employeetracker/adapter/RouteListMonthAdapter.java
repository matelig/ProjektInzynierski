package com.polsl.android.employeetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.polsl.android.employeetracker.Activity.RouteListActivity;
import com.polsl.android.employeetracker.Activity.RouteMonthList;
import com.polsl.android.employeetracker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by m_lig on 11.09.2017.
 */

public class RouteListMonthAdapter extends RecyclerView.Adapter<RouteListMonthAdapter.MonthViewHolder> {
    String[] mDataset = {"Data", "In", "Adapter"};
    private List<String> months;
    private Context context;
    private Toast toast;
    private int year;

    public RouteListMonthAdapter(List<String> months, Context context, int year) {
        this.months = months;
        this.context = context;
        this.year = year;
    }

    @Override
    public RouteListMonthAdapter.MonthViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemTrack = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.route_list_years, viewGroup, false);
        RouteListMonthAdapter.MonthViewHolder viewHolder = new RouteListMonthAdapter.MonthViewHolder(itemTrack);
        viewHolder.view = itemTrack;
        viewHolder.dateMonthView = (TextView) itemTrack.findViewById(R.id.year_text);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RouteListMonthAdapter.MonthViewHolder holder, int position) {
        String name = months.get(position);
        holder.dateMonthView.setText(name);
        holder.position = position;

        holder.view.setOnClickListener(v -> {
            Intent intent = new Intent(context, RouteListActivity.class);
            intent.putExtra("year",year);
            intent.putExtra("month", months.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    class MonthViewHolder extends RecyclerView.ViewHolder {
        TextView dateMonthView;
        View view;
        int position;

        public MonthViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }
}
