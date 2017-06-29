package com.example.my.myapplication.IOT.server.service;

import com.example.my.myapplication.IOT.server.models.Thingspeak;
import com.example.my.myapplication.IOT.server.models.TypeParticle;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by MY on 6/20/2017.
 */
public class ServiceType {
    public ServiceType() {
    }


    public ArrayList<TypeParticle> getListType() throws ExecutionException, InterruptedException {
        final DetailServiceGetListType detailServiceGetListType = new DetailServiceGetListType();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailServiceGetListType.execute();
            }
        });
        thread.start();
        return detailServiceGetListType.get();
    }
}
