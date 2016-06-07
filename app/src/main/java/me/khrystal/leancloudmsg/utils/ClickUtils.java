package me.khrystal.leancloudmsg.utils;

import android.os.SystemClock;
import android.util.Log;

import me.khrystal.leancloudmsg.BuildConfig;

/**
 * @FileName: me.khrystal.leancloudmsg.utils.ClickUtils.java
 * @Fuction: 快速点击处理类
 * @author: kHRYSTAL
 * @email: 723526676@qq.com
 * @date: 2016-03-03 15:35
 * @UpdateUser:
 * @UpdateDate:
 */
public class ClickUtils {

    private static final String TAG = ClickUtils.class.getSimpleName();
    private static long lastClickTime = 0L;
    private static final boolean isDebug = BuildConfig.DEBUG;
    private static final String BLANK_LOG = "\t";

    /**
     * 用于处理频繁点击问题, 如果两次点击小于250毫秒则不予以响应
     *
     * @return true:是连续的快速点击
     */
    public static boolean isFastDoubleClick(){
        //从开机到现在毫秒数
        long nowTime = SystemClock.elapsedRealtime();
        if (isDebug){
            Log.d(TAG,"nowTime:" + nowTime);
            Log.d(TAG, "lastClickTime:" + lastClickTime);
            Log.d(TAG,"time space:"+(nowTime - lastClickTime));
        }
        if ((nowTime - lastClickTime) < 250) {

            if (isDebug){
                Log.d(TAG,"FastClick");
                Log.d(TAG, BLANK_LOG);
            }
            return true;
        } else {
            lastClickTime = nowTime;
            if (isDebug){
                Log.d(TAG,"lastClickTime:" + lastClickTime);
                Log.d(TAG,"Not FastClick");
                Log.d(TAG,BLANK_LOG);
            }
            return false;
        }
    }
}
