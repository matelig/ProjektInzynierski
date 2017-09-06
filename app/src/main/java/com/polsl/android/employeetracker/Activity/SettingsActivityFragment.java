package com.polsl.android.employeetracker.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polsl.android.employeetracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsActivityFragment extends Fragment {


    public SettingsActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_activity, container, false);
    }

}
