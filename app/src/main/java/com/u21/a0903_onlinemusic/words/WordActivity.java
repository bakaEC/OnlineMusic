package com.u21.a0903_onlinemusic.words;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.u21.a0903_onlinemusic.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordActivity extends Activity {


	private TextView wordMain, wordExp;
	private List<String> list = new ArrayList<>();
	private RelativeLayout relativeLayout;


	String word;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		word = intent.getStringExtra("Word");

		setContentView(R.layout.activity_randwords);
		relativeLayout = findViewById(R.id.randwordsInterface);
		wordMain = findViewById(R.id.wordmain);
		wordExp = findViewById(R.id.wordexp);


		int index = word.indexOf(" ");


		name = word.substring(0, index);
		String explain = word.substring(index);

		wordMain.setText(name);
		wordExp.setText(explain);
		relativeLayout.setOnClickListener(v -> {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});


	}


}
