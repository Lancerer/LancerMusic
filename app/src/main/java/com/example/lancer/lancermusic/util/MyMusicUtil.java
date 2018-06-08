package com.example.lancer.lancermusic.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.lancer.lancermusic.bean.MusicBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Lancer on 2018/5/29.
 */

public class MyMusicUtil {
    public  List<MusicBean> getMp3Infos(Context context) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        int mId = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int mTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int mArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int mAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int mAlbumID = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        int mDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int mSize = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
        int mUrl = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int mIsMusic = cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC);

        List<MusicBean> mp3Infos = new ArrayList<>();
        for (int i = 0, p = cursor.getCount(); i < p; i++) {
            cursor.moveToNext();
            MusicBean mp3Info = new MusicBean();
            long id = cursor.getLong(mId); // 音乐id
            String title = cursor.getString(mTitle); // 音乐标题
            String artist = cursor.getString(mArtist); // 艺术家
            String album = cursor.getString(mAlbum); // 专辑
            long albumId = cursor.getInt(mAlbumID);
            long duration = cursor.getLong(mDuration); // 时长
            long size = cursor.getLong(mSize); // 文件大小
            String url = cursor.getString(mUrl); // 文件路径
            int isMusic = cursor.getInt(mIsMusic); // 是否为音乐
            if (isMusic != 0 && url.matches(".*\\.mp3$") && duration > 30 * 1000 && !artist.equals("<unknown>") && !artist.equals("")) { // 只把音乐添加到集合当中
                Log.d("song", "id:" + id + " title: " + title + " artist:" + artist + " album:" + album + " size:" + size + " duration: " + duration);
                mp3Info.setId(id);
                mp3Info.setTitle(title);
                mp3Info.setArtist(artist);
                mp3Info.setAlbum(album);
                mp3Info.setAlbumId(albumId);
                mp3Info.setDuration(duration);
                mp3Info.setSize(size);
                mp3Info.setUrl(url);
                mp3Infos.add(mp3Info);
            }
        }
        cursor.close();
        return mp3Infos;
    }
    /**
     * 往List集合中添加Map对象数据，每一个Map对象存放一首音乐的所有属性
     *
     * @param mp3Infos
     * @return
     */
    public static List<HashMap<String, String>> getMusicMaps(List<MusicBean> mp3Infos) {
        List<HashMap<String, String>> mp3list = new ArrayList<>();
        for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext(); ) {
            MusicBean mp3Info = (MusicBean) iterator.next();
            HashMap<String, String> map = new HashMap<>();
            map.put("title", mp3Info.getTitle());
            map.put("Artist", mp3Info.getArtist());
            map.put("album", mp3Info.getAlbum());
            map.put("albumId", String.valueOf(mp3Info.getAlbumId()));
            map.put("duration", formatTime(mp3Info.getDuration()));
            map.put("size", String.valueOf(mp3Info.getSize()));
            map.put("url", mp3Info.getUrl());
            mp3list.add(map);
        }
        return mp3list;
    }
    /**
     * 格式化时间，将毫秒转换为分:秒格式
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }
}
