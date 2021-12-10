package com.u21.a0903_onlinemusic;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.List;

public class GuideActivity extends Activity {

    private LinearLayout layout;
    private List<View> list;
    private int[] layouts={
            R.layout.activity_main,
            R.layout.activity_musiclist,
            R.layout.activity_musicplay
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout.findViewById(R.id.linear_left);
        LayoutInflater inflater =getLayoutInflater();
        for (int j : layouts) {
            View view = inflater.inflate(j, null);
            list.add(view);
        }
        for (int i = 0; i < layouts.length; i++) {
            ImageView dot=new ImageView(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,4,4,4);
            dot.setLayoutParams(params);
            if (i==1){
                dot.setImageResource(R.drawable.shibai);
            }else {
                dot.setImageResource(R.drawable.zanting);
            }
            layout.addView(dot);

        }

    }
}
