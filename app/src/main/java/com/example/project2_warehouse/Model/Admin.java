package com.example.project2_warehouse.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Admin {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("User")
    @Expose
    private String user;
    @SerializedName("Password")
    @Expose
    private String password;

    public String getID() {
    return iD;
    }

    public void setID(String iD) {
    this.iD = iD;
    }

    public String getUser() {
    return user;
    }

    public void setUser(String user) {
    this.user = user;
    }

    public String getPassword() {
    return password;
    }

    public void setPassword(String password) {
    this.password = password;
    }

}