package com.u21.a0903_onlinemusic.viewholder;

import android.view.View;
import android.widget.TextView;

import com.u21.a0903_onlinemusic.R;
import com.u21.a0903_onlinemusic.entity.Music;
import com.u21.a0903_onlinemusic.entity.Word;

import java.util.List;

public class WordViewHolder {
    private TextView word_h;
    private TextView explain_h;

    public WordViewHolder(View view){
        word_h=view.findViewById(R.id.itemword);
        explain_h=view.findViewById(R.id.itemexp);
    }

    public void setData(List<Word> list, int pos){
        Word word=list.get(pos);
        //logo.setImageBitmap(music.getLogo());
        if (word.getWord()!=null) {
            word_h.setText(word.getWord());
        }else {
            word_h.setText("空");
        }
        if (explain_h!=null){
            explain_h.setText(word.getExplain());
        }

    }
}
