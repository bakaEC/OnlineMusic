package com.u21.a0903_onlinemusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.u21.a0903_onlinemusic.words.Allwords;
import com.u21.a0903_onlinemusic.words.Randomwords;
import com.u21.a0903_onlinemusic.words.WordActivity;

public class WordLearningActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView CapList;
    private ArrayAdapter<String> capAdapter;


    String arry[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        CapList = (ListView) findViewById(R.id.listView1);


        capAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arry);
        CapList.setAdapter(capAdapter);

        CapList.setOnItemClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {

        Intent intent = new Intent(WordLearningActivity.this, Allwords.class);
        intent.putExtra("Key", arry[index]);

        startActivity(intent);
    }


}
