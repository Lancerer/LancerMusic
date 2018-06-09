package com.example.lancer.lancermusic.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lancer.lancermusic.R;

public class recentlymusicActivity extends BaseActivity {

    private Toolbar toolbarRecentlyactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recentlymusic);
        initView();
        initData();
    }
    public void initData() {
        setSupportActionBar(toolbarRecentlyactivity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_recentlymusic;
    }

    @Override
    public void initListener() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    public void initView() {
        toolbarRecentlyactivity = findViewById(R.id.toolbar_recentlyactivity);
    }
}
