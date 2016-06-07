package me.khrystal.leancloudmsg.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;

import com.socks.library.KLog;

import me.khrystal.leancloudmsg.R;

/**
 * @FileName: me.khrystal.leancloudmsg.utils.MeasureUtil.java
 * @Fuction: 测量工具类
 * @author: kHRYSTAL
 * @email: 723526676@qq.com
 * @date: 2016-03-03 15:52
 * @UpdateUser:
 * @UpdateDate:
 */
public class MeasureUtil {

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources()
                // TODO: 16/4/28
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId>0){
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取工具栏高度
     * @param context
     * @return
     */
    public static int getToolbarHeight(Context context){
        final TypedArray styledAttributes = context.getTheme()
                .obtainStyledAttributes(new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int)styledAttributes.getDimension(0,0);
        styledAttributes.recycle();
        return toolbarHeight;
    }

    public static int getNavigationBarHeight(Activity activity){
        Resources resources = activity.getResources();
        int rid = resources.getIdentifier("config_showNavigationBar","bool","android");
        if (rid>0){
            KLog.e("NavigationBar is display"+resources.getBoolean(rid)+"");
        }
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId>0){
            KLog.e("NavigationBar height" + resources.getDimensionPixelSize(resourceId)+"");
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }


    /**
     * get Screen Size
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize(Context context){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Point screenSize = new Point();
        wm.getDefaultDisplay().getSize(screenSize);
        return screenSize;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
