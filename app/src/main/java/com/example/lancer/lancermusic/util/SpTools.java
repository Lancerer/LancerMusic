package com.example.lancer.lancermusic.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lancer on 2018/6/7.
 */

public class SpTools {
    public class MyConstants {

        public static final String CONFIGFILE = "config";
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        int value = sp.getInt(key, defValue);
        return value;
    }

    public static Boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        boolean spBoolean = sp.getBoolean(key, defValue);
        return spBoolean;
    }

    public static void setBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, defValue).commit();
    }

    /**
     * 获取当前用户主题
     */
    public static String getCurrentTheme(Context context) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        if (sp != null)
            return sp.getString("theme", "原谅绿");
        return null;
    }

    /**
     * 获取当前用户主题
     */
    public static void setCurrentTheme(Context context, String theme) {
        SharedPreferences sp = context.getSharedPreferences(MyConstants.CONFIGFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (editor != null) {
            editor.putString("theme", theme);
            editor.commit();
        }
    }

}
