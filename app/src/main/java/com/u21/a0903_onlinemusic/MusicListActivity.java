package com.u21.a0903_onlinemusic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.u21.a0903_onlinemusic.entity.Music;
import com.u21.a0903_onlinemusic.net.Net;
import com.u21.a0903_onlinemusic.viewholder.MusicViewHolder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends Activity implements Runnable, AdapterView.OnItemClickListener {
    private List<Music> dataSource = new ArrayList<Music>();
    private ProgressDialog pd;
    private String response;
    private Boolean isError;
    private MusicAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiclist);
        listView=findViewById(R.id.list1);
        pd=ProgressDialog.show(this,null,"LOADING...");
//        initData();
        listView.setOnItemClickListener(this);
        new Thread(this).start();


    }

    private void initData(){
//        Bitmap pic =BitmapFactory.decodeResource(getResources(), R.mipmap.dujiaoshou1);
//        Music music = new Music(pic,"想去海边");
//        dataSource.add(music);
//
//        pic = BitmapFactory.decodeResource(getResources(),R.mipmap.dujiaoshou3);
//        music = new Music(pic,"重力-gravity");
//        dataSource.add(music);

    }

    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
        Music music = dataSource.get(position);
        Intent intent = new Intent(this,MusicPlayActivity.class);
        intent.putExtra("name",music.getName());

        //Toast.makeText(MusicListActivity.this,"data:"+dataSource.get(position).getName(),Toast.LENGTH_LONG).show();
        startActivity(intent);
    }


    class MusicAdapter extends BaseAdapter {

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
            MusicViewHolder vh =null;
            if(view == null){
                view=getLayoutInflater().inflate(R.layout.lrc_item,null);
                vh=new MusicViewHolder(view);
                vh.setData(dataSource,position);
                view.setTag(vh);
            }else{
                vh=(MusicViewHolder) view.getTag();
                vh.setData(dataSource,position);
            }
            return view;

        }
    }
    @Override
    public void run() {
        isError = false;
        try {
            response = Net.get("http://10.1.7.5:8080/web_war_exploded/list");
            JSONArray array = new JSONArray(response);
            for (int i = 0; i < array.length(); i++) {
                String lrcName=array.getString(i);
                Music music = new Music(lrcName);
                dataSource.add(music);
            }
        } catch (Exception e) {
            response = e.toString();
            isError=true;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                pd.dismiss();
                //Toast.makeText(MusicListActivity.this,"size:"+dataSource.size(),Toast.LENGTH_LONG).show();
                if (isError){
                    Toast.makeText(MusicListActivity.this,response,Toast.LENGTH_LONG).show();
                    return;
                }
                adapter=new MusicAdapter();
                listView.setAdapter(adapter);


            }
        });
    }

}
