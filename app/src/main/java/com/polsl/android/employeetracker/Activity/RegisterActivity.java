package com.polsl.android.employeetracker.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.polsl.android.employeetracker.Entity.DaoMaster;
import com.polsl.android.employeetracker.Entity.DaoSession;
import com.polsl.android.employeetracker.Entity.User;
import com.polsl.android.employeetracker.Entity.UserDao;
import com.polsl.android.employeetracker.R;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.login_input)
    EditText login;
    @BindView(R.id.password_input)
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    public void onButtonClick(View view) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "main-db");
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        UserDao userDao = daoSession.getUserDao();
        List<User> users = userDao.loadAll();
        boolean notUnique = false;
        for (User u : users) {
            if (u.getLogin().equals(login.getText().toString())) {
                Toast.makeText(this,"Wybrany użytkownik jest już zarejestrowany", Toast.LENGTH_SHORT).show();
                notUnique = true;
                break;
            }
        }
        if (!notUnique) {
            User user = new User();
            user.setLogin(login.getText().toString());
            user.setPassword(password.getText().toString());
            userDao.insert(user);
        }
    }

    public void OnListClicl(View view) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "main-db");
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        UserDao userDao = daoSession.getUserDao();
        List<User> users = userDao.loadAll();
        System.out.println("cos");
    }
}
