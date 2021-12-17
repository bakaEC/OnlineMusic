package com.u21.a0903_onlinemusic.words;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.u21.a0903_onlinemusic.MainActivity;
import com.u21.a0903_onlinemusic.R;
import com.u21.a0903_onlinemusic.WordLearningActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordActivity extends Activity {


	private TextView wordMain, wordExp;
	private List<String> list = new ArrayList<String>();
	private RelativeLayout relativeLayout;


	String word;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//实例化控件
		Intent intent = getIntent();
		word = intent.getStringExtra("Word");

		setContentView(R.layout.activity_randwords);
		relativeLayout = (RelativeLayout) findViewById(R.id.randwordsInterface);
		wordMain = (TextView) findViewById(R.id.wordmain);
		wordExp = (TextView) findViewById(R.id.wordexp);


		int index = word.indexOf(" ");


		name = word.substring(0, index);
		String explain = word.substring(index);

		wordMain.setText(name);
		wordExp.setText(explain);
		relativeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});


	}


}
