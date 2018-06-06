package com.example.lancer.lancermusic.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.lancer.lancermusic.bean.MusicBean;

import java.util.ArrayList;

/**
 * Created by Lancer on 2018/5/29.
 */

public class MyMusicUtil {
    MusicBean bean;//音乐信息类
    public ArrayList<MusicBean> InfoList;//将音乐信息填充到该集合中

    public ArrayList<MusicBean> getMusicInfo(Context context) {
        InfoList = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null
                , null);
        if (cursor != null && cursor.getCount() > 0) {
            MusicBean bean = null;
            while (cursor.moveToNext()) {
                bean = new MusicBean();
                bean.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                bean.setTitle((cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))));
                bean.setDuration(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                bean.setAbulm_id(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                bean.setUrl(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                //  info.setSize(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
                InfoList.add(bean);
            }
        }

        return InfoList;
    }
}
