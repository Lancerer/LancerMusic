package com.example.lancer.lancermusic.fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lancer.lancermusic.R;
import com.example.lancer.lancermusic.bean.MusicBean;
import com.example.lancer.lancermusic.util.MyMusicUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class singFragment extends Fragment implements MediaPlayer.OnCompletionListener {
    private View view;
    private ListView lvSing;
    private List<MusicBean> lists;
    private int mPosition;
    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_sing, null);
        initView();
        initData();
        return view;
    }

    private void initData() {
        mediaPlayer = new MediaPlayer();
        lists = new ArrayList<>();
        MyMusicUtil myMusicUtil = new MyMusicUtil();
        lists = myMusicUtil.getMusicInfo(getContext());
        MyAdapter adapter = new MyAdapter();
        lvSing.setAdapter(adapter);
        lvSing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                changeMusic(mPosition);   //切歌.
                EventBus.getDefault().post(new MessageEvent(mPosition, lists,mediaPlayer));
            }
        });
        mediaPlayer.setOnCompletionListener(this);
    }

    private void changeMusic(int position) {

        if (position < 0) {
            mPosition = position = lists.size() - 1;
        } else if (position > lists.size() - 1) {
            mPosition = position = 0;
        }

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        try {
            // 切歌之前先重置，释放掉之前的资源
            mediaPlayer.reset();
            // 设置播放源
            mediaPlayer.setDataSource(lists.get(position).getUrl());
            // 开始播放前的准备工作，加载多媒体资源，获取相关信息
            mediaPlayer.prepare();

            // 开始播放
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        changeMusic(++mPosition);
    }

  /*  @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }*/

    private   class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public MusicBean getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_sing, null);
                viewHolder = new ViewHolder();
                viewHolder.tvItemSing = convertView.findViewById(R.id.tv_item_sing);
                viewHolder.tvItemSinger = convertView.findViewById(R.id.tv_item_singer);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            MusicBean item = getItem(position);
            viewHolder.tvItemSing.setText("" + item.getTitle());
            viewHolder.tvItemSinger.setText("" + item.getArtist());
            return convertView;
        }
    }

    public class ViewHolder {
        TextView tvItemSing;
        TextView tvItemSinger;
    }

    private void initView() {
        lvSing = view.findViewById(R.id.lv_sing);
    }

    public class MessageEvent {
        public int position;
        public List<MusicBean> list;
        public MediaPlayer mediaPlayer;

        public MessageEvent(int Position, List<MusicBean> list, MediaPlayer mediaPlayer) {
            this.position = Position;
            this.list = list;
            this.mediaPlayer = mediaPlayer;
        }
    }
}
