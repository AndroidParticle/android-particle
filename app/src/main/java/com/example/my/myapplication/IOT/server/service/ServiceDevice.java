package com.example.my.myapplication.IOT.server.service;

import com.example.my.myapplication.IOT.server.models.Account;
import com.example.my.myapplication.IOT.server.models.Device;
import com.example.my.myapplication.IOT.server.models.EnviromentCurrent;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by MY on 6/20/2017.
 */
public class ServiceDevice {

    public ServiceDevice() {

    }

    public ArrayList<Device> getListDevice(final String accountId) throws ExecutionException, InterruptedException {
        final DetailServiceGetListDevice detailServiceGetListDevice = new DetailServiceGetListDevice();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailServiceGetListDevice.execute(accountId);
            }
        });
        thread.start();
        return detailServiceGetListDevice.get();
    }

    public Device getDeviceInfo(final String accountId, final  String deviceID) throws ExecutionException, InterruptedException {
        final DetailServiceGetDeviceInfo detailServiceGetDeviceInfo = new DetailServiceGetDeviceInfo();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailServiceGetDeviceInfo.execute(accountId,deviceID);
            }
        });
        thread.start();
        return detailServiceGetDeviceInfo.get();
    }
    public boolean createDevice(final Device device) throws ExecutionException, InterruptedException {
        final DetailServiceCreateDevice detailServiceCreateDevice = new DetailServiceCreateDevice();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailServiceCreateDevice.execute(device);
            }
        });
        thread.start();
        return detailServiceCreateDevice.get();
    }

    public boolean editDevice(final Device device) throws ExecutionException, InterruptedException {
        final DetailServiceEditDevice detailServiceEditDevice = new DetailServiceEditDevice();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailServiceEditDevice.execute(device);
            }
        });
        thread.start();
        return detailServiceEditDevice.get();
    }
    public boolean deleteDevice(final String deviceId) throws ExecutionException, InterruptedException {
        final DetailServiceDeleteDevice detailServiceDeleteDevice = new DetailServiceDeleteDevice();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailServiceDeleteDevice.execute(deviceId);
            }
        });
        thread.start();
        return detailServiceDeleteDevice.get();
    }

}
