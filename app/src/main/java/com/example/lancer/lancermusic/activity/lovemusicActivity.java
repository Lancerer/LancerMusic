package com.example.lancer.lancermusic.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lancer.lancermusic.R;

public class lovemusicActivity extends BaseActivity {

    private Toolbar toolbarLoveactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lovemusic);
        initView();
        initData();
    }

    public void initData() {
        setSupportActionBar(toolbarLoveactivity);
        toolbarLoveactivity.setTitle("我的喜愛");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_lovemusic;
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
        toolbarLoveactivity = findViewById(R.id.toolbar_loveactivity);
    }
}
