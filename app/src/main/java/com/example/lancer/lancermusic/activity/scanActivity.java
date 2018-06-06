package com.example.lancer.lancermusic.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lancer.lancermusic.R;

public class scanActivity extends AppCompatActivity {

    private Toolbar toolbarScan;
    private TextView scanPath;
    private TextView tvScanCount;
    private Button btScan;
    private boolean isscaning = false;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        initView();
        setSupportActionBar(toolbarScan);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initData();
    }

    @SuppressLint("HandlerLeak")
    private void initData() {
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isscaning) {
                    tvScanCount.setVisibility(View.VISIBLE);
                    isscaning = true;
                    startscanMusic();
                    //todo 自定义扫描动画view start
                    btScan.setText("停止扫描");

                } else {
                    tvScanCount.setVisibility(View.GONE);
                    isscaning = false;
                    //todo 自定义扫描动画view stop
                    btScan.setText("开始扫描");
                }
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {

                }
            }
        };
    }

    //扫描本地所有音乐
    private void startscanMusic() {
        new Thread(){
            @Override
            public void run() {
                super.run();
            }
        }.start();
    }

    //扫描完成方法
    private void scanComplete() {
        btScan.setText("完成");
        isscaning = false;
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isscaning) {
                    scanActivity.this.finish();
                }
            }
        });
        //todo 扫描动画2停止
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return true;
    }

    private void initView() {
        toolbarScan = findViewById(R.id.toolbar_scan);
        scanPath = findViewById(R.id.scan_path);
        tvScanCount = findViewById(R.id.tv_scan_count);
        btScan = findViewById(R.id.bt_scan);
    }
}
