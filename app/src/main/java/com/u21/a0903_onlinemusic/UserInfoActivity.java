package com.u21.a0903_onlinemusic;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.kongzue.dialogx.dialogs.BottomDialog;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;

import com.u21.a0903_onlinemusic.net.Net;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.FutureTask;
import java.util.zip.Inflater;


public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {


    private final ObjectMapper mapper = new ObjectMapper();
    private Button login;
    private Button register;
    private String Susername = "";
    private String Spassword = "";

    public String resp = null;
    private Boolean loginR = false;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:

                BottomDialog.show(
                        new OnBindView<BottomDialog>(R.layout.user_register) {
                            @Override
                            public void onBind(BottomDialog dialog, View v) {

                                Button button =v.findViewById(R.id.reg_apply);
                                EditText username= v.findViewById(R.id.reg_name);
                                EditText password = v.findViewById(R.id.reg_password);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Susername = username.getText().toString();
                                        Spassword = password.getText().toString();
                                        Log.e("reg", "点击注册");

                                        try {
                                            if (Susername != null & Spassword != null) {
                                                signup(Susername, Spassword);
                                            } else {
                                                TipDialog.show("请完整填写信息");
                                            }
                                            Log.e("reg", resp);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        Susername = null;
                                        Spassword = null;

                                        TimerTask task = new TimerTask() {
                                            public void run() {
                                                TipDialog.show("注册成功", WaitDialog.TYPE.SUCCESS);
                                            }
                                        };
                                        Timer timer = new Timer();
                                        timer.schedule(task, 300);
                                        try {
                                            Runtime runtime = Runtime.getRuntime();
                                            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                });

                            }

                        });
                break;
            case R.id.login:

                BottomDialog.show(
                        new OnBindView<BottomDialog>(R.layout.user_login) {
                            @Override
                            public void onBind(BottomDialog dialog, View v) {

                                Button button =v.findViewById(R.id.log_apply);
                                TextView textView = v.findViewById(R.id.log_text);
                                EditText username= v.findViewById(R.id.log_name);
                                EditText password = v.findViewById(R.id.log_password);
                                CoordinatorLayout coordinatorLayout = v.findViewById(R.id.logincord);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        setUser(username.getText().toString());
                                        setPassword(password.getText().toString());
                                        Log.e("log", "登录");

                                        try {
                                            loginR = signin(getUser(), getPassword());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        if (loginR) {
                                            Runtime runtime = Runtime.getRuntime();
                                            TimerTask task = new TimerTask() {
                                                public void run() {

                                                    TipDialog.show("欢迎你，" + getUser(), WaitDialog.TYPE.SUCCESS);


                                                }
                                            };

                                            Timer timer = new Timer();
                                            timer.schedule(task, 300);

                                            try {
                                                //BottomDialog.build().dismiss();
                                                runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {

                                            Snackbar.make(coordinatorLayout, "用户名或密码错误！", Snackbar.LENGTH_SHORT).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE).show();
//                                            username.setTextColor(0x66ff0000);
                                            password.setText("");
                                        }

                                    }
                                });

                            }

                        });
                break;
        }

    }

    public Boolean signup(String Susername, String Spassword) throws Exception {
        String url = "http://10.1.7.5:8080/web_war_exploded/reg?name=" + Susername + "&password=" + Spassword;

        String resp = Net.get(url);
        return resp.equals("success!");

    }

    public Boolean signin(String Susername, String Spassword) throws Exception {
        String url = "http://10.1.7.5:8080/web_war_exploded/login?name=" + Susername + "&password=" + Spassword;

        String resp = Net.get(url);
        return resp.equals(Susername);

    }


    public String getUser() {
        return Susername;
    }

    public void setUser(String susername) {
        Susername = susername;
    }

    public String getPassword() {
        return Spassword;
    }

    public void setPassword(String spassword) {
        Spassword = spassword;
    }


}


