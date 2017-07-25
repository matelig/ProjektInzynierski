package com.polsl.android.employeetracker.Activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.polsl.android.employeetracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AfterButton extends AppCompatActivity {

    @BindView(R.id.name)
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_button);
        ButterKnife.bind(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("deviceAddress","");
        this.name.setText(name);
    }
}
