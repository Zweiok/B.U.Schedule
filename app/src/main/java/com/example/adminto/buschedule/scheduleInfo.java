package com.example.adminto.buschedule;

import java.util.ArrayList;

/**
 * Created by V on 21.05.2017.
 */

public class scheduleInfo {
    ArrayList<String> room;
    ArrayList<String> teachersNames;
    ArrayList<String> groups;

    public scheduleInfo(ArrayList<String> room, ArrayList<String> teachersNames, ArrayList<String> groups) {
        this.room = room;
        this.teachersNames = teachersNames;
        this.groups = groups;
    }

    public scheduleInfo() {
    }

    public ArrayList<String> getRoom() {
        return room;
    }

    public void setRoom(ArrayList<String> room) {
        this.room = room;
    }

    public ArrayList<String> getTeachersNames() {
        return teachersNames;
    }

    public void setTeachersNames(ArrayList<String> teachersNames) {
        this.teachersNames = teachersNames;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }
}
