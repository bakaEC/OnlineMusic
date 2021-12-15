package com.u21.a0903_onlinemusic;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import com.kongzue.dialogx.dialogs.BottomDialog;
import com.kongzue.dialogx.dialogs.TipDialog;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.u21.a0903_onlinemusic.databinding.ActivityMainBinding;
import com.u21.a0903_onlinemusic.net.Net;
import com.u21.a0903_onlinemusic.user.NetworkSettings;
import com.u21.a0903_onlinemusic.user.NetworkThread;
import com.u21.a0903_onlinemusic.user.ResponseBody;

import java.io.IOException;
import java.util.concurrent.FutureTask;
import java.util.zip.Inflater;


public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private final ObjectMapper mapper = new ObjectMapper();
    private Button login;
    private Button register;
    String Susername="";
    String Spassword="";

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        register=findViewById(R.id.register);
        login=findViewById(R.id.login);
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
                                TextView textView = v.findViewById(R.id.reg_text);
                                EditText username= v.findViewById(R.id.reg_name);
                                EditText password = v.findViewById(R.id.reg_password);

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Susername = username.getText().toString();
                                        Spassword = password.getText().toString();
                                        Log.e("reg","点击注册");
                                        textView.setText(Susername);
                                        try {
                                            //Net.get("http://10.1.7.5:8080/web_war_exploded/reg?username="+Susername+"&password="+Spassword);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        TipDialog.show("Success!", WaitDialog.TYPE.SUCCESS);
                                        Runtime runtime = Runtime.getRuntime();
                                        try {
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

                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Susername = username.getText().toString();
                                        Spassword = password.getText().toString();
                                        Log.e("log","登录");
                                        textView.setText(Susername);
                                        signUp(Susername,Spassword);
                                        TipDialog.show("Success!", WaitDialog.TYPE.SUCCESS);
                                        Runtime runtime = Runtime.getRuntime();
                                        try {
                                            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }

                        });
                break;
                }

        }


    public Boolean signIn(String name,String password){

        FutureTask<String> signInTask = new FutureTask<>(new NetworkThread(name,password, NetworkSettings.SIGN_IN));
        Thread thread = new Thread(signInTask);
        thread.start();
        try{
            //get获取线程返回值，通过ObjectMapper反序列化为ResponseBody
            ResponseBody body = mapper.readValue(signInTask.get(),ResponseBody.class);
            //根据返回码确定提示信息
            if(body.getCode() == 200){
                Log.e("reg","登录成功！");
                return true;
            }else {
                Log.e("reg","登录失败！");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public String signUp(String name,String password){

        FutureTask<String> signUpTask = new FutureTask<>(new NetworkThread(name,password,NetworkSettings.SIGN_UP));
        Thread thread = new Thread(signUpTask);
        thread.start();
        try{
            ResponseBody body = mapper.readValue(signUpTask.get(),ResponseBody.class);
            if(body.getCode() == 200){
                Log.e("reg","登录成功！");
                return body.toString();
            }else {
                Log.e("reg","登录失败！");
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}