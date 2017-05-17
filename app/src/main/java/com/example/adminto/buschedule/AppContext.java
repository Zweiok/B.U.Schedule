package com.example.adminto.buschedule;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * Created by V on 17.05.2017.
 */

public class AppContext extends Application{

    private static Context context;
    private static Activity activity;

    public void onCreate() {
        super.onCreate();
        AppContext.context = getApplicationContext();
    }

    public synchronized static Context getAppContext() {
        return AppContext.context;
    }

    /**
     * setCurrentActivity(null) in onPause() on each activity
     * setCurrentActivity(this) in onResume() on each activity
     *
     */

    public static void setCurrentActivity(Activity currentActivity) {
        activity = currentActivity;
    }

    public static Activity currentActivity() {
        return activity;
    }


}
