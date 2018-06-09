package com.example.lancer.lancermusic.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.lancer.lancermusic.R;

/**
 * 主题管理类
 * Created by Lancer on 2018/6/9.
 */

public class ThemeUtil {
    private String[] mTheme = {"原谅绿", "酷炫黑", "少女红", "胖次蓝", "基佬紫", "活力橙", "大地棕", "哔哩粉" };
    private static ThemeUtil mInstance;

    public static ThemeUtil getInstance() {
        if (mInstance == null) {
            synchronized (ThemeUtil.class) {
                if (mInstance == null) {
                    mInstance = new ThemeUtil();
                }
            }
        }
        return mInstance;
    }
    /*
    * 获得主题
    * */
    public String[] getTheme() {
        return mTheme;
    }

    /*
    *设置主题
    **/
    public void setTheme(Activity context, String theme) {
        String currentTheme = SpTools.getCurrentTheme(context);
        if (currentTheme != null && currentTheme.equals(theme)) {
            return;
        }
        SpTools.setCurrentTheme(context, theme);
        context.finish();
        Intent intent = context.getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constant.UPDATA_THEME, true);
        context.startActivity(intent);
    }

    /**
     * 获取当前主题名
     *
     * @param context 上下文
     * @return 如: 少女粉
     */
    public String getCurThemeName(Context context) {
        return SpTools.getCurrentTheme(context);
    }

    /*
    * 初始化
    * */
    public void init(Context context) {
        String theme = SpTools.getCurrentTheme(context);
        if (theme.equals(mTheme[0])) {
            context.setTheme(R.style.AppTheme);
        } else if (theme.equals(mTheme[1])) {
            context.setTheme(R.style.AppTheme_Black);
        } else if (theme.equals(mTheme[2])) {
            context.setTheme(R.style.AppTheme_Green);
        } else if (theme.equals(mTheme[3])) {
            context.setTheme(R.style.AppTheme_Blue);
        } else if (theme.equals(mTheme[4])) {
            context.setTheme(R.style.AppTheme_Purple);
        } else if (theme.equals(mTheme[5])) {
            context.setTheme(R.style.AppTheme_Orange);
        } else if (theme.equals(mTheme[6])) {
            context.setTheme(R.style.AppTheme_Brown);
        } else if (theme.equals(mTheme[7])) {
            context.setTheme(R.style.AppTheme_bilipank);
        }
    }
}
