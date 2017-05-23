package com.example.adminto.buschedule;

import android.app.ActivityManager;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.view.View;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by adminto on 22.03.2017.
 */

class PostToWeb extends AsyncTask<String, String, String> {

    private ProgressDialog dialog;
    static String HttpResponse;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(AppContext.currentActivity());
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            //создаем запрос на сервер

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            //он у нас будет посылать post запрос
            HttpPost postMethod = new HttpPost("http://trace1245-001-site1.htempurl.com/");

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
            activity_choose_role.serverIsOnline = false;


            if(GetParsedFromServer.methodName.equals("CheckUser"))
            {
                    activity_choose_role.gotoSchedule();
            }
            else
            {
                GetParsedFromServer.methodName = "";
            }

            GetParsedFromServer.methodName = "";
            if (activity_choose_role.c == 0)
            {
                activity_choose_role.checkUser();
                activity_choose_role.c++;
            }

        }
        dialog.dismiss();
        switch (GetParsedFromServer.methodName)
        {
            // TODO: 14.05.2017
            case "CheckConnection":
                GetParsedFromServer.methodName = "";
                CheckConnection();

                break;
            case "GetScheduleForDB":
                GetParsedFromServer.methodName = "";
               // start_page.scheduleArrayList = GetParsedFromServer.GetSchedule1() ;
               // settings.makeText(GetParsedFromServer.GetSchedule1().get(0).getTime());
                activity_choose_role.dataBase.deleteSchedule();
                activity_choose_role.dataBase.addSchedule(GetParsedFromServer.GetSchedule1());
                if(activity_choose_role.count == 1) {
                    Calendar c = GregorianCalendar.getInstance();

                    // Set the calendar to monday of the current week
                    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

                    // Print dates of the current week starting on Monday
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());


                    c.add(Calendar.DATE, -7);
                    String sd = df.format(c.getTime());
                    c.add(Calendar.DATE, 67);
                    String ed = df.format(c.getTime());

                    GetParsedFromServer.GetComment(activity_choose_role.dataBase.getUserInfo().getGroup_name(), sd, ed);
                    activity_choose_role.dataBase.deleteComments();
                }
                //
                break;
            case "GetScheduleForListView":
                GetParsedFromServer.methodName = "";
                //settings.makeText(HttpResponse);
                start_page.scheduleArrayList = GetParsedFromServer.GetSchedule1();
                if(activity_choose_role.count != 0) {

                     GetParsedFromServer.GetComment(activity_choose_role.currentSchedulesType,start_page.Dates[0],start_page.Dates[start_page.Dates.length-1]);
                }
                start_page.updateActivity();
                break;
            case "CheckUser":
                GetParsedFromServer.methodName = "";

                if(GetParsedFromServer.CheckUser1())
                {

                    activity_choose_role.gotoSchedule();
                    activity_choose_role.currentSchedulesType =activity_choose_role.dataBase.getUserInfo().getGroup_name();
                }
                else
                {
                    activity_choose_role.makeToast("Невірні дані");
                }
                break;
            case "RegisterStudent":
                GetParsedFromServer.methodName = "";
                activity_choose_role.RegStudent(GetParsedFromServer.RegisterStudent1());
                break;
            case "RegisterTeacher":
                GetParsedFromServer.methodName = "";
                activity_choose_role.RegTeacher(GetParsedFromServer.RegisterTeacher1());
              //  activity_choose_role.makeToast(HttpResponse);
                break;
            case "AddComment":
                GetParsedFromServer.methodName = "";
                break;
            case "GetComments":
                GetParsedFromServer.methodName = "";
                if(activity_choose_role.count == 1) {
                    activity_choose_role.dataBase.deleteComments();
                    activity_choose_role.dataBase.addComments(GetParsedFromServer.GetComment1());
                    GetParsedFromServer.GetComment(activity_choose_role.currentSchedulesType,start_page.Dates[0],start_page.Dates[start_page.Dates.length-1]);
                activity_choose_role.count++;
                }
                activity_choose_role.commentArrayList = GetParsedFromServer.GetComment1();
                break;
            case "GetScheduleInfo":
                GetParsedFromServer.methodName = "";
                activity_choose_role.ScheduleInfo = GetParsedFromServer.ScheduleInfo();
                activity_choose_role.checkUser();
                break;
            default:
                GetParsedFromServer.methodName = "";
                break;
        }


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