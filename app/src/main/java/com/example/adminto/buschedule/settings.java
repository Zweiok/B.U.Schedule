package com.example.adminto.buschedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class settings extends AppCompatActivity {


    Switch alarmSwitcher;

    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    static TextView textView4 ;
    static Vibrator vv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        alarmSwitcher  = (Switch) findViewById(R.id.AlarmSwitcher);
        Intent intent = new Intent(getApplicationContext() , AlarmService.class);
        pendingIntent = PendingIntent.getBroadcast(settings.this, 0 , intent , PendingIntent.FLAG_UPDATE_CURRENT);

        TextView infoRole = (TextView) findViewById(R.id.Role);
        TextView infoName = (TextView) findViewById(R.id.Group);

        if(activity_choose_role.dataBase.getUserInfo().getRole() == 1)
        {
            infoRole.setText("Викладач");
            infoName.setText("Ім'я: " + activity_choose_role.dataBase.getUserInfo().getGroup_name());
        }
        else
        {
            infoRole.setText("Студент");
            infoName.setText("Група: " + activity_choose_role.dataBase.getUserInfo().getGroup_name());
        }

        Button reset = (Button) findViewById(R.id.Reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity_choose_role.dataBase.deleteSchedule();
                activity_choose_role.dataBase.deleteComments();
                activity_choose_role.dataBase.deleteUserInfo();
                /*
                try {
                    AlarmManager mgr = (AlarmManager) getSystemService(AppContext.getAppContext().ALARM_SERVICE);
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, PendingIntent.getActivity(activity_choose_role.context, 0, new Intent(getIntent()), PendingIntent.FLAG_ONE_SHOT));
                    System.exit(2);
                }
                catch (NullPointerException e)
                {}
                */
            }
        });

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final DateFormat dfTime = new java.text.SimpleDateFormat("HH:mm", Locale.getDefault());
        final Calendar c = GregorianCalendar.getInstance();




        alarmSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alarmSwitcher.isChecked()) {



                    for (String a : start_page.PlaceholderFragment.Time) {

                        try {
                            Date dt = dfTime.parse(a);
                            c.set(Calendar.HOUR_OF_DAY,  dt.getHours());
                            c.set(Calendar.MINUTE, dt.getMinutes());
                            Toast.makeText(settings.super.getBaseContext(), "Включено " + c.getTime(), Toast.LENGTH_SHORT).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        vv = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        vv.vibrate(500);

                        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() , pendingIntent);
                    }

                }
                if(!alarmSwitcher.isChecked()) {

                    Toast.makeText(settings.super.getBaseContext(), "Выключено", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void backOnClick(View v)
    {
        Intent intObj = new Intent(this, start_page.class);
        startActivity(intObj);
    }


    public static void makeText(String s)
    {
        textView4.setText(s );

    }
}