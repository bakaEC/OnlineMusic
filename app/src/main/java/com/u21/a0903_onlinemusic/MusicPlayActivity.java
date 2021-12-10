package com.u21.a0903_onlinemusic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.u21.a0903_onlinemusic.entity.Lrc;
import com.u21.a0903_onlinemusic.net.Net;
import com.u21.a0903_onlinemusic.view.LRCTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, Runnable {

    private MediaPlayer mediaPlayer;
    private TextView playname;
    private ImageView playBtn;
    private ImageView pauseBtn;
    private SeekBar bar;
    private TextView time;
    private String strMusicTime;
    private TextView lrc1;
    private LRCTextView lrc2;
    private TextView lrc3;

    private ImageView logo;

    private String musicName;

    private int musicTime;
    private int playTime;

    //进程对话框
    private ProgressDialog progressDialog;
    private String response;
    private Lrc lrc;
    private Boolean isError=false;
    private int index=0;
    private float percent;


    private Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            playTime = mediaPlayer.getCurrentPosition();
            musicTime = mediaPlayer.getDuration();
            if (musicTime > 0) {
                bar.setProgress((int) bar.getMax() * playTime / musicTime);
                //time.setText(convertMs(playTime)+"/"+convertMs(sumTime));

            }
        }
    };
    Boolean isPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplay);
        playname=findViewById(R.id.playname);
        playBtn = (ImageView) findViewById(R.id.playBtn);
        //pauseBtn = (ImageView) findViewById(R.id.b2);
        bar = (SeekBar) findViewById(R.id.bar);
        time = (TextView) findViewById(R.id.time);
        lrc1 = (TextView) findViewById(R.id.lrc);
        lrc2 = (LRCTextView) findViewById(R.id.lrc2);
        lrc3 = (TextView) findViewById(R.id.lrc3);
        logo=(ImageView)findViewById(R.id.cover);

        playBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        bar.setOnSeekBarChangeListener(this);
        progressDialog = ProgressDialog.show(this, null, "Connecting...");
        //开启网络访问子线程
        new Thread(this).start();
        //mediaPlayer = MediaPlayer.create(this, R.raw.sarahbrightman);

        //mediaPlayer.start();
        musicName=getIntent().getStringExtra("name");
        playname.setText(musicName);
        timer = new Timer();

        task = new TimerTask() {

            @Override
            public void run() {

                //多媒体不能为空，而且必须处于播放状态
                //多媒体为空，没有处于播放状态，返回，不要执行任何任务
                if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
                    return;
                }
                //获取当前播放时间（毫秒）
                playTime = mediaPlayer.getCurrentPosition();
                //子线程（自定义线程）不能做UI操作
                //子线程进入主线程  runOnUiThread(线程的借口对象action(Runable接口));
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        //主线程的借口实现

                        //显示播放进度-进度条设置相对应的播放进度
                        bar.setProgress(playTime);//当前播放时刻和总的播放时间的进度条刻度比例，就是播放进度
                        //显示播放时间-文本视图及时跟心文本内容（当前播放时间/歌曲总时间）
                        time.setText(convertMs(playTime) + "/" + strMusicTime);
                        if(lrc!=null&&lrc.getList()!=null&&lrc.getList().size()>0){
                            if(index-1>=0){
                                lrc1.setText(lrc.getList().get(index-1).getLrc());
                            }
                            //显示当前对应的歌词
                            lrc2.setLrc(lrc.getList().get(index).getLrc());
                            if (index+1<lrc.getList().size()){
                                lrc3.setText(lrc.getList().get(index+1).getLrc());
                            }
                            for (int i = index; i < lrc.getList().size(); i++) {
                                int start = lrc.getList().get(i).getStart();//上一句歌词起始时间
                                if(i+1>lrc.getList().size()-1){
                                    break;
                                }
                                int end=lrc.getList().get(i+1).getStart();//下一句歌词起始时间

                                if(playTime>=start&&playTime<end){
                                    //计算百分比
                                    percent=(float) (playTime-start)/(float)(end-start);
                                    lrc2.setPercent(percent);
                                    index=i;
                                    break;
                                }
                            }
                        }
                    }
                });
            }
        };
        //开启计时器
        //计时器对象.schedule(执行什么任务(线程run函数),线程开启多少时间以后执行任务,每间隔多少时间执行任务)
        timer.schedule(task, 500, 200);

        playMusic();

    }

    private void playMusic() {
        //如果多媒体对象不为空，实例化多媒体对象
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();

        }
        //为了实现多媒体同一时间只播放一首音乐，我们必须对多媒体对象做同步处理
        synchronized (mediaPlayer) {
            //实现多媒体对音乐的播放
            try {
                //设置播放音乐源—播放音乐的网络链接
                mediaPlayer.setDataSource("http://10.1.7.5:8080/web_war_exploded/mv/"+musicName+"/"+musicName+".mp3");
                //播放器做播放准备
                mediaPlayer.prepare();
                //获取到音乐播放总时间
                musicTime = mediaPlayer.getDuration();
                //设置进度条的长度(刻度总长)
                bar.setMax(musicTime);
                //音乐启动播放后，播放按钮设置成暂停按钮
                playBtn.setImageResource(R.drawable.zanting);
                //设置起始播放时间
                strMusicTime = convertMs(musicTime);
                //播放音乐
                try {

                    logo.setImageBitmap(Net.bmp("http://10.1.7.5:8080/web_war_exploded/mv/"+musicName+"/"+musicName+".jpg"));
                    Toast.makeText(this,"封面",Toast.LENGTH_LONG);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                isPlay = true;//进入播放状态

            } catch (Exception e) {
                Toast.makeText(MusicPlayActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }


        }

    }

    //将毫秒数转成  分钟:秒数 的字符串格式
    private String convertMs(int msecs) {


        //将毫秒数转成秒数
        int secs = msecs / 1000;
        //将秒数转成分钟+秒数
        int ms = secs / 60;
        secs = secs % 60;//分钟后余下的秒数


        String minuts = "" + ms;//分钟
        if (ms < 10) {
            minuts = "0" + ms;
        }
        String seconds = "" + secs;//秒数
        if (secs < 10) {
            seconds = "0" + secs;
        }

        return minuts + ":" + seconds;
    }


    //关闭activity的时候，关闭多媒体
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mediaPlayer != null) {
//			停止播放，关闭多媒体
            mediaPlayer.stop();
//			释放多媒体
            mediaPlayer.release();

            mediaPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == playBtn) {
            if (mediaPlayer == null) {//多媒体是空的,完全没有资源
                playMusic();
            } else {
                //播放
                if (isPlay) {
                    mediaPlayer.pause();//已经播放后暂停
                    //按钮切换成播放图标
                    playBtn.setImageResource(R.mipmap.bofang);
                    isPlay = false;
                } else {//暂停状态
                    mediaPlayer.start();
                    //按钮切换成暂停图标
                    playBtn.setImageResource(R.drawable.zanting);
                    isPlay = true;
                }
            }


        } else {
            //停止
            if (mediaPlayer != null) {
//			停止播放，关闭多媒体
                mediaPlayer.stop();
//			释放多媒体
                mediaPlayer.release();

                mediaPlayer = null;
            }
        }
    }

    //当进度条进度（用户拖拽进度条）发生变化时，触发该函数
    @Override
    public void onProgressChanged(SeekBar bar, int pos, boolean isUser) {
        // 设置播放时间的起始点

    }

    //用户拖拽进度条时，从哪里开始拖拽（拖拽的起始位置）
    @Override
    public void onStartTrackingTouch(SeekBar bar) {
        // TODO Auto-generated method stub
        //暂停播放
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();

            playBtn.setImageResource(R.mipmap.bofang);
            isPlay = false;

        }
    }

    //用户拖拽进度条时，从哪里结束拖拽（拖拽的结束位置）
    @Override
    public void onStopTrackingTouch(SeekBar bar) {
        // TODO Auto-generated method stub
        //设置播放位置，重新开始播放
        if (mediaPlayer != null) {
            //拖拽以后的播放进度
            int progress = bar.getProgress();
            //设置重新播放的起始位置为用户拖拽进度后的位置
            mediaPlayer.seekTo(progress);
            mediaPlayer.start();

            playBtn.setImageResource(R.drawable.zanting);
            isPlay = true;
        }
    }

    private void parseEasyJson(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isError = false;
        try {
            response = Net.get("http://10.1.7.5:8080/web_war_exploded/lrc?name="+musicName);
            JSONObject object = new JSONObject(response);
            lrc = new Lrc(object);
        } catch (Exception e) {
            response = e.toString();
            isError=true;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                progressDialog.dismiss();

                if (isError){
                    lrc1.setText("error"+response);
                }
                //lrc1.setText(lrc.toString());
//                //开启计时器
//                //计时器对象.schedule(执行什么任务(线程run函数),线程开启多少时间以后执行任务,每间隔多少时间执行任务)
//                timer.schedule(task, 500, 200);
//
//                playMusic();
//                lrc1.setText(lrc.getList().get(0).getLrc());
//                lrc2.setText(lrc.getList().get(1).getLrc());
//                lrc3.setText(lrc.getList().get(2).getLrc());


            }
        });
    }
}