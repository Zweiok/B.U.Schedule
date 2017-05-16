package com.example.adminto.buschedule;

/**
 * Created by adminto on 28.03.2017.
 */

public class schedule {
    public int id;
    public String time;
    public String date;
    public String name;
    public String group;
    public String prof;
    public String room;

    public schedule(int id, String time, String date, String name, String group, String prof, String room) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.name = name;
        this.group = group;
        this.prof = prof;
        this.room = room;
    }

    public schedule() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
