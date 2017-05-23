package com.example.adminto.buschedule;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.internal.NavigationMenuPresenter;
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

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class start_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static Context context;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private static SectionsPagerAdapter mSectionsPagerAdapter;

    // названия компаний (групп)

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private static ViewPager mViewPager;
    TextView Week;
    int daysOfWeek = 0;
    static String[] Dates = new String[7];
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);


        AppContext.setCurrentActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        context = getApplicationContext();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.Name_Group);

        nav_user.setText(activity_choose_role.dataBase.getUserInfo().getGroup_name());




        //________________________________________________________________________________________
        Week = (TextView) findViewById(R.id.Week);
        Week.setText(getWeek(daysOfWeek));
        GetParsedFromServer.GetScheduleForListView(activity_choose_role.currentSchedulesType,Dates[0],Dates[Dates.length-1]);
        //________________________________________________________________________________________
        start_page.scheduleArrayList = activity_choose_role.dataBase.getSchedule(start_page.Dates);
        activity_choose_role.commentArrayList = activity_choose_role.dataBase.getComment();
        if(activity_choose_role.schFromList == 1)
        {
            activity_choose_role.schFromList = 0;
            updateActivity();
        }
    }

    static public void refresh()
    {
        Intent intObj = new Intent(AppContext.currentActivity(), start_page.class);
        AppContext.currentActivity().startActivity(intObj);
    }

    //______________________________________________________________________
    public String getWeek(int week) {

        Calendar c = GregorianCalendar.getInstance();

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        c.add(Calendar.DATE, week);
        Dates[0] = df.format(c.getTime());
        for (int i = 1; i < 7 ; i++)
        {
            c.add(Calendar.DATE, 1);
            Dates[i] = df.format(c.getTime());
        }


        return " " + Dates[0] + " -\n- " + Dates[Dates.length-1];


    }
    //{

    public static void updateActivity()
    {
        Calendar c = GregorianCalendar.getInstance();

        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());


        c.add(Calendar.DATE, -7);
        String sd = df.format(c.getTime());
        c.add(Calendar.DATE, 67);
        String ed = df.format(c.getTime());

        if(activity_choose_role.count == 0)
        {
            if(activity_choose_role.serverIsOnline) {
                activity_choose_role.dataBase.deleteComments();
                GetParsedFromServer.GetScheduleForDB(activity_choose_role.currentSchedulesType, sd, ed);
            }
        }
        if(activity_choose_role.schFromList == 1) {
            if(activity_choose_role.serverIsOnline) {
                start_page.scheduleArrayList = activity_choose_role.dataBase.getSchedule(start_page.Dates);
                activity_choose_role.schFromList = 0;
            }
        }
        if(activity_choose_role.currentSchedulesType.equals(activity_choose_role.dataBase.getUserInfo().getGroup_name()))
        {
            activity_choose_role.commentArrayList = activity_choose_role.dataBase.getComment();
        }
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.notifyDataSetChanged();
        activity_choose_role.count++;
    }

    public void nextWeek(View v) {

        daysOfWeek += 7;
        Week.setText(getWeek(daysOfWeek));
        GetParsedFromServer.GetScheduleForListView(activity_choose_role.currentSchedulesType,Dates[0],Dates[Dates.length-1]);
        start_page.scheduleArrayList = activity_choose_role.dataBase.getSchedule(start_page.Dates);
        activity_choose_role.commentArrayList = activity_choose_role.dataBase.getComment();
        if(!activity_choose_role.serverIsOnline) {
            updateActivity();
        }
    }

    public void prevWeek(View v) {
        daysOfWeek -= 7;
        Week.setText(getWeek(daysOfWeek));
        GetParsedFromServer.GetScheduleForListView(activity_choose_role.currentSchedulesType,Dates[0],Dates[Dates.length-1]);
        start_page.scheduleArrayList = activity_choose_role.dataBase.getSchedule(start_page.Dates);
        activity_choose_role.commentArrayList = activity_choose_role.dataBase.getComment();
        if(!activity_choose_role.serverIsOnline) {
            updateActivity();
        }
    }
    static ArrayList<schedule> scheduleArrayList = new ArrayList<>();
    //}
    //______________________________________________________________________

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
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

        static public String[] lessonsName = new String[]{"first lesson name", "second lesson name", "third lesson name", "fourth lesson name"};
        static public String[] roomNumb = new String[]{"100", "101", "104", "255"};
        static public String[] TeachersName = new String[]{"Qwerty Qwert1", "Qwerty Qwert1", "Qwerty Qwert1", "Qwerty Qwert1"};
        static public String[] Time = new String[]{"04:12", "04:13", "04:14", "04:15"};
        static public String[] group = new String[]{"asq-125", "den-643", "uyl-635", "fnz-513"};


        private String[] mWinterMonthsArray = new String[]{"comment 1 ls", "comment 1 ls", "comment 1 ls" };
        private String[] mSpringMonthsArray = new String[]{"comment 2 ls", "comment 2 ls", "comment 2 ls" };
        private String[] mSummerMonthsArray = new String[]{"no comments" };
        private String[] mAutumnMonthsArray = new String[]{"comment 4 ls", "comment 4 ls", "comment 4 ls" };


        static View rootView;


        ArrayList<ArrayList<String>> groups;
        ArrayList<String> children;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_start_page_scedule, container, false);
