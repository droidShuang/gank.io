package com.walker.gank.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by walker on 2016/12/20 0020.
 */

public class ThemeHelper {
    private static final String CURRENT_THEME = "current_theme";
    public static final int PINK_THEME = 0x1;
    public static final int YELLOW_THEME = 0x2;

    public static SharedPreferences getSharedPregerence(Context context) {
        return context.getSharedPreferences("multiple_theme", Context.MODE_PRIVATE);
    }

    public static void setTheme(Context context, int theamId) {
        getSharedPregerence(context).edit().putInt(CURRENT_THEME, theamId).apply();
    }

    public static int getTheme(Context context) {
        return getSharedPregerence(context).getInt(CURRENT_THEME, PINK_THEME);
    }

    public static boolean isDefault(Context context) {
        return getSharedPregerence(context).getInt(CURRENT_THEME, PINK_THEME) == PINK_THEME;
    }

    public static String getThemeName(int themeId) {
        switch (themeId) {
            case PINK_THEME:
                return "PINK";
            case YELLOW_THEME:
                return "YELLOW";
            default:
                return "THEME";
        }
    }
}
