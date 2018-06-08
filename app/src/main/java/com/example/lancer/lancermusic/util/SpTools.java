package com.example.lancer.lancermusic.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lancer on 2018/6/7.
 */

public class SpTools {
    public class MyConstants{

        public static final String CONFIGFILE = "config";
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static int getInt(Context context, String key, int defValue){
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        int value = sp.getInt(key, defValue);
        return value;
    }
}
