package com.walker.gank.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.squareup.leakcanary.LeakCanary;
import com.walker.gank.R;
import com.walker.gank.util.ThemeHelper;


/**
 * Created by walker on 2016/12/20 0020.
 */

public class MApplication extends Application implements ThemeUtils.switchColor {
    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        ThemeUtils.setSwitchColor(this);
        context = getApplicationContext();

    }

    @Override
    public int replaceColorById(Context context, @ColorRes int colorId) {
        if (ThemeHelper.isDefault(context)) {
            return context.getResources().getColor(colorId);
        }
        String theme = getTheme(context);
        if (theme != null) {
            colorId = getThemeColorById(context, colorId, theme);
        }
        return context.getResources().getColor(colorId);

    }

    @Override
    public int replaceColor(Context context, @ColorInt int color) {
        return 0;
    }

    private String getTheme(Context context) {
        if (ThemeHelper.getTheme(context) == ThemeHelper.PINK_THEME) {
            return "pink";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.YELLOW_THEME) {
            return "yellow";
        }
        return null;
    }

    private int getThemeColorById(Context context, int colorId, String theme) {
        switch (colorId) {
            case R.color.theme_color_primary:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case R.color.theme_color_primary_dark:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case R.color.theme_color_primary_trans:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());

        }
        return colorId;
    }

    public Context getContext() {
        return context;
    }

}
