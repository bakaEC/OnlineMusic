package com.u21.a0903_onlinemusic.net;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Net {
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public static String get(String strurl) throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        StringBuffer sb = new StringBuffer();
        URL url = new URL(strurl);
        //建立网络连接
        URLConnection connection = url.openConnection();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置传输方法和编码格式
        conn.setRequestMethod("GET");
        conn.setRequestProperty("encoding", "UTF-8");
        //设置网络连接时间
        conn.setReadTimeout(5000);
        connection.setUseCaches(false);
        //访问服务端
        if(conn.getResponseCode()==200){
            InputStream inputStream= conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //建立容器（字符串）转载网络获取的一行字符
            String line = null;
            while ((line=bufferedReader.readLine())!=null){
                sb.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        }

        return sb.toString();
    }
    public static Bitmap bmp(String strUrl) throws Exception {
        Bitmap bitmap = null;

        URL url = new URL(strUrl);
        //通过url简历下载链接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //网页请求方式
        connection.setRequestMethod("GET");
        //链接超时时间
        connection.setReadTimeout(5000);
        //是否使用下载缓存
        connection.setUseCaches(true);

        if (connection.getResponseCode()==200){
            bitmap= BitmapFactory.decodeStream(connection.getInputStream());
        }


        return bitmap;
    }
}








