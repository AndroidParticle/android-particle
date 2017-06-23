package com.example.my.myapplication.IOT.server.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.my.myapplication.IOT.server.ConfigApp;
import com.example.my.myapplication.IOT.server.models.Account;
import com.example.my.myapplication.IOT.server.models.Enviroment;
import com.example.my.myapplication.IOT.server.models.EnviromentCurrent;
import com.example.my.myapplication.IOT.server.models.LoginInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class DetailServiceLogin extends AsyncTask<String, Integer, LoginInfo> {
    private static final String URL_KT_DANG_NHAP = "http://" + ConfigApp.getHost() + ":" + ConfigApp.getPort() + "/" + ConfigApp.getNameProject() + "/Accounts/login?include=user";
    HttpClient httpClient;
    HttpPost httpPost;

    @Override
    protected LoginInfo doInBackground(String... params) {
        LoginInfo result = null;
        initPost();
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email", params[0]));
            nameValuePairs.add(new BasicNameValuePair("password", params[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.d("email", params[0]);
            Log.d("password", params[1]);
            Log.d("url", URL_KT_DANG_NHAP);

            HttpResponse response = httpClient.execute(httpPost);

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            String jsonResult = "";
            while ((line = br.readLine()) != null) {
                jsonResult += line;
            }
            br.close();
            result = gson.fromJson(jsonResult, LoginInfo.class);
//            java.util.Date temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS")
//                    .parse("2012-07-10 14:58:00.000000");
            Log.d("time", String.valueOf(result.getCreated()));

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
        httpPost = new HttpPost(URL_KT_DANG_NHAP);
    }


    // this class can't be static
    public class GsonUTCDateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

        private final DateFormat dateFormat;

        public GsonUTCDateAdapter() {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);      //This is the format I need
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));                               //This is the key line which converts the date to UTC which cannot be accessed with the default serializer
        }

        @Override
        public synchronized JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(dateFormat.format(date));
        }

        @Override
        public synchronized Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
            try {
                return dateFormat.parse(jsonElement.getAsString());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    }
}
