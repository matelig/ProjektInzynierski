package com.polsl.android.employeetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.polsl.android.employeetracker.Activity.RouteMonthList;
import com.polsl.android.employeetracker.Entity.RouteData;
import com.polsl.android.employeetracker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by m_lig on 11.09.2017.
 */

public class RouteListYearAdapter extends RecyclerView.Adapter<RouteListYearAdapter.YearViewHolder>{
    String[] mDataset = {"Data", "In", "Adapter"};
    private List<String> years;
    private Context context;
    private Toast toast;

    public RouteListYearAdapter(List<String> years, Context context) {
        this.years = years;
        this.context = context;
    }

    @Override
    public RouteListYearAdapter.YearViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemTrack = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.route_list_years, viewGroup, false);
        RouteListYearAdapter.YearViewHolder viewHolder = new RouteListYearAdapter.YearViewHolder(itemTrack);
        viewHolder.view = itemTrack;
        viewHolder.dateYearView = (TextView) itemTrack.findViewById(R.id.year_text);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RouteListYearAdapter.YearViewHolder holder, int position) {
        String name = years.get(position);
        holder.dateYearView.setText(name);
        holder.position = position;

        holder.view.setOnClickListener(v -> {
            Intent intent = new Intent(context, RouteMonthList.class);
            intent.putExtra("year",years.get(position));
            context.startActivity(intent);
        });
    }

    public void refresh(List<String> years) {
        this.years = years;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    class YearViewHolder extends RecyclerView.ViewHolder {
        TextView dateYearView;
        View view;
        int position;

        public YearViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }
}
