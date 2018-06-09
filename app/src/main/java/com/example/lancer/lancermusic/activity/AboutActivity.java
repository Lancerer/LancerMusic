package com.example.lancer.lancermusic.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lancer.lancermusic.R;

public class AboutActivity extends BaseActivity {


    private Toolbar aboutToolbar;
    private TextView aboutVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        initData();

    }

    public void initData() {
        aboutToolbar = (Toolbar) findViewById(R.id.about_toolbar);
        setSupportActionBar(aboutToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public int initLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void initListener() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    public void initView() {
        aboutToolbar = findViewById(R.id.about_toolbar);
        aboutVersion = findViewById(R.id.about_version);
    }
}
