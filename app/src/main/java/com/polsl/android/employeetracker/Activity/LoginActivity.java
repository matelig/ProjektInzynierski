package com.polsl.android.employeetracker.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.polsl.android.employeetracker.Helper.Command;
import com.polsl.android.employeetracker.R;

public class LoginActivity extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginButtonClick(View view) {
        //TODO: Sprawdzenie połączenia z internetem
        //TODO: Połączenie z bazą, sprawdzenie użytkownika czy istnieje, zapamiętanie jego Loginu i ID w prefsach
//        SharedPreferences prefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
//        prefs.edit().putString(Command.USER_NAME, authResponse.getAuthToken()).apply();
        Intent intent = new Intent(context,MainActivity.class);
        startActivity(intent);
    }

    public void offlineModeCLick(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        //alertDialogBuilder.setTitle("Your Title");

        // set dialog message
        alertDialogBuilder
                .setMessage(R.string.offline_mode_dialog)
                .setCancelable(false)
                .setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context,SlideActivityPager.class);
                        startActivity(intent);
                        //TODO: Zapamiętanie, że pracujemy offline
                    }
                })
                .setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


}
