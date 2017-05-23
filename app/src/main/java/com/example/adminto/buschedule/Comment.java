package com.example.adminto.buschedule;

/**
 * Created by V on 21.05.2017.
 */

public class Comment {

    int LessonId;
    String Message = "No Comments";
    String Name = "";


    public Comment() {
    }

    public int getLessonId() {
        return LessonId;
    }

    public void setLessonId(int lessonId) {
        LessonId = lessonId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
