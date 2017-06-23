package com.example.my.myapplication.IOT.server.service;

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

public class DetailServiceCreateAccount extends AsyncTask<Account, Integer, Account> {
    private static final String URL_CREATE_ACCOUNT = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject() + "/Accounts";
    HttpClient httpClient;
    HttpPost httpPost;
    private String idAccount;

    @Override
    protected Account doInBackground(Account... params) {
        Account result = null;
        initPost();
        try {

            // String username = URLEncoder.encode(params[0].getFullName(), "UTF-8");
            Account user = params[0];
            httpPost.setHeader("Content-type", "application/json");
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String json = gson.toJson(user);

            Log.d("user: ", json);
            Log.d("url", URL_CREATE_ACCOUNT);
            httpPost.setEntity(new StringEntity(json, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            Log.d("datahttpPost: ", httpPost.toString());
            Log.d("httpClient: ", httpClient.toString());
            Log.d("response: ", response.toString());

            String line = "";
            String jsonResult = "";
            while ((line = br.readLine()) != null) {
                jsonResult += line;
            }
            br.close();
            Log.d("user", jsonResult);
            result = gson.fromJson(jsonResult, Account.class);
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
        httpPost = new HttpPost(URL_CREATE_ACCOUNT);
    }
}
