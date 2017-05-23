package com.example.adminto.buschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by adminto on 16.02.2017.
 */

public class DataBase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "my_db";
    public static final String KEY_ID = "_id";

    public static final String TABLE_COMMENTS = "_comments";
    public static final String KEY_LID = "_lesson_id";
    public static final String KEY_COMMENT = "_comment";

    public static final String TABLE_SCHEDULE = "_schedule";
    public static final String KEY_TIME = "_time";
    public static final String KEY_DATE = "_date";
    public static final String KEY_NAME = "_name";
    public static final String KEY_GROUP = "_group";
    public static final String KEY_PROF = "_prof";
    public static final String KEY_ROOM = "_room";


    public static final String TABLE_USER_INFO = "_user";
    public static final String KEY_ROLE = "_role";
    public static final String KEY_GROUP_NAME = "_rschedule";
    public static final String KEY_LOG = "_login";
    public static final String KEY_PAS = "_password";



    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_COMMENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_NAME + " text," + KEY_LID + " integer,"
                + KEY_COMMENT + " text" + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_SCHEDULE + "(" + KEY_ID + " INTEGER,"
                + KEY_TIME + " text," + KEY_DATE + " text," + KEY_NAME + " text,"
                + KEY_GROUP + " text," + KEY_PROF + " text," + KEY_ROOM + " integer" + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER_INFO + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_ROLE + " INTEGER," + KEY_GROUP_NAME + " text,"
                + KEY_LOG + " text," + KEY_PAS + " text," + KEY_NAME + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_COMMENTS);
        onCreate(db);
    }

    static SQLiteDatabase db;

    public void deleteUserInfo() {
        db = getWritableDatabase();
        db.execSQL("delete from " + TABLE_USER_INFO);
    }

    public void deleteSchedule() {
        db = getWritableDatabase();
        db.execSQL("delete from " + TABLE_SCHEDULE);
    }

    public void deleteComments() {
        db = getWritableDatabase();
        db.execSQL("delete from " + TABLE_COMMENTS);
    }


    public void addSchedule(ArrayList<schedule> schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv;
        for (int i = 0; i < schedule.size(); i++) {
            cv = new ContentValues();

            cv.put(KEY_ID, schedule.get(i).getId());
            cv.put(KEY_TIME, schedule.get(i).getTime());
            cv.put(KEY_DATE, schedule.get(i).getDate());
            cv.put(KEY_NAME, schedule.get(i).getName());
            cv.put(KEY_GROUP, schedule.get(i).getGroup());
            cv.put(KEY_PROF, schedule.get(i).getProf());
            cv.put(KEY_ROOM, schedule.get(i).getRoom());

            db.insert(TABLE_SCHEDULE, null, cv);
        }
        db.close();


    }

    public ArrayList<schedule> getSchedule(String[] Dates) {
        ArrayList<schedule> schedules = new ArrayList<schedule>();
        schedule s;
        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor c;
        for (int i = 0; i < Dates.length; i++) {

            String selectQuery = "SELECT * FROM " + TABLE_SCHEDULE + " WHERE " + KEY_DATE + " = '" + Dates[i] + "'";

            Cursor c = db.rawQuery(selectQuery, null);

            c.moveToFirst();
            if (c.moveToFirst()) {
                do {
                    s = new schedule();
                    s.setGroup(c.getString(c.getColumnIndex(KEY_GROUP)));
                    s.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                    s.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                    s.setProf(c.getString(c.getColumnIndex(KEY_PROF)));
                    s.setTime(c.getString(c.getColumnIndex(KEY_TIME)));
                    s.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                    s.setRoom(c.getString(c.getColumnIndex(KEY_ROOM)));
                    schedules.add(s);
                } while (c.moveToNext());
            }
            c.close();


        }
        db.close();
        return schedules;
    }

    public void setUserInfo(user User) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_LOG, User.getEmail());
        cv.put(KEY_GROUP_NAME, User.getGroup_name());
        cv.put(KEY_PAS, User.getPass());
        cv.put(KEY_ROLE, User.getRole());


        db.insert(TABLE_USER_INFO, null, cv);
    }

    public user getUserInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        user User;
        String selectQuery = "SELECT * FROM " + TABLE_USER_INFO;
        User = new user();

        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        if (c.isFirst()) {
            User.setEmail(c.getString(c.getColumnIndex(KEY_LOG)));
            User.setGroup_name(c.getString(c.getColumnIndex(KEY_GROUP_NAME)));
            User.setPass(c.getString(c.getColumnIndex(KEY_PAS)));
            User.setRole(c.getInt(c.getColumnIndex(KEY_ROLE)));

        } else {
            User.setRole(3);
        }

        db.close();

        return User;
    }

    public void addComments(ArrayList<Comment> arrayList) {
        db = this.getWritableDatabase();


        ContentValues cv;

        for ( Comment c : arrayList) {
            cv = new ContentValues();
            cv.put(KEY_NAME, c.getName());
            cv.put(KEY_LID, c.getLessonId());
            cv.put(KEY_COMMENT, c.getMessage());
            db.insert(TABLE_COMMENTS, null, cv);
        }



        db.close(); // Closing database connection
    }

    public ArrayList<Comment> getComment() {
        ArrayList<Comment> arrayComments = new ArrayList<Comment>();
        Comment com;
        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor c;

        String selectQuery = "SELECT * FROM " + TABLE_COMMENTS;

        Cursor c = db.rawQuery(selectQuery, null);

        c.moveToFirst();
        if (c.moveToFirst()) {
            do {
                com = new Comment();

                com.setMessage(c.getString(c.getColumnIndex(KEY_COMMENT)));
                com.setLessonId(c.getInt(c.getColumnIndex(KEY_LID)));
                com.setName(c.getString(c.getColumnIndex(KEY_NAME)));

                arrayComments.add(com);
            } while (c.moveToNext());
        }
        c.close();



        db.close();
        return arrayComments;
    }
}
