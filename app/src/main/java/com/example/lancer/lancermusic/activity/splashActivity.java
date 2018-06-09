package com.example.lancer.lancermusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lancer.lancermusic.MainActivity;
import com.example.lancer.lancermusic.R;
import com.example.lancer.lancermusic.util.HttpUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class splashActivity extends AppCompatActivity {

    private LinearLayout ll;
    private ImageView ivSplash;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
    }

    private void initData() {
        //   Glide.with(splashActivity.this).load(R.drawable.spl).into(ivSplash);
        loadPic();
        toMain();
    }

    private void toMain() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(splashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(timerTask, 3000);

    }

    private void loadPic() {
        HttpUtil.sendOkHttpRequest(HttpUtil.requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String bingPic = response.body().string();
                    //  MyMusicUtil.setBingShared(bingPic);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(splashActivity.this).load(bingPic).into(ivSplash);
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

    private void initView() {
        tv = findViewById(R.id.tv);
        ll = findViewById(R.id.ll);
        ivSplash = findViewById(R.id.iv_splash);
    }
}
