package com.example.lancer.lancermusic.fragment;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lancer.lancermusic.R;
import com.example.lancer.lancermusic.activity.playActivity;
import com.example.lancer.lancermusic.bean.MusicBean;
import com.example.lancer.lancermusic.service.PlayerService;
import com.example.lancer.lancermusic.util.MyMusicUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class playbarFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView ivPlaybarAlbum;
    private TextView tvPlaybarSingName;
    private TextView tvPlaybarSingerName;
    private ImageView ivPlaybarPlay;
    private ImageView ivPlaybarNext;
    private ImageView ivPlaybarMenu;
    private int noePosition;
    private List<MusicBean> nowlist;
    private MediaPlayer mediaPlayer;
    private PopupWindow popupWindow;
    private ListView listView;
    private LinearLayout llplaybar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_playbar, null);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        mediaPlayer = new MediaPlayer();
        listView = new ListView(getContext());
        nowlist = new ArrayList<>();
        MyMusicUtil myMusicUtil = new MyMusicUtil();
        nowlist = myMusicUtil.getMp3Infos(getContext());
        ivPlaybarNext.setOnClickListener(this);
        ivPlaybarMenu.setOnClickListener(this);
        ivPlaybarPlay.setOnClickListener(this);
        llplaybar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_playbar_play) {//暂停/播放
            // 首次点击播放按钮，默认播放第0首
            if (mediaPlayer == null) {
                changeMusic(0);
                String title = nowlist.get(noePosition + 1).getTitle();
                tvPlaybarSingName.setText(title);
                tvPlaybarSingerName.setText(nowlist.get(noePosition).getArtist());
            } else {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    ivPlaybarPlay.setImageResource(R.drawable.play);
                } else {
                    mediaPlayer.start();
                    ivPlaybarPlay.setImageResource(R.drawable.pause);
                }
            }
        } else if (v.getId() == R.id.iv_playbar_next) {// 下一首
            changeMusic(++noePosition);
            ivPlaybarPlay.setImageResource(R.drawable.pause);
            String title = nowlist.get(noePosition).getTitle();
            tvPlaybarSingName.setText(title);//展示下一首的歌名
            tvPlaybarSingerName.setText(nowlist.get(noePosition).getArtist());

        } else if (v.getId() == R.id.iv_playbar_menu) {
            showPopWindow();
        } else if (v.getId() == R.id.ll_playbar) {
            startActivity(new Intent(getContext(), playActivity.class));
        }

    }

    private void showPopWindow() {
        //1.设置contentVIew即填充popwindow的布局
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popwindow_item, null);
        // popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, 500, false);
        popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        //2.设置布局中各个View点击事件
        TextView tvpopclose = contentView.findViewById(R.id.tv_pop_close);
        tvpopclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //3.设置popwindow要显示的位置
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setAnimationStyle(R.style.pop_anim);

    }

    private void changeMusic(int position) {
        if (position < 0) {
            noePosition = position = nowlist.size() - 1;
        } else if (position > nowlist.size() - 1) {
            noePosition = position = 0;
        }

        try {
            // 切歌之前先重置，释放掉之前的资源
            mediaPlayer.reset();
            // 设置播放源
            mediaPlayer.setDataSource(nowlist.get(position).getUrl());
            // 开始播放前的准备工作，加载多媒体资源，获取相关信息
            mediaPlayer.prepare();

            // 开始播放
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView(View view) {
        ivPlaybarAlbum = view.findViewById(R.id.iv_playbar_album);
        tvPlaybarSingName = view.findViewById(R.id.tv_playbar_sing_name);
        tvPlaybarSingerName = view.findViewById(R.id.tv_playbar_singer_name);
        ivPlaybarPlay = view.findViewById(R.id.iv_playbar_play);
        ivPlaybarNext = view.findViewById(R.id.iv_playbar_next);
        ivPlaybarMenu = view.findViewById(R.id.iv_playbar_menu);
        llplaybar = view.findViewById(R.id.ll_playbar);
    }


}
