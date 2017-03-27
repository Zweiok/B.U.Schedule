package com.example.adminto.buschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by adminto on 16.02.2017.
 */

public class DataBase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "my_db";
    public static final String TABLE_TASKS = "Tasks";
    public static final String KEY_TIME = "Time";
    public static final String KEY_DATE = "Date";
    public static final String KEY_TASK = "Task";
    public static final String KEY_DAILY = "Daily";
    public static final String KEY_ID = "_id";

    SimpleDateFormat curFormater = new SimpleDateFormat("dd.MM.yyyy");
    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
                + KEY_TIME + " text," + KEY_DATE + " text,"
                + KEY_TASK + " text," + KEY_DAILY + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_TASKS);
        onCreate(db);
    }

    SQLiteDatabase DB;

    public void insertTask()
    {
    }

    public void deleteTask()
    {
    }
}
