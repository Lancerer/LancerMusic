package com.example.lancer.lancermusic.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lancer.lancermusic.MainActivity;
import com.example.lancer.lancermusic.R;
import com.example.lancer.lancermusic.bean.MusicBean;
import com.example.lancer.lancermusic.fragment.singFragment;
import com.example.lancer.lancermusic.service.PlayerService;
import com.example.lancer.lancermusic.util.Constant;
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
    private List<MusicBean> lists = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private boolean mIsPlaying;
    private int mPosition;
    private MusicBean musicBean;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.STATUS_PREPARED) {
                mPosition = msg.arg1;
                mIsPlaying = (boolean) msg.obj;
                switchSongUI(mPosition);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();
        initData();
    }

    private void initData() {
        MyMusicUtil myMusicUtil = new MyMusicUtil();
        lists = myMusicUtil.getMp3Infos(this);
        startService();
        ivPlayBack.setOnClickListener(this);
        ivPlayMenu.setOnClickListener(this);
        ivPlayNext.setOnClickListener(this);
        ivPlayPlay.setOnClickListener(this);
        ivPlayUp.setOnClickListener(this);
        switchSongUI(mPosition);
    }

    private void startService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayerService.class);
        intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) lists);
        intent.putExtra("messager", new Messenger(handler));
        startService(intent);
    }

    private void switchSongUI(int position) {
        if (lists.size() > 0 && position < lists.size()) {
            //获得播放数据
            musicBean = lists.get(position);
            //设置歌手名，歌曲名
            tvPlaySinger.setText(musicBean.getArtist());
            tvPlaySing.setText(musicBean.getTitle());
        } else {
            Toast.makeText(this, "没有歌曲，下载歌曲后再来使用 ", Toast.LENGTH_SHORT).show();
        }
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
                sendBroadcast(Constant.ACTION_NEXT);
                ivPlayPlay.setImageResource(R.drawable.play_pause);
                break;
            case R.id.iv_play_play:
                if (!mIsPlaying) {
                    ivPlayPlay.setImageResource(R.drawable.play_pause);
                    mIsPlaying = true;
                    sendBroadcast(Constant.STATUS_PLAY);
                } else {
                    ivPlayPlay.setImageResource(R.drawable.play_btn_play_pressed);
                    mIsPlaying = false;
                    sendBroadcast(Constant.STATUS_PAUSE);
                }
                break;
            case R.id.iv_play_up:
                sendBroadcast(Constant.ACTION_UP);
                break;
            default:
                break;
        }
    }

    private void sendBroadcast(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        sendBroadcast(intent);
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
/*    private void up() {
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
    }*/