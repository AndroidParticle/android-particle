package com.example.my.myapplication.IOT.server.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Device;
import com.example.my.myapplication.IOT.server.models.Enviroment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.EventListener;

/**
 * Created by MY on 6/20/2017.
 */
public class DetailServiceGetListEnv extends AsyncTask<String, Integer, ArrayList<Enviroment>> {
    private static final String URL_GET_LIST_ENV_BY_DEVICEID = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject() + "/Devices/";
    private String deviceId;
    HttpClient httpClient;
    HttpGet httpGet;

    @Override
    protected ArrayList<Enviroment> doInBackground(String... params) {
        ArrayList<Enviroment> result = null;

        setDeviceId(params[0]);
        Log.d("deviceId", deviceId);

        initGet();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            String json = "";
            while ((line = br.readLine()) != null) {
                json += line;
            }
            br.close();
            Gson gson = new Gson();
            Log.d("listEnv", json);
            Type type = new TypeToken<ArrayList<Enviroment>>() {}.getType();
            result = gson.fromJson(json, type);

           // result = gson.fromJson(json,new ArrayList<Device>(){}.getClass());
            Log.d("tempC",result.get(0).getTempC()+"");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    private void initGet() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        httpGet = new HttpGet(URL_GET_LIST_ENV_BY_DEVICEID+getDeviceId()+"/environments");
    }
}
