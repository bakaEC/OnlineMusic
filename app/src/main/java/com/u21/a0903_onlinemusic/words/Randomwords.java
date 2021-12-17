package com.u21.a0903_onlinemusic.words;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.u21.a0903_onlinemusic.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Randomwords extends Activity implements OnClickListener {


    private TextView wordText, wordExplain;
    private List<String> list = new ArrayList<String>();
    private RelativeLayout wordInterface;
    String word;
    String name;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randwords);

        wordText = (TextView) findViewById(R.id.wordmain);
        wordExplain = (TextView) findViewById(R.id.wordexp);

        wordInterface = (RelativeLayout) findViewById(R.id.randwordsInterface);
        wordInterface.setOnClickListener(this);


        try {

            InputStream inputStream = getResources().getAssets().open("Allwords.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);
			Random random = new Random();
			int ran = random.nextInt(5120);
			String line = null;
			for (int i = 0; (line = reader.readLine()) != null; i++) {
				if (i == ran) {
					word = line;
				}
				list.add(line);
			}
			reader.close();
			inputStream.close();
            streamReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        int index = word.indexOf(" ");
        name = word.substring(0, index);
        String explain = word.substring(index);
        wordText.setText(name);
        wordExplain.setText(explain);
    }


	@Override
	public void onClick(View arg0) {

        Random r = new Random();
        int ran = r.nextInt(5120);
        word = list.get(ran);
        int index = word.indexOf(" ");
        name = word.substring(0, index);
        String explain = word.substring(index);
        wordText.setText(name);
        wordExplain.setText(explain);

    }


}
