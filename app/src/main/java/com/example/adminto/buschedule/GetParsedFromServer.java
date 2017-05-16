package com.example.adminto.buschedule;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PipedOutputStream;
import java.util.ArrayList;

/**
 * Created by V on 13.05.2017.
 */

public class GetParsedFromServer {

    static String methodName;

    public static void GetScheduleForDB(String Group, String StartDate, String EndDate) {
        new PostToWeb().execute("GetSchedule", Group, StartDate, EndDate);
        GetParsedFromServer.methodName = "GetScheduleForDB";
    }
    public static void GetScheduleForListView(String Group, String StartDate, String EndDate) {
        new PostToWeb().execute("GetSchedule", Group, StartDate, EndDate);
        GetParsedFromServer.methodName = "GetScheduleForListView";
    }

    public static void CheckUser(String Email, String Password) {
        new PostToWeb().execute("Check", Email, Password);
        GetParsedFromServer.methodName = "CheckUser";
    }

    public static void RegisterStudent(String Group, String Id) {
        new PostToWeb().execute("Register", "Student", Group, Id);
        GetParsedFromServer.methodName = "RegisterStudent";
    }

    public static void RegisterTeacher(String Email, String Password, String Id) {
        new PostToWeb().execute("Register", "Prof", Email, Password, Id);
        GetParsedFromServer.methodName = "RegisterTeacher";
    }

    public static void AddComment(String LessonId, String Message, String Name) {
        new PostToWeb().execute("AddComment", LessonId, Message, Name);
        GetParsedFromServer.methodName = "AddComment";
    }

    // {id} {time} {name} {group} {prof] {room}
    public static ArrayList<schedule> GetSchedule1() {
        ArrayList<schedule> arrayList = new ArrayList<schedule>();
        schedule schedulee;
        if (activity_choose_role.serverIsOnline) {


            try {
                // Create the root JSONObject from the JSON string.
               // JSONObject jsonRootObject = new JSONObject();

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = new JSONArray(PostToWeb.HttpResponse);

                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject jsonObject = jsonArray.getJSONObject(i);
/*
                    schedulee = new schedule(
                            Integer.parseInt(jsonObject.optString("id").toString()),
                            jsonObject.optString("time").toString(),
                            jsonObject.optString("name").toString(),
                            jsonObject.optString("group").toString(),
                            jsonObject.optString("prof").toString(),
                            jsonObject.optString("room").toString()
                    );

     */             String[] DateTime= jsonObject.optString("time").toString().split(" ");

                    String[] t = DateTime[1].split(":");
                    String time = t[0]+":" + t[1];

                    schedulee = new schedule();
                    schedulee.setGroup(jsonObject.optString("group").toString());
                    schedulee.setId(Integer.parseInt(jsonObject.optString("id").toString()));
                    schedulee.setName(jsonObject.optString("name").toString());
                    schedulee.setProf(jsonObject.optString("prof").toString());
                    schedulee.setTime(time);
                    schedulee.setDate(DateTime[0]);
                    schedulee.setRoom(jsonObject.optString("room").toString());

                    arrayList.add(schedulee);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return arrayList;
        } else {
            activity_choose_role.makeToast("Server is offline");
            return arrayList;
        }
    }

    public static String CheckUser1() {
        if (activity_choose_role.serverIsOnline) {
            return PostToWeb.HttpResponse;

        } else {
            activity_choose_role.makeToast("Server is offline");
            return null;
        }
    }

    public static String[] RegisterStudent1() {
        String[] s = null;
        if (activity_choose_role.serverIsOnline) {
            try {
                // Create the root JSONObject from the JSON string.
                // JSONObject jsonRootObject = new JSONObject();

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = new JSONArray(PostToWeb.HttpResponse);

                


            }catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            activity_choose_role.makeToast("Server is offline");
        }
        return s;
    } // TODO: 15.05.2017  

    // {State} {ProfName}
    public static String[] RegisterTeacher1() {
        String[] s = null;
        if (activity_choose_role.serverIsOnline) {
            try {
                // Create the root JSONObject from the JSON string.
                // JSONObject jsonRootObject = new JSONObject();

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = new JSONArray(PostToWeb.HttpResponse);




            }catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            activity_choose_role.makeToast("Server is offline");
        }
        return s;
    } // TODO: 15.05.2017

    public static boolean AddComment1() {
        boolean check = false;
        String s = "false";
        if (activity_choose_role.serverIsOnline) {
            if(PostToWeb.HttpResponse != s)
            {
                check = true;
            }
        } else {
            activity_choose_role.makeToast("Server is offline");
        }
        return check;
    }

// TODO: 13.05.2017
    /*
    public static void GetComments(String LessonId, String Message, String Name)
    {
        if(activity_choose_role.serverIsOnline) {
            PostToWeb postToWeb = new PostToWeb();
            postToWeb.execute("AddComment",LessonId,Message,Name);
        }
        else
        {
            activity_choose_role.makeToast("Server is offline");
        }

    }
*/
}
