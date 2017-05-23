package com.example.adminto.buschedule;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class activity_choose_role extends AppCompatActivity {

    public static Context context;
    public static user thisUser = new user();
    static ArrayList<Comment> commentArrayList = new ArrayList<>();
    static int c = 0;
    static int schFromList=0;
    static int count = 0;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    static public String currentSchedulesType; // поточна група\імя\кабінет
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    static scheduleInfo ScheduleInfo = new scheduleInfo();
    private ViewPager mViewPager;
    static boolean serverIsOnline=true;
    static DataBase dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        AppContext.setCurrentActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        activity_choose_role.context = getApplicationContext();
        dataBase = new DataBase(this);
        GetParsedFromServer.GetScheduleInfo();
    }
    public static void checkUser()
    {
        if(dataBase.getUserInfo().getRole() == 3)
        {
        }
        else{
            thisUser = dataBase.getUserInfo();
            if(dataBase.getUserInfo().getRole() == 0) {
                activity_choose_role.currentSchedulesType =activity_choose_role.dataBase.getUserInfo().getGroup_name();
                gotoSchedule();
            }
            else
            {
                if(!serverIsOnline)
                {
                    gotoSchedule();
                }
                activity_choose_role.currentSchedulesType =activity_choose_role.dataBase.getUserInfo().getGroup_name();
                GetParsedFromServer.CheckUser(dataBase.getUserInfo().getEmail(),dataBase.getUserInfo().getPass());
            }
        }
    }


    public static void makeToast(String s) {
        Toast.makeText(activity_choose_role.context, "" + s, Toast.LENGTH_SHORT).show();
    }

    // sliding form

    public static void RegStudent(boolean check)
    {


        if(check)
        {

            thisUser.setRole(0);
            dataBase.setUserInfo(thisUser);
            activity_choose_role.currentSchedulesType =activity_choose_role.dataBase.getUserInfo().getGroup_name();
            gotoSchedule();
        }
        else
        {
            Toast.makeText(AppContext.getAppContext(), "Невірні дані ", Toast.LENGTH_SHORT).show();
            thisUser.setGroup_name(null);
        }
    }
    public static void RegTeacher(String[] s)
    {

        if (s[0].equals("true")) {
            thisUser.setRole(1);
            thisUser.setGroup_name(s[1]);
            dataBase.setUserInfo(thisUser);
            activity_choose_role.currentSchedulesType =activity_choose_role.dataBase.getUserInfo().getGroup_name();
            gotoSchedule();
        }
        else
        {
            Toast.makeText(AppContext.getAppContext(), "" +  s[1] , Toast.LENGTH_SHORT).show();
            thisUser.setRole(0);
            thisUser.setPass(null);
            thisUser.setEmail(null);
            thisUser.setGroup_name(null);
        }
    }


    public static void gotoSchedule()
    {

        Intent intObj = new Intent(AppContext.currentActivity(), start_page.class);
        AppContext.currentActivity().startActivity(intObj);

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        static View rootView;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                rootView = inflater.inflate(R.layout.student_form, container, false);

                final EditText group = (EditText) rootView.findViewById(R.id.editText2);


                Button student = (Button) rootView.findViewById(R.id.button2);
                student.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataBase.deleteUserInfo();
                        if (true) {
                            activity_choose_role.thisUser.setGroup_name(group.getText().toString());
                            GetParsedFromServer.RegisterStudent(group.getText().toString(),"sashaLalka");

                        }
                    }
                });

            }
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                rootView = inflater.inflate(R.layout.teacher_form, container, false);
                final EditText email = (EditText) rootView.findViewById(R.id.editText3);
                final EditText pass = (EditText) rootView.findViewById(R.id.editText);
                Button teacher = (Button) rootView.findViewById(R.id.button3);

                teacher.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataBase.deleteUserInfo();
                        if (true) {
                        //    Toast.makeText(rootView.getContext(), "" + email.getText().toString() + pass.getText().toString() , Toast.LENGTH_SHORT).show();
                            activity_choose_role.thisUser.setEmail(email.getText().toString());
                            activity_choose_role.thisUser.setPass(pass.getText().toString());
                            GetParsedFromServer.RegisterTeacher(email.getText().toString(),pass.getText().toString(),"lalkaSasha");

                        }

                    }
                });


            }
            return rootView;


        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Студент";
                case 1:
                    return "Викладач";
            }
            return null;
        }
    }
}
