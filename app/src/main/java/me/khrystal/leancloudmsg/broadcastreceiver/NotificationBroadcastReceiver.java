package me.khrystal.leancloudmsg.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import me.khrystal.leancloudmsg.module.chat.view.LoginActivity;
import me.khrystal.leancloudmsg.net.LeanCloudClientManager;
import me.khrystal.leancloudmsg.utils.Constants;

/**
 * usage: 因为 notification 点击时，控制权不在 app，此时如果 app 被 kill 或者上下文改变后，
 * 有可能对 notification 的响应会做相应的变化，所以此处将所有 notification 都发送至此类，
 * 然后由此类做分发。
 * author: kHRYSTAL
 * create time: 16/6/10
 * update time:
 * email: 723526676@qq.com
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if (LeanCloudClientManager.getInstance().getClient() == null) {
            gotoLoginActivity(context);
        } else {
            String conversationId = intent.getStringExtra(Constants.SQUARE_CONVERSATION_ID);
            if (!TextUtils.isEmpty(conversationId)) {
                if (Constants.SQUARE_CONVERSATION_ID.equals(conversationId)) {
                    gotoSquareActivity(context, intent);
                } else {
                    gotoSingleChatActivity(context, intent);
                }
            }

        }
    }


    private void gotoSingleChatActivity(Context context, Intent intent) {

    }

    private void gotoSquareActivity(Context context, Intent intent) {

    }

    /**
     * 如果 app 上下文已经缺失 则跳转到登录页面
     * @param context
     */
    private void gotoLoginActivity(Context context) {
        Intent startActivityIntent = new Intent(context, LoginActivity.class);
        startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startActivityIntent);
    }
}
