package com.example.my.myapplication.serverTest.service;


import com.example.my.myapplication.IOT.server.models.Account;

import java.util.concurrent.ExecutionException;

public class ServiceRegistry {
    public ServiceRegistry(){

    }
    public boolean taoTaiKhoan(final Account user) throws ExecutionException, InterruptedException {
        final DetailServiceRegistry detail = new DetailServiceRegistry();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detail.execute(user);
            }
        });
        thread.start();
        return detail.get();
    }
}

