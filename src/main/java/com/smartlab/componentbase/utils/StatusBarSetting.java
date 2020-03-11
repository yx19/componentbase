package com.smartlab.componentbase.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

/**
 * @author：xxl
 * @Created in：2019/2/19
 */
public class StatusBarSetting {

    /**设置透明的顶部状态栏，并根据需要选择是否设置状态栏的图标和文字为暗色或淡色*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBar(Context context, boolean isDark) {
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else if (context instanceof ContextWrapper) {
            activity = (Activity) (ContextWrapper) ((ContextWrapper) context).getBaseContext();
        }
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

        /**设置状态栏的图标和文字为暗色或淡色*/
        if (isDark){
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//暗色
        }else {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//淡色
        }
    }


    /**
     * 不带toolbar的  直接在根布局添加
     * android:fitsSystemWindows="true"
     *
     * 带toolbar的 将toolbar的layout_height设置为wrap_content
     * 然后在toolbar添加android:fitsSystemWindows="true"
     */
}
