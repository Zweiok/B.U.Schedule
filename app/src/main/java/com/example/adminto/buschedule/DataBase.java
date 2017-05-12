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

    public static final String TABLE_COMMENTS = "Comments";
    public static final String KEY_LID = "LessonId";
    public static final String KEY_COMMENT = "Comment";

    public static final String TABLE_SCHEDULE = "Schedule";
    public static final String KEY_DATE_TIME = "Time";
    public static final String KEY_NAME = "Name";
    public static final String KEY_GROUP = "Group";
    public static final String KEY_PROF = "Prof";
    public static final String KEY_ROOM = "Room";


    public static final String TABLE_USER_INFO = "User";
    public static final String KEY_ROLE = "Role";
    public static final String KEY_READING_SCHEDULE = "RSchedule";
    public static final String KEY_LOG = "Login";
    public static final String KEY_PAS = "Password";


    SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_COMMENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_NAME + " text," + KEY_LID + " integer,"
                + KEY_COMMENT + "text" + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SCHEDULE + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_DATE_TIME + " text," + KEY_NAME + " text,"
                + KEY_GROUP + " text," + KEY_PROF + " text" + KEY_ROOM + " integer" + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER_INFO + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_ROLE + " INTEGER," + KEY_READING_SCHEDULE + " text," + KEY_LOG + " text," + KEY_PAS + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + "*");
        onCreate(db);
    }

    SQLiteDatabase db;


    public void addSchedule(ArrayList<schedule> schedule) {}

    public void updateSchedule(ArrayList<schedule> schedule) {}

    public ArrayList<schedule> getSchedule() {return null;}

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

    public void updateLogPass(String Login, String Pass) {
        String countQuery = "SELECT * FROM " + TABLE_USER_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        if (cursor.getCount() > 0) {
            db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(KEY_LOG, Login);
            cv.put(KEY_PAS, Pass);
            db.update(TABLE_USER_INFO, cv, KEY_ID + " = ?", new String[]{String.valueOf(1)});
            db.close();
        } else {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(KEY_LOG, Login);
            cv.put(KEY_PAS, Pass);
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
