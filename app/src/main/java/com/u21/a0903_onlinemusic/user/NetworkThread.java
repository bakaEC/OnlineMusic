package com.u21.a0903_onlinemusic.user;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

public class NetworkThread implements Callable<String> {
    private final String name;
    private final String password;
    private final String url;

    public NetworkThread(String name, String password, String url) {
        this.name = name;
        this.password = password;
        this.url = url;
    }

    @Override
    public String call(){
        try {
            //开启连接
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            //拼接数据
            String data = "name="+ URLEncoder.encode(name, "utf-8")+"&password="+URLEncoder.encode(password,"utf-8");
            //设置请求方法
            connection.setRequestMethod("POST");
            //允许输入输出
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //写数据（也就是发送数据）
            connection.getOutputStream().write(data.getBytes("utf-8"));
            byte [] bytes = new byte[1024];
            //获取返回的数据
            int len = connection.getInputStream().read(bytes);
            return new String(bytes,0,len,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}