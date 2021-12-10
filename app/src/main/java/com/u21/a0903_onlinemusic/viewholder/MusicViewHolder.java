package com.u21.a0903_onlinemusic.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.*;

import com.u21.a0903_onlinemusic.R;
import com.u21.a0903_onlinemusic.entity.Music;
import com.u21.a0903_onlinemusic.view.CircleImageView;

public class MusicViewHolder{
    private CircleImageView logo;
    private TextView name;


    public MusicViewHolder(View view){
        logo=view.findViewById(R.id.musicLogo);
        name= view.findViewById(R.id.musicName);

    }

    public void setData(List<Music> list,int pos){
        Music music= list.get(pos);
        //logo.setImageBitmap(music.getLogo());
        if (music.getName()!=null) {
            name.setText(music.getName());
        }else {
            name.setText("空");
        }
        if (music.getLogo()!=null){
            logo.setImageBitmap(music.getLogo());
        }

    }


}
