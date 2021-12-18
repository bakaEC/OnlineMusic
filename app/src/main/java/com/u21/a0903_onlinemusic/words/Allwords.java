package com.u21.a0903_onlinemusic.words;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.u21.a0903_onlinemusic.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Allwords extends Activity implements OnItemClickListener {


	private ListView lv;
	private ArrayAdapter<String> adpter;
	private List<String> list = new ArrayList<>();
	private FloatingActionButton fab;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wordmain);

		Intent intent = getIntent();
		String Cap = intent.getStringExtra("Key");
//		CoordinatorLayout coordinatorLayout = findViewById(R.id.snackbar_container);
//		Snackbar.make(coordinatorLayout, str, Snackbar.LENGTH_SHORT);

		lv = findViewById(R.id.capList);
		fab = findViewById(R.id.fab);

		fab.setOnClickListener(view -> {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});


		try {

			InputStream is = getResources().getAssets().open(Cap + ".txt");

			InputStreamReader isr = new InputStreamReader(is);

			BufferedReader br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);

			}

			br.close();
			is.close();
			isr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		adpter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
		lv.setAdapter(adpter);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

		Intent intent = new Intent(this, WordActivity.class);
		intent.putExtra("Word", list.get(pos));
		startActivity(intent);
	}

}

	
