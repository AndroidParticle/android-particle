package com.example.my.myapplication.IOT.server.service;

import com.example.my.myapplication.IOT.server.models.Enviroment;
import com.example.my.myapplication.IOT.server.models.EnviromentCurrent;
import com.example.my.myapplication.IOT.server.models.Thingspeak;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by MY on 6/20/2017.
 */
public class ServiceThingspeak {
    public ServiceThingspeak() {
    }

    public ArrayList<Thingspeak> getListThingSpeak(final String deviceID) throws ExecutionException, InterruptedException {
        final DetailServiceGetListThingspeak detailServiceGetListThingspeak = new DetailServiceGetListThingspeak();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailServiceGetListThingspeak.execute(deviceID);
            }
        });
        thread.start();
        return detailServiceGetListThingspeak.get();
    }
}
