package com.example.my.myapplication.serverTest.service;


import com.example.my.myapplication.serverTest.models.BaiViet;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ServiceLoadData {
    public ServiceLoadData() {

    }

    public List<BaiViet> loadDSBaiViet() throws ExecutionException, InterruptedException {
        final DetailServiceLoadDSDiaDiem detail = new DetailServiceLoadDSDiaDiem();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detail.execute();
            }
        });
        thread.start();
        return detail.get();
    }


    public List<BaiViet> loadBST(final String idUser) throws ExecutionException, InterruptedException {
        final DetailServiceLoadBST detail = new DetailServiceLoadBST();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detail.execute(idUser);
            }
        });
        thread.start();
        return detail.get();
    }
}
