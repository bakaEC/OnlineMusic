package com.u21.a0903_onlinemusic.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

import com.u21.a0903_onlinemusic.net.Net;

public class Music {
    private Bitmap logo;
    private String name;



    public Music(String lrcName) throws Exception {
        this.name = lrcName;
        this.logo = Net.bmp("http://10.1.7.5:8080/web_war_exploded/mv/"+name+"/"+name+".jpg");
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Bitmap loadCover(String path) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(path);
        byte[] cover = mediaMetadataRetriever.getEmbeddedPicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);
        return  bitmap;
    }


}