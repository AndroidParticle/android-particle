package com.example.my.myapplication.IOT.server.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.TypeParticle;
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
public class DetailServiceGetListType extends AsyncTask<String, Integer, ArrayList<TypeParticle>> {
    private static final String URL_GET_LIST_TYPE = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject() + "/Types";
    HttpClient httpClient;
    HttpGet httpGet;

    @Override
    protected ArrayList<TypeParticle> doInBackground(String... params) {
        ArrayList<TypeParticle> result = null;
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
            Log.d("listtype", json);

            Type type = new TypeToken<ArrayList<TypeParticle>>() {}.getType();
            result = gson.fromJson(json, type);

           // result = gson.fromJson(json,new ArrayList<Device>(){}.getClass());
            Log.d("content",result.get(0).getNameType());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private void initGet() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        httpGet = new HttpGet(URL_GET_LIST_TYPE);
    }
}
