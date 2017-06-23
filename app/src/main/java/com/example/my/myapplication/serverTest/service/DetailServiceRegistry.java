package com.example.my.myapplication.serverTest.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Account;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class DetailServiceRegistry extends AsyncTask<Account, Integer, Boolean> {
    private static final String URL_TAO_TAI_KHOAN = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject() + "/DAO/addTaiKhoan";
    HttpClient httpClient;
    HttpPost httpPost;

    @Override
    protected Boolean doInBackground(Account... params) {
        boolean result = false;
        initPost();
        try {
            Account user = params[0];
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd").create();
            String json = gson.toJson(user);

            Log.d("user: ", json);

            httpPost.setEntity(new StringEntity(json));
            HttpResponse response = httpClient.execute(httpPost);

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.equals("true")) {
                    result = true;
                } else {
                    result = false;
                }
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void initPost() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        httpPost = new HttpPost(URL_TAO_TAI_KHOAN);
    }
}
