package com.example.my.myapplication.IOT.server.service;

import com.example.my.myapplication.IOT.server.models.Thingspeak;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by MY on 6/20/2017.
 */
public class ServiceControl {
    public ServiceControl() {
    }


    public Boolean controlLed(final String deviceID,final  String control) throws ExecutionException, InterruptedException {
        final DetailServiceControlLed detailServiceControlLed = new DetailServiceControlLed();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailServiceControlLed.execute(deviceID,control);
            }
        });
        thread.start();
        return detailServiceControlLed.get();
    }
    public Boolean controlTimeDelay(final String deviceID,final  String control) throws ExecutionException, InterruptedException {
        final DetailServiceControlTimeDelay detailServiceControlTimeDelay = new DetailServiceControlTimeDelay();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailServiceControlTimeDelay.execute(deviceID,control);
            }
        });
        thread.start();
        return detailServiceControlTimeDelay.get();
    }
}
