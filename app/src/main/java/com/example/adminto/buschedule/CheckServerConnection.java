package com.example.adminto.buschedule;

import android.app.Application;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;

import static junit.framework.Assert.assertEquals;

/**
 * Created by V on 13.05.2017.
 */

public class CheckServerConnection{


    public static void checkConnection(){

        GetParsedFromServer.methodName = "CheckConnection";

        new PostToWeb().execute("CheckConnection");

    }


}
