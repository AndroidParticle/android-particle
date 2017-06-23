package com.example.my.myapplication.IOT.server.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Account;
import com.example.my.myapplication.IOT.server.models.Device;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class DetailServiceGetDeviceInfo extends AsyncTask<String, Integer, Device> {
   // https://cryptic-sea-66379.herokuapp.com/api/Accounts/594427a18872d40011a9d344/devices?filter=[where][deviceId]=2e0027001247343339383037
    private static final String URL_GET_DEVICEINFO = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject() + "/Accounts/";
    private String accountID;
    private  String deviceID;
    HttpClient httpClient;
    HttpGet httpGet;

    @Override
    protected Device doInBackground(String... params) {
        Device result = null;
        setAccountID(params[0]);
        setDeviceID(params[1]);
        initGet();
        try {
            Log.d("url",URL_GET_DEVICEINFO);
            HttpResponse response = httpClient.execute(httpGet);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            String json = "";
            while ((line = br.readLine()) != null) {
                json += line;
            }
            br.close();
            Gson gson = new Gson();
            Log.d("user", json);
            result = gson.fromJson(json, Device.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public void initGet() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        httpGet = new HttpGet(URL_GET_DEVICEINFO + getAccountID()+"/devices?filter=[where][deviceId]="+getDeviceID());
    }
}
