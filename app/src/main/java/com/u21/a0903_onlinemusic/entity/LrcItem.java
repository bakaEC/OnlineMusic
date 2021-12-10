package com.u21.a0903_onlinemusic.entity;


import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LrcItem {

    private int start;
    private String lrc;
    String time = null;

    public LrcItem(JSONObject object){
        start=object.optInt("start");
        lrc=object.optString("lrc");
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString(){
        return "LrcItem[start="+start+",lrc="+lrc+"]";
    }
}
