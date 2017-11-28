package com.polsl.android.employeetracker.util;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.polsl.android.employeetracker.helper.Constants;

/**
 * Created by m_lig on 27.11.2017.
 */

public class SpinnerListener implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Hawk.put("selectedSpinnerItem", position);

        switch (position) {
            case 0:
                Hawk.delete("frequency");
                Hawk.put("frequency", Constants.LOCATION_0);
                break;
            case 1:
                Hawk.delete("frequency");
                Hawk.put("frequency", Constants.LOCATION_1);
                break;
            case 2:
                Hawk.delete("frequency");
                Hawk.put("frequency", Constants.LOCATION_2);
                break;
            case 3:
                Hawk.delete("frequency");
                Hawk.put("frequency", Constants.LOCATION_3);
                break;
            case 4:
                Hawk.delete("frequency");
                Hawk.put("frequency", Constants.LOCATION_4);
                break;
            case 5:
                Hawk.delete("frequency");
                Hawk.put("frequency", Constants.LOCATION_5);
                break;
        }
        Log.v("hawk value", String.valueOf(Hawk.get("frequency", Constants.LOCATION_0)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
