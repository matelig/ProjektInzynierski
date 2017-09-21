package com.polsl.android.employeetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.polsl.android.employeetracker.Activity.MapsActivity;
import com.polsl.android.employeetracker.Entity.DaoMaster;
import com.polsl.android.employeetracker.Entity.DaoSession;
import com.polsl.android.employeetracker.Entity.RouteData;
import com.polsl.android.employeetracker.Entity.RouteDataDao;
import com.polsl.android.employeetracker.Helper.ApiHelper;
import com.polsl.android.employeetracker.Helper.UploadStatus;
import com.polsl.android.employeetracker.R;

import org.greenrobot.greendao.database.Database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by m_lig on 27.07.2017.
 */

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.DataViewHolder>  {
    String[] mDataset = {"Data", "In", "Adapter"};
    private List<RouteData> tracks;
    private Context context;
    private Toast toast;

    public RouteListAdapter(List<RouteData> tracks, Context context) {
        this.tracks = tracks;
        this.context = context;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemTrack = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.route_list_item, viewGroup, false);
        DataViewHolder viewHolder = new DataViewHolder(itemTrack);
        viewHolder.view = itemTrack;
        viewHolder.dateItemView = (TextView) itemTrack.findViewById(R.id.date_text);
        viewHolder.descriptionItemView = (TextView) itemTrack.findViewById(R.id.description_text);
        viewHolder.durationItemView = (TextView) itemTrack.findViewById(R.id.duration_text);
        viewHolder.optionsItemView = (TextView) itemTrack.findViewById(R.id.list_options);
        viewHolder.checkBox = (CheckBox) itemTrack.findViewById(R.id.checkbox);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        RouteData info = tracks.get(position);

        holder.dateItemView.setText(dateFormat.format(info.getStartDate()));
        holder.descriptionItemView.setText("Route " + info.getId());
        holder.durationItemView.setText("Duration: " + info.calculateDuration());
        holder.checkBox.setOnCheckedChangeListener(null);

        if(info.getUploadStatus() == UploadStatus.UPLOADED)
            holder.checkBox.setEnabled(false);
        holder.position = position;

        holder.checkBox.setChecked(tracks.get(position).getToSend());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tracks.get(position).setToSend(isChecked);
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "main-db");
            Database db = helper.getWritableDb();
            DaoSession daoSession = new DaoMaster(db).newSession();
            RouteDataDao routeDataDao = daoSession.getRouteDataDao();
            routeDataDao.update(tracks.get(position));
        });

        holder.view.setOnClickListener(v -> {
            Intent intent = new Intent(context, MapsActivity.class);
            intent.putExtra(ApiHelper.ROUTE_ID, tracks.get(position).getId());
            context.startActivity(intent);
        });

        holder.optionsItemView.setOnClickListener(v -> {
            toast = Toast.makeText(context, "You clicked an menu of item " + tracks.get(position).getId(), Toast.LENGTH_SHORT);
            toast.show();
//            PopupMenu popup = new PopupMenu(context, holder.optionsItemView);
//            popup.inflate(R.menu.list_popup_menu);
//            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    switch (item.getItemId()) {
//                        case R.id.ready_to_send:
//                            //TODO: add some information about readiness to send
//                            toast = Toast.makeText(context, "Ready to send", Toast.LENGTH_SHORT);
//                            toast.show();
//                            break;
//                        case R.id.delete_route:
//                            tracks.get(position).delete();
//                            tracks.remove(position);
//                            notifyItemRemoved(position);
//                            notifyItemRangeChanged(position, tracks.size());
//                            toast = Toast.makeText(context, "Delete successful", Toast.LENGTH_SHORT);
//                            toast.show();
//                            break;
//                    }
//                    return false;
//                }
//            });
//            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }



    class DataViewHolder extends RecyclerView.ViewHolder {
        TextView dateItemView;
        TextView descriptionItemView;
        TextView durationItemView;
        TextView optionsItemView;
        CheckBox checkBox;
        View view;
        int position;

        public DataViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }
}