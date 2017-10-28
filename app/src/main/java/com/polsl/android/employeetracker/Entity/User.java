package com.polsl.android.employeetracker.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by m_lig on 24.07.2017.
 */

public class User implements Parcelable {
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

    public static final Parcelable.Creator<User> CREATOR =
            new Creator<User>() {
                @Override
                public User createFromParcel(Parcel source) {
                    return new User(source);
                }

                @Override
                public User[] newArray(int size) {
                    return new User[size];
                }
            };


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



    public User(Parcel in) {
    this.id = in.readLong();
        this.name = in.readString();
        this.surname = in.readString();
        this.pesel = in.readString();
        this.password = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeString(this.pesel);
        dest.writeString(this.password);
    }
}
