package com.example.lancer.lancermusic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.lancer.lancermusic.activity.AboutActivity;
import com.example.lancer.lancermusic.activity.PlayBarBaseActivity;
import com.example.lancer.lancermusic.activity.ThemeActivity;
import com.example.lancer.lancermusic.activity.localmusicActivity;
import com.example.lancer.lancermusic.activity.lovemusicActivity;
import com.example.lancer.lancermusic.activity.recentlymusicActivity;
import com.example.lancer.lancermusic.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends PlayBarBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private LinearLayout fixListLl;
    private LinearLayout llLocalMusic;
    private LinearLayout llRecentlyMusic;
    private LinearLayout llLoveMusic;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //设置Toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        //下面的代码主要通过actionbardrawertoggle将toolbar与drawablelayout关联起来
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //设置navigationview的menu监听
        navigationView.setNavigationItemSelectedListener(this);
        initData();
    }

    private void initData() {
        llLocalMusic.setOnClickListener(this);
        llLoveMusic.setOnClickListener(this);
        llRecentlyMusic.setOnClickListener(this);
        loadPic();
    }

    private void loadPic() {
        HttpUtil.sendOkHttpRequest(HttpUtil.requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String bingPic = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(MainActivity.this).load(bingPic).into(imageView);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_local_music:
                startActivity(new Intent(MainActivity.this, localmusicActivity.class));
                break;
            case R.id.ll_love_music:
                startActivity(new Intent(MainActivity.this, lovemusicActivity.class));
                break;
            case R.id.ll_recently_music:
                startActivity(new Intent(MainActivity.this, recentlymusicActivity.class));
                break;
        }
    }

    //监听back键
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //navigationView抽屉侧边布局点击事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this, ThemeActivity.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (id == R.id.nav_manage) {
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void initView() {
        toolbar = findViewById(R.id.toolbar_mainAvtivity);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        fixListLl = findViewById(R.id.fix_list_ll);
        llLocalMusic = findViewById(R.id.ll_local_music);
        llRecentlyMusic = findViewById(R.id.ll_recently_music);
        llLoveMusic = findViewById(R.id.ll_love_music);
        //抽屉布局找到头布局控件,添加头布局
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        imageView = headerView.findViewById(R.id.imageView);
    }


}
