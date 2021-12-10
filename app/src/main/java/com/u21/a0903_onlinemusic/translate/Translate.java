package com.u21.a0903_onlinemusic.translate;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

import com.u21.a0903_onlinemusic.translate.src.TransApi;

import org.json.JSONArray;
import org.json.JSONObject;

public class Translate {

    private static final String APP_ID = "20211019000976564";
    private static final String SECURITY_KEY = "kabbACDBAHNopUQLUJPI";




    public String trans_str(String origin,String from) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        if (from=="zh") {
            return api.getTransResult(origin, "auto", "en");
        }else if(from=="en"){
            return api.getTransResult(origin, "auto", "zh");
        }else {
            return api.getTransResult(origin, "auto", "zh");
        }
    }

    public static String trans_str(String origin, String from, String to){
        if (origin != null){

        String result_json=translate(origin, from, to);
        String result_str = null;

        try {
            JSONObject jsonObject = new JSONObject(result_json);
            result_str= jsonObject.getString("trans_result");
            JSONArray jsonObject1 = new JSONArray(result_str);
            JSONObject jsonObject2 = (JSONObject) jsonObject1.get(0);
            result_str=jsonObject2.getString("dst");

        }catch (Exception e){
            e.printStackTrace();

        }
        
        return result_str;
        }else {
            return "null";
        }
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    private static String translate(String origin, String from, String to){
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        return api.getTransResult(origin, from, to);

    }



}
