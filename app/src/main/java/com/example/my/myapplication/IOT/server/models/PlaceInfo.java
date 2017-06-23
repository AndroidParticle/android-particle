package com.example.my.myapplication.IOT.server.models;

import java.io.Serializable;

/**
 * Created by MY on 6/11/2017.
 */
public class PlaceInfo implements Serializable {
    private static final long serialVersionUID = 0L;

   /* private String rank;
    private String country;
    private String population;
    private int flag;

    public PlaceInfo(String rank, String country, String population,
                     int flag) {
        this.rank = rank;
        this.country = country;
        this.population = population;
        this.flag = flag;
    }

    public String getRank() {
        return this.rank;
    }

    public String getCountry() {
        return this.country;
    }

    public String getPopulation() {
        return this.population;
    }

    public int getFlag() {
        return this.flag;
    }*/

    private Device deviceInfo;
    private int flag;
    private boolean active;
    public  PlaceInfo(){}
    public PlaceInfo(Device device, int flag, boolean active){
        deviceInfo=device;
        this.flag=flag;
        this.active=active;
    }

    public Device getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(Device deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
