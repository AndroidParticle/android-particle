package com.example.my.myapplication.IOT.server.models;

/**
 * Created by MY on 6/17/2017.
 */
public class Account {
    private String fullName;
    private boolean active;
    private boolean rule;
    private String email;
    private String password;
    private String id;

    public Account() {
    }

    public Account(String fullName, boolean active, boolean rule, String email, String password, String id) {
        this.fullName = fullName;
        this.active = active;
        this.rule = rule;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public Account(String username, boolean active, boolean rule, String email, String pass) {
        this.fullName = username;
        this.active = active;
        this.rule = rule;
        this.email = email;
        this.password = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isRule() {
        return rule;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRule(boolean rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

/* "fullName": "Mai Hữu Tài",
    "active": true,
    "rule": true,
    "email": "taimai0604@gmail.com",
    "id": "594427a18872d40011a9d344"*/