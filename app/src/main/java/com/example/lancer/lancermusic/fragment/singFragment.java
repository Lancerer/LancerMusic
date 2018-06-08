package com.example.lancer.lancermusic.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.example.lancer.lancermusic.util.Constant;
import com.example.lancer.lancermusic.util.MyMusicUtil;
import com.example.lancer.lancermusic.util.SpTools;

import java.util.ArrayList;
import java.util.List;

public class singFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ListView lvSing;
    private List<MusicBean> lists = new ArrayList<>();
    private int mPosition;
    private LinearLayout llPlaybar;
    private ImageView ivPlaybarAlbum;
    private TextView tvPlaybarSingName;
    private TextView tvPlaybarSingerName;
    private ImageView ivPlaybarPlay;
    private ImageView ivPlaybarNext;
    private ImageView ivPlaybarMenu;
    private boolean mIsPlaying;
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
    private PopupWindow popupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_sing, null);
        initView();
        initData();
        return view;
    }

    private void initData() {
        MyMusicUtil myMusicUtil = new MyMusicUtil();
        lists = myMusicUtil.getMp3Infos(getContext());
        startService();//todo
        lvSing.setAdapter(new MyAdapter());
        // 初始化控件UI，默认显示历史播放歌曲
        mPosition = SpTools.getInt(getContext(), "music_current_position", 0);
        initLinster();
        switchSongUI(mPosition);
    }

    private void switchSongUI(int position) {
        if (lists.size() > 0 && position < lists.size()) {
            //获得播放数据
            musicBean = lists.get(position);
            //设置歌手名，歌曲名
            tvPlaybarSingerName.setText(musicBean.getArtist());
            tvPlaybarSingName.setText(musicBean.getTitle());
        } else {
            Toast.makeText(getContext(), "没有歌曲，下载歌曲后再来使用 ", Toast.LENGTH_SHORT).show();
        }
    }

    private void initLinster() {
        ivPlaybarNext.setOnClickListener(this);
        ivPlaybarMenu.setOnClickListener(this);
        ivPlaybarPlay.setOnClickListener(this);
        llPlaybar.setOnClickListener(this);
        lvSing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendBrodcast(Constant.ACTION_LIST_ITEM, position);
                mPosition = position;
                ivPlaybarPlay.setImageResource(R.drawable.pause);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_playbar_menu:
                showPop();
                break;
            case R.id.iv_playbar_next:
                sendBroadcast(Constant.ACTION_NEXT);
                ivPlaybarPlay.setImageResource(R.drawable.pause);
                break;
            case R.id.iv_playbar_play:
                if (!mIsPlaying) {
                    ivPlaybarPlay.setImageResource(R.drawable.pause);
                    mIsPlaying = true;
                    sendBroadcast(Constant.STATUS_PLAY);
                } else {
                    ivPlaybarPlay.setImageResource(R.drawable.play);
                    mIsPlaying = false;
                    sendBroadcast(Constant.STATUS_PAUSE);
                }
                break;
            case R.id.ll_playbar:
                startActivity(new Intent(getContext(), playActivity.class));
                break;
            default:
                break;
        }
    }

    private void showPop() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popwindow_item, null);

        popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        //2.设置布局中各个View点击事件
        ListView lv_pop = contentView.findViewById(R.id.lv_pop);
        lv_pop.setAdapter(new MyAdapter());
        lv_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendBrodcast(Constant.ACTION_LIST_ITEM, position);
                ivPlaybarPlay.setImageResource(R.drawable.pause);
                popupWindow.dismiss();
            }
        });

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

    private void sendBroadcast(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        getActivity().sendBroadcast(intent);
    }

    private void sendBrodcast(String action, int position) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.setAction(action);
        getActivity().sendBroadcast(intent);
    }

    private void startService() {
        Intent intent = new Intent();
        intent.setClass(getContext(), PlayerService.class);
        intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) lists);
        intent.putExtra("messager", new Messenger(handler));
        getActivity().startService(intent);
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
        llPlaybar = view.findViewById(R.id.ll_playbar);
        ivPlaybarAlbum = view.findViewById(R.id.iv_playbar_album);
        tvPlaybarSingName = view.findViewById(R.id.tv_playbar_sing_name);
        tvPlaybarSingerName = view.findViewById(R.id.tv_playbar_singer_name);
        ivPlaybarPlay = view.findViewById(R.id.iv_playbar_play);
        ivPlaybarNext = view.findViewById(R.id.iv_playbar_next);
        ivPlaybarMenu = view.findViewById(R.id.iv_playbar_menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SpTools.setInt(getContext(), "music_current_position", mPosition);

    }


}
