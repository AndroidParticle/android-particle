package com.example.my.myapplication.IOT.server.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Device;
import com.example.my.myapplication.IOT.server.models.ObjectResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class DetailServiceControlLed extends AsyncTask<String, Integer, Boolean> {
    private static final String URL_GET_DEVICEINFO = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject()
            + "/Devices/controllerLed?deviceId=";
    private String deviceID;
    private String control;
    HttpClient httpClient;
    HttpGet httpGet;

    @Override
    protected Boolean doInBackground(String... params) {
        boolean result = false;
        setDeviceID(params[0]);
        setControl(params[1]);
        initGet();
        try {
            Log.d("url", URL_GET_DEVICEINFO);
            HttpResponse response = httpClient.execute(httpGet);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            String jsonResult = "";
            while ((line = br.readLine()) != null) {
                jsonResult += line;
            }
            br.close();
            Log.d("user", jsonResult);
            ObjectResult objectResult = gson.fromJson(jsonResult, ObjectResult.class);
            if (objectResult.isResult()) {
                result = true;
            }
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

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public void initGet() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        httpGet = new HttpGet(URL_GET_DEVICEINFO + getDeviceID() + "&command=" + getControl());
    }
}
