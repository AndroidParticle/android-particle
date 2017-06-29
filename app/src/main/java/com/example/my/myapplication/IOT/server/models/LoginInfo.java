package com.example.my.myapplication.IOT.server.models;

import java.util.Date;

/**
 * Created by MY on 6/20/2017.
 */
public class LoginInfo {
    private String id;
    private int ttl;
    private Date created;
    private String userId;
    private Account user;
    public LoginInfo(){}

    @Override
    public String toString() {
        return super.toString();
    }

    public Date getCreated() {
        return created;
    }

    public Account getUser() {
        return user;
    }
}

