package com.example.adminto.buschedule;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

public class start_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;


    // названия компаний (групп)

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    TextView Week;
    int daysOfWeek = 0;
    static String[] Dates = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        Log.d("Error ", "");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //________________________________________________________________________________________
        Week = (TextView) findViewById(R.id.Week);
        Week.setText(getWeek(daysOfWeek));
      //  GetParsedFromServer.GetSchedule("КН-10",Dates[0],Dates[Dates.length-1]);
        //________________________________________________________________________________________
    }

    //______________________________________________________________________
    public String getWeek(int week) {

        Calendar c = GregorianCalendar.getInstance();

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
/*
        c.add(Calendar.DATE, week);
        startDate = df.format(c.getTime());
        c.add(Calendar.DATE, 6);
        endDate = df.format(c.getTime());
/*/
        c.add(Calendar.DATE, week);
        Dates[0] = df.format(c.getTime());
        for (int i = 1; i <= 6 ; i++)
        {
            c.add(Calendar.DATE, 1);
            Dates[i] = df.format(c.getTime());
        }


        return " " + Dates[0] + " -\n- " + Dates[Dates.length-1];

     //   return " " + startDate + " -\n- " + endDate;

    }
    //{

    public void nextWeek(View v) {

        daysOfWeek += 7;
        Week.setText(getWeek(daysOfWeek));
     //   GetParsedFromServer.GetSchedule("КН-10",Dates[0],Dates[Dates.length-1]);
    }

    public void prevWeek(View v) {
        daysOfWeek -= 7;
        Week.setText(getWeek(daysOfWeek));
      //  GetParsedFromServer.GetSchedule("КН-10",Dates[0],Dates[Dates.length-1]);
    }
    static ArrayList<schedule> scheduleArrayList = new ArrayList<>();

    //}
    //______________________________________________________________________

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        static ArrayList<schedule> scheduleOfFragment;
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            /*
            scheduleOfFragment = new ArrayList<>();
            for (schedule s: start_page.scheduleArrayList) {
                if(s.getDate() == start_page.Dates[sectionNumber])
                {
                    scheduleOfFragment.add(s);
                }
            }
*/

            return fragment;
        }

        static public String[] lessonsName = new String[]{"first lesson name", "second lesson name", "third lesson name", "fourth lesson name"};
        static public String[] roomNumb = new String[]{"100", "101", "104", "255"};
        static public String[] TeachersName = new String[]{"Qwerty Qwert1", "Qwerty Qwert1", "Qwerty Qwert1", "Qwerty Qwert1"};
        static public String[] Time = new String[]{"04:12", "04:13", "04:14", "04:15"};
        static public String[] group = new String[]{"asq-125", "den-643", "uyl-635", "fnz-513"};


        private String[] mWinterMonthsArray = new String[]{"comment 1 ls", "comment 1 ls", "comment 1 ls", ""};
        private String[] mSpringMonthsArray = new String[]{"comment 2 ls", "comment 2 ls", "comment 2 ls", ""};
        private String[] mSummerMonthsArray = new String[]{"no comments", ""};
        private String[] mAutumnMonthsArray = new String[]{"comment 4 ls", "comment 4 ls", "comment 4 ls", ""};


        static View rootView;


        ArrayList<ArrayList<String>> groups;
        ArrayList<String> children;
         // global schedule arraylist
        schedule ss;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_start_page_scedule, container, false);



            //_____________________________________________________________________________________
            groups = new ArrayList<>();
            addToArrayList(mWinterMonthsArray);
            addToArrayList(mSpringMonthsArray);
            addToArrayList(mSummerMonthsArray);
            addToArrayList(mAutumnMonthsArray);
            //_____________________________________________________________________________________________________
            ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.expListView);
            ExpListAdapter adapter = new ExpListAdapter(rootView.getContext(), groups, activity_choose_role.dataBase.getSchedule(start_page.Dates));
            expandableListView.setAdapter(adapter);
            return rootView;
        }
        public ArrayList<schedule> addssss()
        {
            scheduleArrayList = new ArrayList<>();
            for (int i = 0; i < lessonsName.length ; i++) {
                ss = new schedule();
                ss.setGroup(group[i]);
                ss.setId(i);
                ss.setName(lessonsName[i]);
                ss.setProf(TeachersName[i]);
                ss.setTime(Time[i]);
                ss.setRoom(roomNumb[i]);
                scheduleArrayList.add(ss);

            }
            return scheduleArrayList;
        }
