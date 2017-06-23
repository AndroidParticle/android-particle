package com.example.my.myapplication.serverTest.service;

import android.os.AsyncTask;


import com.example.my.myapplication.IOT.server.ConfigApp;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ServiceAddGallery {
    public static boolean addGellery(final String tenTk, final String maBaiViet) throws ExecutionException, InterruptedException {
        final DetailAddGallery detail = new DetailAddGallery();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                detail.execute(tenTk, maBaiViet);
            }
        });
        thread.start();
        return detail.get();
    }

    private static class DetailAddGallery extends AsyncTask<String, Integer, Boolean> {
        private String URL_Gellery = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject() + "/DAO/addBaiVietBST";
        HttpClient httpClient;
        HttpPost httpPost;

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;
            try {
                if (httpClient == null) {
                    httpClient = new DefaultHttpClient();
                }
                httpPost = new HttpPost(URL_Gellery);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("taiKhoan", params[0]));
                nameValuePairs.add(new BasicNameValuePair("maBaiViet", params[1]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                Log.d("taiKhoan", params[0]);
//                Log.d("maBV", params[1]);
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
    }
}
