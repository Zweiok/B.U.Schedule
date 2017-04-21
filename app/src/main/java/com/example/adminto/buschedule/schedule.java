package com.example.adminto.buschedule;

/**
 * Created by adminto on 28.03.2017.
 */

public class schedule {
    int id;
    String lesson;
    int audience;
    String techer;
    String time;
    public void schedule(int id,String lesson,int audience,String techer,String time)
    {
        this.id = id;
        this.audience = audience;
        this.lesson = lesson;
        this.techer = techer;
        this.time = time;
    }

}
