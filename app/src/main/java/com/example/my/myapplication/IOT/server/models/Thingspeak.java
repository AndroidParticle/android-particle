package com.example.my.myapplication.IOT.server.models;

/**
 * Created by MY on 6/20/2017.
 */
public class Thingspeak {
    /*    "name": "string",
            "content": "string",
            "description": "string",
            "active": true,
            "id": "59443370a0c916099c26a99b",
            "deviceId": "594430969f2878375076f3d5" */
    private String name;
    private String content;
    private String description;
    private boolean active;
    private String id;
    private String deviceId;

    public Thingspeak() {
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
