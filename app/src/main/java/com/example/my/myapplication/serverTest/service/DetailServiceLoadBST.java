package com.example.my.myapplication.serverTest.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.serverTest.models.BaiViet;
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
import java.util.List;

public class DetailServiceLoadBST extends AsyncTask<String, Integer, List<BaiViet>> {
    private static final String URL_LOAD_DS_DIA_DIEM = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject() + "/DAO/getBSTtaiKhoan";
    HttpClient httpClient;
    HttpGet httpGet;

    @Override
    protected List<BaiViet> doInBackground(String... params) {
        List<BaiViet> list = null;
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        httpGet = new HttpGet(URL_LOAD_DS_DIA_DIEM+"/"+params[0]);
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
            Log.d("list ", json);
            Type collectionType = new TypeToken<List<BaiViet>>() {
            }.getType();
            list = gson.fromJson(json, collectionType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void initGet() {

    }
}
