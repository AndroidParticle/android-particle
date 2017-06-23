package com.example.my.myapplication.IOT.server.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Account;
import com.example.my.myapplication.IOT.server.models.Device;
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

/**
 * Created by MY on 6/20/2017.
 */
public class DetailServiceGetListDevice extends AsyncTask<String, Integer, ArrayList<Device>> {
    //https://cryptic-sea-66379.herokuapp.com/api/Devices?filter[where][accountId]=594427a18872d40011a9d344
    private static final String URL_GET_LIST_DEVICE = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject() + "/Devices";
    private static final String URL_GET_LIST_DEVICE_ByACCOUNT = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject() + "/Accounts/";
    private String accountID;
    HttpClient httpClient;
    HttpGet httpGet;

    @Override
    protected ArrayList<Device> doInBackground(String... params) {
        ArrayList<Device> result = null;

        setAccountID(params[0]);
        Log.d("accountID", accountID);

        initGet();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            Log.d("url", URL_GET_LIST_DEVICE);

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            String json = "";
            while ((line = br.readLine()) != null) {
                json += line;
            }
            br.close();
            Gson gson = new Gson();
            Log.d("listDevice", json);

            Type type = new TypeToken<ArrayList<Device>>() {
            }.getType();
            result = gson.fromJson(json, type);

            if (result.size() == 1) {
                //value test
                Device device = result.get(0);
                Device device1 = new Device(device.getDeviceId(), "Photon Test", device.isActive(), device.getLocation(), device.getLatitude() + 0.3, device.getLongitude() + 0.1, device.getDescription(), device.getId(), device.getTypeId(), device.getAccountId(), device.getKeyThingspeak());
                Device device2 = new Device(device.getDeviceId(), "Eclectron Test", device.isActive(), device.getLocation(), device.getLatitude() + 0.2, device.getLongitude(), device.getDescription(), device.getId(), device.getTypeId(), device.getAccountId(), device.getKeyThingspeak());
                result.add(device2);
                result.add(device1);
                //end

                // result = gson.fromJson(json,new ArrayList<Device>(){}.getClass());
                Log.d("nameDevice", result.get(0).getNameDevice());
                Log.d("nameDevice", result.get(1).getNameDevice());
            }
            Log.d("result", result.size() + "");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    private void initGet() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        if (accountID != null || !accountID.equals("")) {
            //httpGet = new HttpGet(URL_GET_LIST_DEVICE + "?filter[where][accountId]=" + accountID);
            httpGet = new HttpGet(URL_GET_LIST_DEVICE_ByACCOUNT + accountID + "/devices");
        }
        httpGet = new HttpGet(URL_GET_LIST_DEVICE);
    }
}
