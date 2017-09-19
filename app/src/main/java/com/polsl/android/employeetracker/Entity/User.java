package com.polsl.android.employeetracker.Entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by m_lig on 24.07.2017.
 */

@Entity
public class User {

    @SerializedName("idUser")
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private String pesel;

    @NotNull
    private String password;

    @Generated(hash = 44559750)
    public User(Long id, @NotNull String name, @NotNull String surname,
                String pesel, @NotNull String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.password = password;
    }

    @Generated(hash = 586692638)
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
