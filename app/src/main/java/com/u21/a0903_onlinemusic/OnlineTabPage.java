package com.u21.a0903_onlinemusic;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;


import java.util.ArrayList;
import java.util.List;



public class OnlineTabPage extends ActivityGroup implements TabHost.OnTabChangeListener {
    String []strTabs={
            "首页",
            "热点",
            "聆听",
            "我的"
    };

    int[] iconSel={
            R.mipmap.xihuan,
            R.mipmap.shoucang_2,
            R.mipmap.zan,
            R.mipmap.salefill
    };
    int[] iconUnsel={
            R.mipmap.xihuan_1,
            R.mipmap.shoucang_1,
            R.mipmap.zan_1,
            R.mipmap.sale
    };

    private int index=0;
    //标签页对象
    private TabHost tabHost;
    //标签按钮的界面收集
    List<View> listViews;//视图列表

    Context context = null;

    LocalActivityManager manager = null;


    private ViewPager pager = null;
    private List<LinearLayout> tablinears=new ArrayList<LinearLayout>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_online);



        //标签栏------------
        for (int i = 0; i < iconSel.length; i++) {
            LinearLayout linearLayout=(LinearLayout)getLayoutInflater().inflate(R.layout.tab_btn,null);
            ImageView icon = (ImageView)linearLayout.findViewById(R.id.imageView1);
            TextView title = (TextView)linearLayout.findViewById(R.id.textView1);
            //当前显示窗口对应的文字图标
            if(i==index){
                icon.setImageResource(iconSel[i]);
                title.setTextColor(0xff13227a);
            }else {
                icon.setImageResource(iconUnsel[i]);
                title.setTextColor(0xff0f0f0f);
            }
            title.setText(strTabs[i]);
            tablinears.add(linearLayout);

        }
        //窗口显示----------
        tabHost=(TabHost)findViewById(R.id.tabhost);
        LocalActivityManager activityManager = getLocalActivityManager();
        tabHost.setup(activityManager);
        //通过TabHost对象创建tabspec，blob代表该tabspec标签字符串
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("0");
        //设置标签页单个标签界面内容
        tabSpec.setIndicator(tablinears.get(0));
        //设置标签页分页Activity
        tabSpec.setContent(new Intent(this,MainActivity.class));
        //添加分页内容
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("1");
        //设置标签页单个标签界面内容
        tabSpec.setIndicator(tablinears.get(1));
        //设置标签页分页Activity
        tabSpec.setContent(new Intent(this,MyWebActivity.class));
        //添加分页内容
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("2");
        //设置标签页单个标签界面内容
        tabSpec.setIndicator(tablinears.get(2));
        //设置标签页分页Activity
        tabSpec.setContent(new Intent(this,MusicListActivity.class));
        //添加分页内容
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec("3");
        //设置标签页单个标签界面内容
        tabSpec.setIndicator(tablinears.get(3));
        //设置标签页分页Activity
        tabSpec.setContent(new Intent(this, UserInfoActivity.class));
        //添加分页内容
        tabHost.addTab(tabSpec);

        tabHost.setOnTabChangedListener(this);



    }

    @Override
    public void onTabChanged(String tabId) {
        try{
            index=Integer.parseInt(tabId);
        }catch (Exception e){
            e.printStackTrace();
        }
        for (int i = 0; i < iconSel.length; i++) {
            LinearLayout linearLayout=tablinears.get(i);
            ImageView icon = (ImageView)linearLayout.findViewById(R.id.imageView1);
            TextView title = (TextView)linearLayout.findViewById(R.id.textView1);
            //当前显示窗口对应的文字图标
            if(i==index){
                icon.setImageResource(iconSel[i]);
                title.setTextColor(0xff13227a);
            }else {
                icon.setImageResource(iconUnsel[i]);
                title.setTextColor(0xff0f0f0f);
            }


        }
    }
}


