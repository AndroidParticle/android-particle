package com.example.my.myapplication.IOT.server.service;

import com.example.my.myapplication.IOT.server.models.Enviroment;
import com.example.my.myapplication.IOT.server.models.EnviromentCurrent;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by MY on 6/20/2017.
 */
public class ServiceEnv {
    public ServiceEnv() {
    }

    public EnviromentCurrent getEnviromentCurrent(final String deviceID) throws ExecutionException, InterruptedException {
        final DetailServiceGetEnvCurrent detailServiceGetEnvCurrent = new DetailServiceGetEnvCurrent();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailServiceGetEnvCurrent.execute(deviceID);
            }
        });
        thread.start();
        return detailServiceGetEnvCurrent.get();
    }

    public ArrayList<Enviroment> getListEnviroment(final String deviceID) throws ExecutionException, InterruptedException {
        final DetailServiceGetListEnv detailServiceGetListEnv = new DetailServiceGetListEnv();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailServiceGetListEnv.execute(deviceID);
            }
        });
        thread.start();
        return detailServiceGetListEnv.get();
    }
}
