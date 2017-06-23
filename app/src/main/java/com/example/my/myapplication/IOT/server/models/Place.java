package com.example.my.myapplication.IOT.server.models;

import java.io.Serializable;

/**
 * Created by MY on 6/21/2017.
 */
public class Place implements Serializable {
    private Device deviceInfo;
    private int flag;
    private boolean active;
    public  Place(){}
    public Place(Device device, int flag, boolean active){
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