//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.tab_online);
//        manager = new LocalActivityManager(this, false);
//        manager.dispatchCreate(savedInstanceState);
//        /*在一个Activity的一部分中显示其他Activity”要用到LocalActivityManagerity
//        作用体现在manager获取View：manager.startActivity(String, Intent).getDecorView()*/
//
//        tabHost = (TabHost) findViewById(R.id.tabhost);
//        tabHost.setup();
//        tabHost.setup(manager);//实例化THost
//
//        context = OnlineTabPage.this;
//
//        pager = (ViewPager) findViewById(R.id.view_pager);//ViewPager
//
//
//        //加入4个子Activity
//        Intent i1 = new Intent(context, MainActivity.class);
//        Intent i2 = new Intent(context, MyWebActivity.class);
//        Intent i3 = new Intent(context, MusicListActivity.class);
//        Intent i4 = new Intent(context, UserInfoActivity.class);
//
//        listViews = new ArrayList<View>();  //实例化listViews
//        listViews.add(manager.startActivity("T1", i1).getDecorView());
//        listViews.add(manager.startActivity("T2", i1).getDecorView());
//        listViews.add(manager.startActivity("T3", i1).getDecorView());
//        listViews.add(manager.startActivity("T4", i4).getDecorView());
//
//
//        LinearLayout tabIndicator1 = (LinearLayout) getLayoutInflater().inflate(R.layout.tab_btn, null);
//        ImageView icon1 = (ImageView) tabIndicator1.findViewById(R.id.imageView1);
//        icon1.setImageResource(iconSel[0]);
//        TextView title1 = (TextView) tabIndicator1.findViewById(R.id.textView1);
//        title1.setText(strTabs[0]);
//
//        LinearLayout tabIndicator2 = (LinearLayout) getLayoutInflater().inflate(R.layout.tab_btn, null);
//        ImageView icon2 = (ImageView) tabIndicator2.findViewById(R.id.imageView1);
//        icon2.setImageResource(iconSel[1]);
//        TextView title2 = (TextView) tabIndicator2.findViewById(R.id.textView1);
//        title2.setText(strTabs[1]);
//
//        LinearLayout tabIndicator3 = (LinearLayout) getLayoutInflater().inflate(R.layout.tab_btn, null);
//        ImageView icon3 = (ImageView) tabIndicator3.findViewById(R.id.imageView1);
//        icon3.setImageResource(iconSel[2]);
//        TextView title3 = (TextView) tabIndicator3.findViewById(R.id.textView1);
//        title3.setText(strTabs[2]);
//
//        LinearLayout tabIndicator4 = (LinearLayout) getLayoutInflater().inflate(R.layout.tab_btn, null);
//        ImageView icon4 = (ImageView) tabIndicator4.findViewById(R.id.imageView1);
//        icon4.setImageResource(iconSel[3]);
//        TextView title4 = (TextView) tabIndicator4.findViewById(R.id.textView1);
//        title4.setText(strTabs[3]);
//
//        tabHost.addTab(tabHost.newTabSpec("1").setIndicator(tabIndicator1).setContent(i1));
//        //TabSpec的名字A，B，C才是各个tab的Id
//        tabHost.addTab(tabHost.newTabSpec("2").setIndicator(tabIndicator2).setContent(i2));
//        tabHost.addTab(tabHost.newTabSpec("3").setIndicator(tabIndicator3).setContent(i3));
//        tabHost.addTab(tabHost.newTabSpec("4").setIndicator(tabIndicator4).setContent(i4));
//
//        tabHost.setOnTabChangedListener(this);
//
//        pager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//
//
//        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                tabHost.setCurrentTab(position);//在Viewpager改变tabhost
//            }
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//            }
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//            }
//        });
//
//    }
//
//    @Override
//    public void onTabChanged(String tabId) {
//
//        try {
//            index = Integer.parseInt(tabId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < iconSel.length; i++) {
//            LinearLayout linearLayout = tablinears.get(i);
//            ImageView icon = (ImageView) linearLayout.findViewById(R.id.imageView1);
//            TextView title = (TextView) linearLayout.findViewById(R.id.textView1);
//            //当前显示窗口对应的文字图标
//            if (i == index) {
//                icon.setImageResource(iconSel[i]);
//                title.setTextColor(0xff13227a);
//            } else {
//                icon.setImageResource(iconUnsel[i]);
//                title.setTextColor(0xff0f0f0f);
//            }
//
//
//        }
//    }
//}
//
