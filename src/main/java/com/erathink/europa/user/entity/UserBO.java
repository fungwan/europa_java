package com.erathink.europa.user.entity;

/**
 * Created by fengyun on 2017/12/17.
 */
public class UserBO implements java.io.Serializable{

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String userName;
    String password;

}
