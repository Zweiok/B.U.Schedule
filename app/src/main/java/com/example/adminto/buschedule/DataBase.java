package com.example.adminto.buschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    public static final String KEY_READING_SCHEDULE = "_rschedule";
    public static final String KEY_LOG = "_login";
    public static final String KEY_PAS = "_password";


    SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_COMMENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_NAME + " text," + KEY_LID + " integer,"
                + KEY_COMMENT + " text" +  ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_SCHEDULE + "(" + KEY_ID + " INTEGER,"
                + KEY_TIME + " text,"+ KEY_DATE + " text," + KEY_NAME + " text,"
                + KEY_GROUP + " text," + KEY_PROF + " text," + KEY_ROOM + " integer" +")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER_INFO + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_ROLE + " INTEGER," + KEY_READING_SCHEDULE + " text,"
                + KEY_LOG + " text," + KEY_PAS + " text," + KEY_NAME + " text" +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_COMMENTS);
        onCreate(db);
}

    static SQLiteDatabase db;


    public void addSchedule(ArrayList<schedule> schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv ;
        for(int i=0; i < schedule.size(); i++) {
            cv = new ContentValues();

            cv.put(KEY_ID,schedule.get(i).getId());
            cv.put(KEY_TIME,schedule.get(i).getTime());
            cv.put(KEY_DATE,schedule.get(i).getDate());
            cv.put(KEY_NAME,schedule.get(i).getName());
            cv.put(KEY_GROUP,schedule.get(i).getGroup());
            cv.put(KEY_PROF,schedule.get(i).getProf());
            cv.put(KEY_ROOM,schedule.get(i).getRoom());

            db.insert(TABLE_SCHEDULE, null, cv);
        }
        db.close();



    }

    public void updateSchedule(ArrayList<schedule> schedule) {


        db = this.getWritableDatabase();

        ContentValues cv ;
        for(int i=0; i < schedule.size(); i++) {
            cv = new ContentValues();

            cv.put(KEY_TIME,schedule.get(i).getTime());
            cv.put(KEY_DATE,schedule.get(i).getDate());
            cv.put(KEY_NAME,schedule.get(i).getName());
            cv.put(KEY_GROUP,schedule.get(i).getGroup());
            cv.put(KEY_PROF,schedule.get(i).getProf());
            cv.put(KEY_ROOM,schedule.get(i).getRoom());

            db.update(TABLE_USER_INFO, cv, KEY_ID + " = ?", new String[]{String.valueOf(schedule.get(i).getId())});
        }
        db.close();

    }

    public ArrayList<schedule> getSchedule(String[] Dates) {
        ArrayList<schedule> schedules = new ArrayList<schedule>();
        schedule s;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        for(int i = 0; i < Dates.length-1;i++) {

            String selectQuery = "SELECT  * FROM " + TABLE_SCHEDULE + " WHERE " + KEY_DATE + " = '" + Dates[i]+ "'" ;


            c = db.rawQuery(selectQuery, null);

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
            db.close();

        }

        return schedules;
    } // TODO: 13.05.2017

    public void updateRole(int role) {

        String countQuery = "SELECT * FROM " + TABLE_USER_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        if (cursor.getCount() > 0) {
            db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(KEY_ROLE, role);
            db.update(TABLE_USER_INFO, cv, KEY_ID + " = ?", new String[]{String.valueOf(1)});
            db.close();
        } else {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(KEY_ROLE, role);
            db.insert(TABLE_USER_INFO, null, cv);
            db.close(); // Closing database connection
        }
    }

    public void updateLogPass(String Login, String Pass,String Name) {
        String countQuery = "SELECT * FROM " + TABLE_USER_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        if (cursor.getCount() > 0) {
            db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(KEY_LOG, Login);
            cv.put(KEY_PAS, Pass);
            cv.put(KEY_NAME, Name);
            db.update(TABLE_USER_INFO, cv, KEY_ID + " = ?", new String[]{String.valueOf(1)});
            db.close();
        } else {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(KEY_LOG, Login);
            cv.put(KEY_PAS, Pass);
            cv.put(KEY_NAME, Name);
            db.insert(TABLE_USER_INFO, null, cv);
            db.close(); // Closing database connection
        }
    }

    public void updateReadingSchedule(String schedule) {
        String countQuery = "SELECT * FROM " + TABLE_USER_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        if (cursor.getCount() > 0) {
            db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(KEY_READING_SCHEDULE, schedule);
            db.update(TABLE_USER_INFO, cv, KEY_ID + " = ?", new String[]{String.valueOf(1)});
            db.close();
        } else {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(KEY_READING_SCHEDULE, schedule);
            db.insert(TABLE_USER_INFO, null, cv);
            db.close(); // Closing database connection
        }

    }

    public String getReadingSchedule() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER_INFO, new String[]{KEY_READING_SCHEDULE}, KEY_ID + "=?",
                new String[]{String.valueOf(1)}, null, null, null, null);


        return cursor.getString(0);
    }

    public void addComments(String name, int lid, String comment) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_LID, lid);
        cv.put(KEY_COMMENT, comment);
        db.insert(TABLE_COMMENTS, null, cv);
        db.close(); // Closing database connection
    }

    public ArrayList getComment() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COMMENTS, new String[]{KEY_NAME, KEY_LID, KEY_COMMENT}, KEY_ID + "=?",
                new String[]{String.valueOf(1)}, null, null, null, null);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll((Collection) cursor);
        return arrayList;
    }
}
