package com.polsl.android.employeetracker.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by m_lig on 24.07.2017.
 */

public class User {
    @Expose
    @SerializedName("idUser")
    private Long id;
    @Expose
    private String name;
    @Expose
    private String surname;
    @Expose
    private String pesel;
    @Expose
    private String password;


    public User(Long id, String name, String surname,
             String pesel, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.password = password;
    }

    public User() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPesel() {
        return this.pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
