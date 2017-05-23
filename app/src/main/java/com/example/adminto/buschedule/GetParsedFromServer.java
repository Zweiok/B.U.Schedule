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
        GetParsedFromServer.methodName = "GetScheduleForDB";
        new PostToWeb().execute("GetSchedule", Group, StartDate, EndDate);

    }
    public static void GetScheduleForListView(String Group, String StartDate, String EndDate) {
        GetParsedFromServer.methodName = "GetScheduleForListView";
        new PostToWeb().execute("GetSchedule", Group, StartDate, EndDate);

    }

    public static void CheckUser(String Email, String Password) {
        GetParsedFromServer.methodName = "CheckUser";
        new PostToWeb().execute("Check", Email, Password);

    }

    public static void RegisterStudent(String Group, String Id) {
        GetParsedFromServer.methodName = "RegisterStudent";
        new PostToWeb().execute("Register", "Student", Group, Id);
    }

    public static void RegisterTeacher(String Email, String Password, String Id) {
        GetParsedFromServer.methodName = "RegisterTeacher";
        new PostToWeb().execute("Register", "Prof", Email, Password, Id);
    }

    public static void AddComment(String LessonId, String Message, String Name) {
        GetParsedFromServer.methodName = "AddComment";
        new PostToWeb().execute("AddComment", LessonId, Message, Name);
    }
    public static void GetComment(String Group, String StartDate, String EndDate) {
        GetParsedFromServer.methodName = "GetComments";
        new PostToWeb().execute("GetComments", Group, StartDate, EndDate);
    }
    public static void GetScheduleInfo() {
        GetParsedFromServer.methodName = "GetScheduleInfo";
        new PostToWeb().execute("GetGRL");
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

                    String[] DateTime= jsonObject.optString("time").toString().split(" ");

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

    public static boolean CheckUser1() {
        boolean s = false;
        if (activity_choose_role.serverIsOnline) {
            try {
                // Create the root JSONObject from the JSON string.
                // JSONObject jsonRootObject = new JSONObject();

                //Get the instance of JSONArray that contains JSONObjects

                JSONObject jsonObject = new JSONObject(PostToWeb.HttpResponse);
                if(jsonObject.optString("State").toString().equals("true")) {
                    s = true;
                }


            }catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            activity_choose_role.makeToast("Server is offline");
        }
        return s;
    }

    public static boolean RegisterStudent1() {
        boolean s = false;
        if (activity_choose_role.serverIsOnline) {
            try {
                // Create the root JSONObject from the JSON string.
                // JSONObject jsonRootObject = new JSONObject();

                //Get the instance of JSONArray that contains JSONObjects

                JSONObject jsonObject = new JSONObject(PostToWeb.HttpResponse);
                if(jsonObject.optString("State").toString().equals("true")) {
                  s = true;
                }


            }catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            activity_choose_role.makeToast("Server is offline");
        }
        return s;
    }

    // {State} {ProfName}
    public static String[] RegisterTeacher1() {
        String[] s = {"",""};
        if (activity_choose_role.serverIsOnline) {
            try {
                // Create the root JSONObject from the JSON string.
                // JSONObject jsonRootObject = new JSONObject();

                //Get the instance of JSONArray that contains JSONObjects
              //  JSONArray jsonArray = new JSONArray(PostToWeb.HttpResponse);
                JSONObject jsonObject = new JSONObject(PostToWeb.HttpResponse);

                s[0]= jsonObject.optString("State");
                s[1]= jsonObject.optString("Info");

                if(jsonObject.optString("State").toString().equals("true")) {
                    s[1]= jsonObject.optString("ProfName");
                }

            }catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            activity_choose_role.makeToast("Server is offline");
        }
        return s;
    }



    public static ArrayList<Comment> GetComment1()
    {
        ArrayList<Comment> comments = new ArrayList<>(); 
        Comment comment;


        if (activity_choose_role.serverIsOnline) {


            try {
                // Create the root JSONObject from the JSON string.
                // JSONObject jsonRootObject = new JSONObject();

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = new JSONArray(PostToWeb.HttpResponse);

                //Iterate the jsonArray and print the info of JSONObjects

                for (int i = 0; i < jsonArray.length(); i++) {
                    comment = new Comment();

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if(jsonObject.length() > 0) {
                        comment.setName(jsonObject.optString("Name").toString());
                        comment.setLessonId(Integer.parseInt(jsonObject.optString("LessonId").toString()));
                        comment.setMessage(jsonObject.optString("Commentary").toString());
                    }
                    comments.add(comment);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            activity_choose_role.makeToast("Server is offline");
        }
        
        return comments;
    }

    public static scheduleInfo ScheduleInfo()
    {
        scheduleInfo S = new scheduleInfo();
        ArrayList<String> arrayGroup = new ArrayList<>();
        ArrayList<String> arrayName = new ArrayList<>();
        ArrayList<String> arrayRoom = new ArrayList<>();
        if (activity_choose_role.serverIsOnline) {

            try {
                // Create the root JSONObject from the JSON string.
                // JSONObject jsonRootObject = new JSONObject();

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = new JSONArray(PostToWeb.HttpResponse);

                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONArray JsonArray = jsonArray.getJSONArray(i);

                    for(int k = 0; k < JsonArray.length(); k++)
                    {
                        JSONObject jsonObject = JsonArray.getJSONObject(k);

                        if(i == 0) {
                            arrayGroup.add(jsonObject.optString("Name").toString());
                        }

                        if(i == 1) {

                            arrayRoom.add(jsonObject.optString("Name").toString());
                        }
                        if(i == 2) {

                            arrayName.add(jsonObject.optString("Name").toString());
                        }


                    }


                }

                S.setGroups(arrayGroup);
                S.setRoom(arrayRoom);
                S.setTeachersNames(arrayName);
            } catch (JSONException e) {
                e.printStackTrace();
            }}
        else {
            activity_choose_role.makeToast("Server is offline");
            return S;
            }




        return S;
    }

}