/*
        public static void changeSchedule(ArrayList<schedule> Schedule,ArrayList<ArrayList<String>> comments) {

            Schedule = new ArrayList<>();


            ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.expListView);
            ExpListAdapter adapter = new ExpListAdapter(rootView.getContext(), comments,Schedule);
            expandableListView.setAdapter(adapter);
        }
*/

        public void addToArrayList(String[] ch) {

            children = new ArrayList<>();
            for (int i = 0; i < ch.length; i++) {
                children.add(ch[i]);
            }
            groups.add(children);
        }


        //customize extandablelist{

        public static class ExpListAdapter extends BaseExpandableListAdapter {

            private ArrayList<ArrayList<String>> mGroups;
            private Context mContext;
            ArrayList<schedule> mScheduleArrayList;
            public ExpListAdapter(Context context, ArrayList<ArrayList<String>> groups,ArrayList<schedule> scheduleArrayList) {
                mContext = context;
                mGroups = groups;
                mScheduleArrayList = scheduleArrayList;
            }

            @Override
            public int getGroupCount() {
                return mScheduleArrayList.size();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return mGroups.get(groupPosition).size();
            }

            @Override
            public Object getGroup(int groupPosition) {
                return mGroups.get(groupPosition);
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return mGroups.get(groupPosition).get(childPosition);
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                                     ViewGroup parent) {

                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.custom_listview, null);
                }

                if (isExpanded) {
                    //Изменяем что-нибудь, если текущая Group раскрыта
                } else {
                    //Изменяем что-нибудь, если текущая Group скрыта
                }

                TextView lessonsName1 = (TextView) convertView.findViewById(R.id.lessonsName);
                TextView roomNumb1 = (TextView) convertView.findViewById(R.id.roomNumb);
                TextView TeachersName1 = (TextView) convertView.findViewById(R.id.TeachersName);
                TextView Time1 = (TextView) convertView.findViewById(R.id.Time);
                TextView group1 = (TextView) convertView.findViewById(R.id.group);
                lessonsName1.setText(mScheduleArrayList.get(groupPosition).getName());
                roomNumb1.setText(mScheduleArrayList.get(groupPosition).getRoom() + " каб.");
                TeachersName1.setText("Викладач: " +  mScheduleArrayList.get(groupPosition).getProf());
                Time1.setText("Час: " + mScheduleArrayList.get(groupPosition).getTime());
                group1.setText("Група: " + mScheduleArrayList.get(groupPosition).getGroup());

                return convertView;

            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                     View convertView, ViewGroup parent) {
                convertView = null;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.comments_activity, null);

                }

                TextView comment = (TextView) convertView.findViewById(R.id.comment);
                comment.setText(mGroups.get(groupPosition).get(childPosition));

                if (isLastChild) {

                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.activity_add_comment, null);


                }
                return convertView;

            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
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

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E");

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1
            );
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            c.setTime(new Date()); // Устанавливаем текущее время

            switch (position) {
                case 0:
                    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    return dateFormat.format(c.getTime());
                case 1:
                    c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                    return dateFormat.format(c.getTime());
                case 2:
                    c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                    return dateFormat.format(c.getTime());
                case 3:
                    c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                    return dateFormat.format(c.getTime());
                case 4:
                    c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                    return dateFormat.format(c.getTime());
                case 5:
                    c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                    return dateFormat.format(c.getTime());
                case 6:
                    c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    return dateFormat.format(c.getTime());
            }
            return null;
        }
    }
    // Navigation drawer{


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        final String[] roomNumb = new String[]{"100", "101", "104", "255"};
        final String[] TeachersName = new String[]{"Qwerty Qwert", "Qwerty Qwert", "Qwerty Qwert1", "Qwerty Qwert1"};
        final String[] group = new String[]{"asq-125", "den-643", "uyl-635", "fnz-513"};

        if (id == R.id.nav_camera) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.choose_sch_room, null);
            choose("Виберіть кабінет", roomNumb, view);
        } else if (id == R.id.nav_gallery) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.choose_sch_group, null);
            choose("Виберіть групу", group, view);
        } else if (id == R.id.nav_slideshow) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.choose_sch_teacher, null);
            choose("Виберіть викладача", TeachersName, view);
        } else if (id == R.id.nav_settings) {
            OnSettingsChoose();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_content);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void OnSettingsChoose() {
        Intent intObj = new Intent(this, settings.class);
        startActivity(intObj);
    }
    // }

    // alert dialog{

    public void choose(String s, String[] t, View view) {


        final ListView listView = (ListView) view.findViewById(R.id.listView);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setSelector(android.R.color.darker_gray);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_activated_1, t);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast.makeText(view.getContext(), "" + adapter.getItem(listView.getCheckedItemPosition()), Toast.LENGTH_SHORT).show();

            }
        });
        Dialog dialog = new Dialog(this);
        dialog.setContentView(view.getRootView());
        dialog.setTitle(s);
        dialog.show();


    }


    // }


}
