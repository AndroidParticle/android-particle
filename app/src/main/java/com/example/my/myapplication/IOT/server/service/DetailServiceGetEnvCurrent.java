package com.example.my.myapplication.IOT.server.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Account;
import com.example.my.myapplication.IOT.server.models.EnviromentCurrent;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class DetailServiceGetEnvCurrent extends AsyncTask<String, Integer, EnviromentCurrent> {
    private static final String URL_GET_ENV_CURRENT = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject() + "/Devices/getInfoEnv?deviceId=";
    private String deviceId;
    HttpClient httpClient;
    HttpGet httpGet;

    @Override
    protected EnviromentCurrent doInBackground(String... params) {
        EnviromentCurrent result = null;
        setDeviceId(params[0]);
        initGet();
        try {
            Log.d("deviceid", getDeviceId());
            Log.d("URL_GET_ENV_CURRENT", URL_GET_ENV_CURRENT);

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
            try {
                String env = json;
                Log.d("envtry", env);
                String envf = convertStandardJSONString(env);
                Log.d("envftry", env);
                JSONObject obj = new JSONObject(envf);
                Log.d("envtry", String.valueOf(obj.getInt("la")));
                EnviromentCurrent enviromentCurrent = gson.fromJson(env, EnviromentCurrent.class);
                result = enviromentCurrent;
                Log.d("resulttry", result.getLa() + " ");
            } catch (Exception e) {
                //test
                //    String env = "{\\\"pa\\\" : 100470, \\\"height\\\" : 86, \\\"la\\\" : 23, \\\"ld\\\" : 0, \\\"h\\\" : 82, \\\"t\\\" : 27, \\\"f\\\" : 80, \\\"k\\\" : 300, \\\"hi\\\" : 29, \\\"dp\\\" : 23}";
                String env = "{\"pa\" : 100470, \"height\" : 86, \"la\" : 23, \"ld\" : 0, \"h\" : 82, \"t\" : 27, \"f\" : 80,\"k\" : 300, \"hi\" : 29, \"dp\" : 23}";
                Log.d("env", env);

                String envf = convertStandardJSONString(env);
                Log.d("envf", env);
                JSONObject obj = new JSONObject(envf);
                Log.d("env", String.valueOf(obj.getInt("la")));

                EnviromentCurrent enviromentCurrent = gson.fromJson(env, EnviromentCurrent.class);
                Log.d("enla", enviromentCurrent.getLa() + "");

                result = enviromentCurrent;
                //end test
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void initGet() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
        }
        httpGet = new HttpGet(URL_GET_ENV_CURRENT + getDeviceId());
    }

    public static String convertStandardJSONString(String data_json) {
        data_json = data_json.replaceAll("\\\\r\\\\n", "");
        data_json = data_json.replace("\"{", "{");
        data_json = data_json.replace("}\",", "},");
        data_json = data_json.replace("}\"", "}");
        return data_json;
    }
}
