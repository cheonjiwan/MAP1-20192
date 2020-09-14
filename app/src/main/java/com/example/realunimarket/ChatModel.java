package com.example.realunimarket;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

public class ChatModel implements Comparable<ChatModel> {

//    private int boardid;
    private int  picture;
//    private int otherid;

    private String name;
    private String timelog;
    private String text;

    public ChatModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public ChatModel(int picture,String name ,String text,String timelog) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        this.name = name;
        //this.boardid = boardid;
        this.timelog =   timelog;
        this.text = text;
        this.picture = picture;
       // this.otherid = otherid;
    }

//    public int getBoardid() {
//        return boardid;
//    }

    public String getName() {
        return name;
    }

    public String getTimelog() {
        return timelog;
    }

    public String getText() {
        return text;
    }
    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimelog(String timelog) {
        this.timelog = timelog;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int compareTo(ChatModel item)
    {
        return timelog.compareTo(item.getTimelog());
    }



//    public int getOtherid() {
//        return otherid;
//    }
}
