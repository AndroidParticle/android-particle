package com.example.my.myapplication.IOT.server.models;

import java.io.Serializable;

/**
 * Created by MY on 6/20/2017.
 */
public class Device implements Serializable {
    private static final long serialVersionUID = 0L;
    private String deviceId;
    private String nameDevice;
    private boolean active;
    private String location;
    private double latitude;
    private double longitude;
    private String description;
    private String id;
    private String typeId;
    private String accountId;
    private String KeyThingspeak;

    public Device() {
    }

    public Device(String deviceId, String nameDevice, boolean active, String location, double latitude, double longgitude, String description,
                  String id, String typeId, String accountId, String keyThingspeak) {
        this.deviceId = deviceId;
        this.nameDevice = nameDevice;
        this.active = active;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longgitude;
        this.description = description;
        this.id = id;
        this.typeId = typeId;
        this.accountId = accountId;
        this.KeyThingspeak = keyThingspeak;
    }

    public void setNameDevice(String nameDevice) {
        this.nameDevice = nameDevice;
    }

    public String getNameDevice() {
        return nameDevice;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKeyThingspeak(String keyThingspeak) {
        KeyThingspeak = keyThingspeak;
    }


    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getKeyThingspeak() {
        return KeyThingspeak;
    }

    public String getAccountId() {
        return accountId;
    }

    public boolean isActive() {
        return active;
    }
}
