package com.example.my.myapplication.IOT.server.models;

import java.util.Date;

/**
 * Created by MY on 6/20/2017.
 */
public class Enviroment {
    /*{
            "tempC": 26,
            "tempK": 299,
            "tempF": 78,
            "dewPoint": 20,
            "heatIndex": 27,
            "humidity": 72,
            "pressure": 100190,
            "lightLevel": 51,
            "datedCreated": "2017-06-18T07:21:25.876Z",
            "deviceIdParticle": "2e0027001247343339383037",
            "id": "594629f647492d00110c15aa",
            "deviceId": "5945c01f0bf432324cccf6ad"
        }*/
    private int tempC;
    private int tempK;
    private int tempF;
    private int dewpoint;
    private int heatIndex;
    private int humidity;
    private int pressure;
    private int lightLevel;
    private Date datedCreated;
    private String deviceIdParticle;
    private String id;
    private String deviceId;

    public Enviroment() {

    }

    public Date getDatedCreated() {
        return datedCreated;
    }

    public String getId() {
        return id;
    }

    public int getTempC() {
        return tempC;
    }
}
