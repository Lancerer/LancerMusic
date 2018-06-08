package com.example.lancer.lancermusic.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import android.widget.TextView;

import com.example.lancer.lancermusic.R;
import com.example.lancer.lancermusic.bean.MusicBean;
import com.example.lancer.lancermusic.fragment.singFragment;
import com.example.lancer.lancermusic.util.MyMusicUtil;


import java.util.List;

/**
 * Created by Lancer on 2018/5/30.
 */

public class MyPopWindow extends PopupWindow {
    private View view;
    private Context context;
    private ListView listView;
    private MyAdapter adapter;
    private List<MusicBean> lists;

    public MyPopWindow(Context context) {
        super(context);
        this.context = context;
        MyMusicUtil myMusicUtil = new MyMusicUtil();
        lists = myMusicUtil.getMp3Infos(context);
        init();
    }

    private void init() {

    }

    private class MyAdapter extends BaseAdapter {
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
                convertView = View.inflate(context, R.layout.item_sing, null);
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
}
