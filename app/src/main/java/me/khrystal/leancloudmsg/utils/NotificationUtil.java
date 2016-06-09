package me.khrystal.leancloudmsg.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import me.khrystal.leancloudmsg.R;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/10
 * update time:
 * email: 723526676@qq.com
 */
public class NotificationUtil {

    /**
     * tag list, 用来标记是否应该展示 Notification
     * 比如已经在聊天页面了 实际就不应该弹出 notification
     */
    private static List<String> notificationTagList = new LinkedList<>();

    public static void addTag(String tag){
        if (!notificationTagList.contains(tag)){
            notificationTagList.add(tag);
        }
    }

    /**
     * 删除tag
     * @param tag
     */
    public static void removeTag(String tag){
        notificationTagList.remove(tag);
    }

    /**
     * 判断是否包含 如果不包含 返回true 应该显示notification
     * @param tag
     * @return
     */
    public static boolean isShowNotification(String tag){
        return !notificationTagList.contains(tag);
    }

    public static void showNotification(Context context, String title, String content, String sound, Intent intent){
        intent.setFlags(0);
//        此处简单处理 有可能发生重复id情况
        int notificationId = (new Random()).nextInt();
//        注册广播的PendingIntent 包裹参数默认intent
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0);
//        构造通知
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
//                        默认震动和声音
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentText(content);
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = mBuilder.build();

        if (sound != null && sound.trim().length() > 0){
            notification.sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "//" + sound);
        }
        manager.notify(notificationId, notification);
    }
}
