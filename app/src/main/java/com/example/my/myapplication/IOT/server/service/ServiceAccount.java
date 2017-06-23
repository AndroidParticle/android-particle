package com.example.my.myapplication.IOT.server.service;

import com.example.my.myapplication.IOT.server.models.Account;
import com.example.my.myapplication.IOT.server.models.LoginInfo;

import java.util.concurrent.ExecutionException;

/**
 * Created by MY on 6/19/2017.
 */
public class ServiceAccount {
    public  ServiceAccount(){

    }
    public LoginInfo checkAccount(final String tenDN, final String matKhau) throws ExecutionException, InterruptedException {
        final DetailServiceLogin detail = new DetailServiceLogin();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detail.execute(tenDN, matKhau);
            }
        });
        thread.start();
        return detail.get();
    }

    public Account getAccount(final String username) throws ExecutionException, InterruptedException {
        final DetailServiceGetAccount detailGetUser = new DetailServiceGetAccount();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detailGetUser.execute(username);
            }
        });
        thread.start();
        return detailGetUser.get();
    }

    public Account createAccount(final Account user) throws ExecutionException, InterruptedException {
        final DetailServiceCreateAccount detail = new DetailServiceCreateAccount();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detail.execute(user);
            }
        });
        thread.start();
        return detail.get();
    }

    public Account editAccount(final Account user) throws ExecutionException, InterruptedException {
        final DetailServiceEditAccount detail = new DetailServiceEditAccount();
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