/*
            for (schedule s: start_page.scheduleArrayList) {
                if(s.getDate() == start_page.Dates[container.getId()])
                {
                    scheduleOfFragment.add(s);
                }
            }

*/

            //_____________________________________________________________________________________
            groups = new ArrayList<>();
            addToArrayList(mWinterMonthsArray);
            addToArrayList(mSpringMonthsArray);
            addToArrayList(mSummerMonthsArray);
            addToArrayList(mAutumnMonthsArray);
            //_____________________________________________________________________________________________________

            CurrDateSchedule(start_page.scheduleArrayList,getArguments().getInt(ARG_SECTION_NUMBER),activity_choose_role.commentArrayList);
            return rootView;
        }


        public void CurrDateSchedule(ArrayList<schedule> schedules,int section,ArrayList<Comment> commentArrayList)
        {
            ArrayList<schedule> currDateSchedule = new ArrayList<>();


            for (schedule s: schedules) {
                if(s.getDate().equals(start_page.Dates[section-1]))
                {
                    currDateSchedule.add(s);
                }
            }

            ArrayList<ArrayList<Comment>> comments = new ArrayList<>();
            Comment comment = new Comment();


            Collections.sort(currDateSchedule, new Comparator<schedule>() {
                @Override
                public int compare(schedule first, schedule second) {
                    try {
                        return (new SimpleDateFormat("HH:mm").parse(first.getTime())).compareTo(new SimpleDateFormat("HH:mm").parse(second.getTime()));
                    } catch (ParseException e) {
                        return 0;
                    }
                }
            });


            for(int q = 0; q < currDateSchedule.size();q++)
            {
                comments.add(new ArrayList<Comment>(Arrays.asList(comment)));
            }



            for(int i = 0; i < currDateSchedule.size();i++)
            {
                //  comments.add(new ArrayList<Comment>(Arrays.asList(comment)));
                int k = 0;
                for(int j = 0; j < commentArrayList.size(); j++)
                {
                    if(currDateSchedule.get(i).getId() == commentArrayList.get(j).getLessonId())
                    {
                        if(k==0) {
                            comments.get(i).clear();
                        }
                        comments.get(i).add(k, commentArrayList.get(j));

                        k++;
                    }
                }
            }

            ExpandableListView expandableListView = (ExpandableListView) rootView.findViewById(R.id.expListView);
            ExpListAdapter adapter = new ExpListAdapter(rootView.getContext(), comments, currDateSchedule);
            expandableListView.setAdapter(adapter);

        }


        public void addToArrayList(String[] ch) {

            children = new ArrayList<>();
            for (int i = 0; i < ch.length; i++) {
                children.add(ch[i]);
            }
            groups.add(children);
        }


        //customize extandablelist{

        public static class ExpListAdapter extends BaseExpandableListAdapter {

            private ArrayList<ArrayList<Comment>> mGroups;
            private Context mContext;
            ArrayList<schedule> mScheduleArrayList;
            public ExpListAdapter(Context context, ArrayList<ArrayList<Comment>> groups,ArrayList<schedule> scheduleArrayList) {
                mContext = context;
                mGroups = new ArrayList<ArrayList<Comment>>(scheduleArrayList.size());
                mGroups.addAll(groups);
                mScheduleArrayList = scheduleArrayList;
            }

            @Override
            public int getGroupCount() {
                return mScheduleArrayList.size();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
               // if(mGroups.)
             //   {
              //      mGroups.set(groupPosition,  new ArrayList<>(Arrays.asList("No Comments", "null")));
              //  }

                if (activity_choose_role.thisUser.getRole() == 1) {
                    return mGroups.get(groupPosition).size() + 1;
                } else return mGroups.get(groupPosition).size();

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
            public View getChildView(final int groupPosition, int childPosition, boolean isLastChild,
                                     View convertView, ViewGroup parent) {

                convertView = null;
            //    if(childPosition > 0) {
                    if (convertView == null) {
                        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = inflater.inflate(R.layout.comments_activity, null);

                    }

                    TextView comment = (TextView) convertView.findViewById(R.id.comment);
                    TextView commentName = (TextView) convertView.findViewById(R.id.commentName);
                    // if(mGroups.get(groupPosition).get(childPosition) != null) {


                    //   }
                    //   else
                    //   {
                    ///       comment.setText("No Comments");
                    //   }

                if (mGroups.get(groupPosition).size() == childPosition) {

                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.activity_add_comment, null);

                    Button addComment = (Button) convertView.findViewById(R.id.addComment);
                    final EditText note = (EditText) convertView.findViewById(R.id.note);



                    addComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(note.getText().length() != 0) {
                                if(activity_choose_role.currentSchedulesType.equals(activity_choose_role.dataBase.getUserInfo().getGroup_name())) {
                                    Comment c = new Comment();
                                    c.setName(activity_choose_role.dataBase.getUserInfo().getGroup_name());
                                    c.setMessage(note.getText().toString());
                                    c.setLessonId(mScheduleArrayList.get(groupPosition).getId());
                                    activity_choose_role.dataBase.addComments(new ArrayList<Comment>(Arrays.asList(c)));
                                }
                                activity_choose_role.makeToast("Замітку додано");
                                GetParsedFromServer.AddComment(mScheduleArrayList.get(groupPosition).getId()+"",note.getText().toString(),activity_choose_role.dataBase.getUserInfo().getGroup_name());

                                start_page.updateActivity();
                            }
                            else
                            {
                                activity_choose_role.makeToast("Введіть замітку");
                            }
                        }
                    });


                }
                else
                {
                    comment.setText(mGroups.get(groupPosition).get(childPosition).getMessage());
                    commentName.setText(mGroups.get(groupPosition).get(childPosition).getName());
                }
           //     }
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


        if (id == R.id.nav_camera) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.choose_sch_room, null);
            if(activity_choose_role.serverIsOnline) {
            choose("Виберіть кабінет", activity_choose_role.ScheduleInfo.getRoom().toArray(new String[activity_choose_role.ScheduleInfo.getRoom().size()]), view);
            }else Toast.makeText(context, " Server is offline " , Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.choose_sch_group, null);
            if(activity_choose_role.serverIsOnline) {
            choose("Виберіть групу", activity_choose_role.ScheduleInfo.getGroups().toArray(new String[activity_choose_role.ScheduleInfo.getGroups().size()]), view);
            }else Toast.makeText(context, " Server is offline " , Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            View view = LayoutInflater.from(getApplication()).inflate(R.layout.choose_sch_teacher, null);
            if(activity_choose_role.serverIsOnline) {
            choose("Виберіть викладача", activity_choose_role.ScheduleInfo.getTeachersNames().toArray(new String[activity_choose_role.ScheduleInfo.getTeachersNames().size()]), view);
            }else Toast.makeText(context, " Server is offline " , Toast.LENGTH_SHORT).show();
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

    public void choose(String title, String[] list, View view) {

        final ListView listView = (ListView) view.findViewById(R.id.listView);
        final Dialog dialog = new Dialog(this);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setSelector(android.R.color.darker_gray);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, list);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast.makeText(view.getContext(), "" + adapter.getItem(listView.getCheckedItemPosition()), Toast.LENGTH_SHORT).show();
                activity_choose_role.schFromList = 1;
                activity_choose_role.currentSchedulesType = adapter.getItem(listView.getCheckedItemPosition());
                refresh();
                dialog.cancel();
              //  GetParsedFromServer.GetScheduleForListView(activity_choose_role.currentSchedulesType,start_page.Dates[0],start_page.Dates[start_page.Dates.length-1]);
            }
        });


        listView.setAdapter(adapter);
        dialog.setContentView(view.getRootView());
        dialog.setTitle(title);
        dialog.show();


    }


    // }


}
