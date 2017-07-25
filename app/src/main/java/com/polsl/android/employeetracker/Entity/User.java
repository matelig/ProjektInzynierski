package com.polsl.android.employeetracker.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by m_lig on 24.07.2017.
 */

@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;

    @Unique
    private String login;

    @NotNull
    private String password;

    @Generated(hash = 1850853155)
    public User(Long id, String login, @NotNull String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    @Generated(hash = 586692638)
    public User() {
    }


    public Long getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
