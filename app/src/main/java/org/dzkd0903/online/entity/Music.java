package org.dzkd0903.online.entity;

import java.util.Date;

import android.graphics.Bitmap;

public class Music {
	private Bitmap img;
	private String name;
	private String singer;
	private String album;
	private Date time;
	
	public Music(Bitmap img, String name, String singer, String album, Date time) {
		super();
		this.img = img;
		this.name = name;
		this.singer = singer;
		this.album = album;
		this.time = time;
	}
	
	public Bitmap getImg() {
		return img;
	}
	public void setImg(Bitmap img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}

	
}
