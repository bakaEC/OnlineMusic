package com.u21.a0903_onlinemusic.translate.pic.data;

public class Config {

    public static final int ERASE_NONE = 0;

    public static final int ERASE_FULL = 1;

    public static final int ERASE_BLOCK = 2;

    public static final int PASTE_NONE = 0;

    public static final int PASTE_FULL = 1;

    public static final int PASTE_BLOCK = 2;


    private final String appId;
    private final String secretKey;
    private String from;
    private String to;
    private String picPath;
    private int erase = ERASE_FULL;
    private int paste = PASTE_FULL;


    public Config(String appId, String secretKey) {
        this.appId = appId;
        this.secretKey = secretKey;
    }


    public void lang(String from, String to) {
        this.from = from;
        this.to = to;
    }


    public void pic(String filePath) {
        this.picPath = filePath;
    }


    public void erase(int erase) {
        this.erase = erase;
    }

    public void paste(int paste) {
        this.paste = paste;
    }

    public String getAppId() {
        return appId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getPicPath() {
        return picPath;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getErase() {
        return erase;
    }

    public int getPaste() {
        return paste;
    }
}
