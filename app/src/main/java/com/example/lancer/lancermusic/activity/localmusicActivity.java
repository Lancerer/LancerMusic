package com.example.lancer.lancermusic.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.lancer.lancermusic.R;
import com.example.lancer.lancermusic.fragment.albumFragment;
import com.example.lancer.lancermusic.fragment.fileFragment;
import com.example.lancer.lancermusic.fragment.singFragment;
import com.example.lancer.lancermusic.fragment.singerFragment;
import com.example.lancer.lancermusic.view.MyViewPager;

import java.util.ArrayList;

public class localmusicActivity extends PlayBarBaseActivity implements TabLayout.OnTabSelectedListener {
    private Toolbar toolbarLocalactivity;
    private TabLayout tabLocalactivity;
    private MyViewPager myviewpager;
    private FrameLayout fragmentPlaybar;
    private String[] title = {"单曲", "歌手", "专辑", "文件夹"};
    private ArrayList<Fragment> fragments;
    private com.example.lancer.lancermusic.fragment.singFragment singFragment;
    private com.example.lancer.lancermusic.fragment.singerFragment singerFragment;
    private com.example.lancer.lancermusic.fragment.albumFragment albumFragment;
    private com.example.lancer.lancermusic.fragment.fileFragment fileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localmusic);
        initView();
        initData();
    }

    private void initData() {
        setSupportActionBar(toolbarLocalactivity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        fragments = new ArrayList<>();
        initFragment();
        for (String tab : title) {
            tabLocalactivity.addTab(tabLocalactivity.newTab().setText(tab));
        }
        tabLocalactivity.addOnTabSelectedListener(this);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        myviewpager.setAdapter(adapter);
    }

    private void initFragment() {
        if (singFragment == null) {
            singFragment = new singFragment();
            fragments.add(singFragment);
        }
        if (singerFragment == null) {
            singerFragment = new singerFragment();
            fragments.add(singerFragment);
        }
        if (albumFragment == null) {
            albumFragment = new albumFragment();
            fragments.add(albumFragment);
        }
        if (fileFragment == null) {
            fileFragment = new fileFragment();
            fragments.add(fileFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.local_music_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.scan_local_menu:
                startActivity(new Intent(localmusicActivity.this,scanActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        myviewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

    private void initView() {
        toolbarLocalactivity = findViewById(R.id.toolbar_localactivity);
        tabLocalactivity = findViewById(R.id.tab_localactivity);
        myviewpager = findViewById(R.id.myviewpager);
        fragmentPlaybar = findViewById(R.id.fragment_playbar);
    }
}
