package com.example.adminto.buschedule;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adminto on 22.03.2017.
 */

class PostToWeb extends AsyncTask<String, String, String> {


    @Override
    protected String doInBackground(String... params) {

        try {
            //создаем запрос на сервер

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            //он у нас будет посылать post запрос
            HttpPost postMethod = new HttpPost(params[0]);

            //будем передавать два параметра
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            //передаем параметры из наших текстбоксов

            nameValuePairs.add(new BasicNameValuePair("args[0]","GetSchedule"));
            nameValuePairs.add(new BasicNameValuePair("args[1]","КН-10"));
            nameValuePairs.add(new BasicNameValuePair("args[2]","01-05-2017"));
            nameValuePairs.add(new BasicNameValuePair("args[3]","30-06-2017"));

            //собераем их вместе и посылаем на сервер
            postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

            //получаем ответ от сервера
            String response = hc.execute(postMethod, res);

            return response;

        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String success) {
        if(success != null){
            settings.makeText(success);
        } else {
            new PostToWeb().execute("http://test1.somee.com/");
        }


    }
}