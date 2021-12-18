package com.u21.a0903_onlinemusic.words;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.u21.a0903_onlinemusic.R;
import com.u21.a0903_onlinemusic.entity.Word;
import com.u21.a0903_onlinemusic.viewholder.WordViewHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class Allwords extends Activity implements OnItemClickListener {


	private ListView lv;
	private List<Word> dataSource = new ArrayList<>();
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

				int index = line.indexOf("|");
				String name = line.substring(0, index);
				String explain = line.substring(index);

				Word word=new Word(name,explain);
				dataSource.add(word);
				list.add(line);

			}

			br.close();
			is.close();
			isr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		WordAdapter adpter = new WordAdapter();
		lv.setOnItemClickListener(this);
		lv.setAdapter(adpter);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		//Toast.makeText(this,"你点到我啦",Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, WordActivity.class);
		intent.putExtra("Word",list.get(pos));
		startActivity(intent);
	}

	class WordAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataSource.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			WordViewHolder vh =null;
			if(view == null){
				view=getLayoutInflater().inflate(R.layout.word_item,null);
				vh=new WordViewHolder(view);
				vh.setData(dataSource,position);
				view.setTag(vh);
			}else{
				vh=(WordViewHolder) view.getTag();
				vh.setData(dataSource,position);
			}
			return view;

		}
	}

}

	
