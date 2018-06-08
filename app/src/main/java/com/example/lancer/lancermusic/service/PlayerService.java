package com.example.lancer.lancermusic.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;

import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.lancer.lancermusic.bean.MusicBean;
import com.example.lancer.lancermusic.util.Constant;
import com.example.lancer.lancermusic.util.SpTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener/*, AudioManager.OnAudioFocusChangeListener*/ {
    private static final String TAG = "PlayerService";
    private static MediaPlayer mPlayer = null;
    private List<MusicBean> lists = new ArrayList<>();
    private MusicBroadReceiver receiver;
    private int curposition;//第几首音乐
    private Messenger mMessenger;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayer.start();
        if (mPlayer != null) {
            sentPreparedMessageToMain();
        }
    }

    private void sentPreparedMessageToMain() {
        Message message = new Message();
        message.what = Constant.STATUS_PREPARED;
        message.arg1 = curposition;
        message.obj = mPlayer.isPlaying();
        try {
            //发送播放位置
            mMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_NEXT);
        sendBroadcast(intent);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_NEXT);
        sendBroadcast(intent);
        return true;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: musicService");
        regFilter();
        initMediaplay();
        super.onCreate();
    }

    private void initMediaplay() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            lists = intent.getParcelableArrayListExtra("list");
            curposition = SpTools.getInt(getApplicationContext(), "music_current_position", 0);
            mMessenger = (Messenger) intent.getExtras().get("messager");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getApplicationContext().unregisterReceiver(receiver);
        }
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    /**
     * 播放
     */
    private void play(int position) {
        if (mPlayer != null && lists.size() > 0) {
            mPlayer.reset();
            try {
                mPlayer.setDataSource(lists.get(position).getUrl());
                mPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 暂停
     */
    private void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            curposition = mPlayer.getCurrentPosition();
            mPlayer.pause();
        }
    }

    /*注册广播*/
    private void regFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_LIST_ITEM);
        intentFilter.addAction(Constant.STATUS_END);
        intentFilter.addAction(Constant.STATUS_PAUSE);
        intentFilter.addAction(Constant.STATUS_PLAY);
        intentFilter.addAction(Constant.STATUS_STOP);
        intentFilter.addAction(Constant.ACTION_NEXT);
        intentFilter.addAction(Constant.ACTION_UP);
        intentFilter.setPriority(1000);
        if (receiver == null) {
            receiver = new MusicBroadReceiver();
        }
        getApplicationContext().registerReceiver(receiver, intentFilter);
    }
    /**
     * 广播接收者
     */
    private class MusicBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.ACTION_LIST_ITEM:
                    Log.i(TAG, "onReceive: ACTION_LIST_ITEM");
                    curposition = intent.getIntExtra("position", 0);
                    play(curposition);
                    break;
                case Constant.STATUS_END:
                    break;
                case Constant.STATUS_PAUSE:
                    Log.i(TAG, "onReceive: ACTION_LIST_ITEM");
                    pause();
                    break;
                case Constant.STATUS_PLAY:
                    Log.i(TAG, "onReceive: ACTION_PLAY");
                    if (mPlayer != null) {
                        mPlayer.start();
                    } else {
                        initMediaplay();
                        play(curposition);
                    }
                    break;
                case Constant.STATUS_STOP:
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }
                    break;
                case Constant.ACTION_NEXT:
                    Log.i(TAG, "onReceive: ACTION_NEXT");
                    curposition++;
                    if (curposition <= lists.size() - 1) {
                        play(curposition);
                    } else {
                        curposition = 0;
                        play(curposition);
                    }
                    break;
                case Constant.ACTION_UP:
                    curposition--;
                    if (curposition < 0) {
                        curposition = lists.size() - 1;
                        play(curposition);
                    } else {
                        play(curposition);
                    }
                    break;
                default:
                    break;

            }
        }
    }

}
