package com.example.lancer.lancermusic.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.lancer.lancermusic.R;
import com.example.lancer.lancermusic.bean.MusicBean;
import com.example.lancer.lancermusic.fragment.singFragment;
import com.example.lancer.lancermusic.util.MyMusicUtil;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class playActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivPlayBack;
    private TextView tvPlaySing;
    private TextView tvPlaySinger;
    private TextView tvCurrentTime;
    private SeekBar seekbarPlay;
    private TextView tvTotalTime;
    private ImageView ivPlayPlay;
    private ImageView ivPlayUp;
    private ImageView ivPlayNext;
    private ImageView ivPlayMenu;
    private ImageView ivMode;
    private int position;
    private List<MusicBean> lists;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();
        initData();
    }

    private void initData() {
        lists = new ArrayList<>();
        MyMusicUtil myMusicUtil = new MyMusicUtil();
        lists = myMusicUtil.getMusicInfo(this);

        ivPlayBack.setOnClickListener(this);
        ivPlayMenu.setOnClickListener(this);
        ivPlayNext.setOnClickListener(this);
        ivPlayPlay.setOnClickListener(this);
        ivPlayUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play_back:
                finish();
                break;
            case R.id.iv_play_menu:
                break;
            case R.id.iv_play_next:
                next();
                break;
            case R.id.iv_play_play:
                play();
                break;
            case R.id.iv_play_up:
                up();
                break;
            default:
                break;
        }
    }

    private void up() {
        changeMusic(--position);
        ivPlayPlay.setImageResource(R.drawable.play_pause);
        tvPlaySing.setText("" + lists.get(position).getTitle());
        tvPlaySinger.setText("" + lists.get(position).getArtist());
    }

    private void next() {
        changeMusic(++position);
        ivPlayPlay.setImageResource(R.drawable.play_pause);
        tvPlaySing.setText("" + lists.get(position).getTitle());
        tvPlaySinger.setText("" + lists.get(position).getArtist());
    }

    private void play() {
        // 首次点击播放按钮，默认播放第0首
        if (mediaPlayer == null) {
            changeMusic(0);
            String title = lists.get(position + 1).getTitle();
            tvPlaySing.setText("" + lists.get(position).getTitle());
            tvPlaySinger.setText("" + lists.get(position).getArtist());
            ivPlayPlay.setImageResource(R.drawable.play_pause);
        } else {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                ivPlayPlay.setImageResource(R.drawable.play_btn_play_pressed);
            } else {
                mediaPlayer.start();
                ivPlayPlay.setImageResource(R.drawable.play_pause);
            }
        }
    }

    private void changeMusic(int mposition) {
        if (position < 0) {
            position = mposition = lists.size() - 1;
        } else if (mposition > lists.size() - 1) {
            position = mposition = 0;
        }

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        try {
            // 切歌之前先重置，释放掉之前的资源
            mediaPlayer.reset();
            // 设置播放源
            mediaPlayer.setDataSource(lists.get(mposition).getUrl());
            // 开始播放前的准备工作，加载多媒体资源，获取相关信息
            mediaPlayer.prepare();

            // 开始播放
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void getListPosition(singFragment.MessageEvent event) {
        position = event.position;
        mediaPlayer = event.mediaPlayer;
    }

    private void initView() {
        ivPlayBack = findViewById(R.id.iv_play_back);
        tvPlaySing = findViewById(R.id.tv_play_sing);
        tvPlaySinger = findViewById(R.id.tv_play_singer);
        tvCurrentTime = findViewById(R.id.tv_current_time);
        seekbarPlay = findViewById(R.id.seekbar_play);
        tvTotalTime = findViewById(R.id.tv_total_time);
        ivPlayPlay = findViewById(R.id.iv_play_play);
        ivPlayUp = findViewById(R.id.iv_play_up);
        ivPlayNext = findViewById(R.id.iv_play_next);
        ivPlayMenu = findViewById(R.id.iv_play_menu);
        ivMode = findViewById(R.id.iv_mode);
    }


}
