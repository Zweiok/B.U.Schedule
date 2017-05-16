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
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adminto on 22.03.2017.
 */

class PostToWeb extends AsyncTask<String, String, String> {

    static String HttpResponse;

    @Override
    protected String doInBackground(String... params) {

        try {
            //создаем запрос на сервер

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            //он у нас будет посылать post запрос
            HttpPost postMethod = new HttpPost("http://login007-001-site1.atempurl.com/");

            //будем передавать два параметра
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            //передаем параметры из наших текстбоксов

            for (int i = 0; i < params.length; i++) {
                nameValuePairs.add(new BasicNameValuePair("args[" + (i) + "]", params[i]));
            }
/*
            nameValuePairs.add(new BasicNameValuePair("args{0}","GetSchedule"));
            nameValuePairs.add(new BasicNameValuePair("args[1]","КН-10"));
            nameValuePairs.add(new BasicNameValuePair("args[2]","01-05-2017"));
            nameValuePairs.add(new BasicNameValuePair("args[3]","30-06-2017"));
*/
            //собераем их вместе и посылаем на сервер
            postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

            //получаем ответ от сервера
            String response = hc.execute(postMethod, res);

            return response;

        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String success) {
        if (success != null) {
            activity_choose_role.serverIsOnline = true;

            HttpResponse = success;
        } else {
            HttpResponse = null;
            activity_choose_role.makeToast("Server is offline");
            GetParsedFromServer.methodName = null;
        }

        switch (GetParsedFromServer.methodName)
        {
            // TODO: 14.05.2017
            case "CheckConnection":
                CheckConnection();
                break;
            case "GetScheduleForDB":
               // start_page.scheduleArrayList = GetParsedFromServer.GetSchedule1() ;
               // settings.makeText(GetParsedFromServer.GetSchedule1().get(0).getTime());
                activity_choose_role.dataBase.addSchedule(GetParsedFromServer.GetSchedule1());
                //
                break;
            case "GetScheduleForListView":
                //

                //settings.makeText(HttpResponse);
                break;
            case "CheckUser":
                break;
            case "RegisterStudent":
                break;
            case "RegisterTeacher":
                break;
            case "AddComment":
                break;
            case "GetComments":
                break;
            default:
                break;
            // TODO: 14.05.2017
        }
        GetParsedFromServer.methodName = null;

    }

    public void CheckConnection() {
        if(HttpResponse != null) {
            activity_choose_role.serverIsOnline = true;
            activity_choose_role.makeToast("Server is online");
        }
        else
        {
            activity_choose_role.serverIsOnline = false;
            activity_choose_role.makeToast("Server is offline" );
        }
    }

}