package com.u21.a0903_onlinemusic.viewholder;

import android.view.View;
import android.widget.TextView;

import com.u21.a0903_onlinemusic.R;

public class WordViewHolder {
    private TextView word;
    private TextView explain;

    public WordViewHolder(View view){
        word=view.findViewById(R.id.itemword);
        explain=view.findViewById(R.id.itemexp);
    }
}
