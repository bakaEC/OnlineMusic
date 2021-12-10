package com.u21.a0903_onlinemusic.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
//将歌词文件转化为歌词对象

public class Lrc {

    private String title;
    private String author;
    private String album;
    private String lauthor;
    private String[] lrc;
    private List<LrcItem> list=new ArrayList<LrcItem>();

    public Lrc(JSONObject object) throws JSONException {
        title=object.optString("ti");
        author=object.optString("ar");
        album= object.optString("al");
        lauthor=object.optString("by");

        JSONArray array = object.optJSONArray("list");

        for (int i =0;i< array.length();i++){
            JSONObject objitem=array.getJSONObject(i);
            LrcItem item=new LrcItem(objitem);
            list.add(item);
        }
    }


    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ti="+title);
        stringBuffer.append("au="+author);
        stringBuffer.append("al="+album);
        stringBuffer.append("by="+lauthor);
        stringBuffer.append(list);
        return stringBuffer.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLauthor() {
        return lauthor;
    }

    public void setLauthor(String lauthor) {
        this.lauthor = lauthor;
    }

    public String[] getLrc() {
        return lrc;
    }

    public void setLrc(String[] lrc) {
        this.lrc = lrc;
    }

    public List<LrcItem> getList() {
        return list;
    }

    public void setList(List<LrcItem> list) {
        this.list = list;
    }


    public JSONObject toJson(){
        JSONObject object = new JSONObject();

        return object;
    }
}

