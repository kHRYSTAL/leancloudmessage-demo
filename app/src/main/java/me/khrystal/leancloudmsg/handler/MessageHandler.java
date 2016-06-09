package me.khrystal.leancloudmsg.handler;

import android.content.Context;
import android.content.Intent;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import de.greenrobot.event.EventBus;
import me.khrystal.leancloudmsg.R;
import me.khrystal.leancloudmsg.broadcastreceiver.NotificationBroadcastReceiver;
import me.khrystal.leancloudmsg.event.ImTypeMessageEvent;
import me.khrystal.leancloudmsg.net.LeanCloudClientManager;
import me.khrystal.leancloudmsg.utils.Constants;
import me.khrystal.leancloudmsg.utils.NotificationUtil;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/10
 * update time:
 * email: 723526676@qq.com
 */
public class MessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage>{

    private Context context;

    public MessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {

        String clientID = "";
        try {
            clientID = LeanCloudClientManager.getInstance().getClientId();
            if (client.getClientId().equals(clientID)){
                //过滤掉自己发的消息
                if (!message.getFrom().equals(clientID)) {
                    sendEvent(message, conversation);
                    if (NotificationUtil.isShowNotification(conversation.getConversationId())){
                        sendNotification(message, conversation);
                    }
                }
            } else {
              client.close(null);
            }
        } catch (IllegalStateException e){
            client.close(null);
        }

    }

    private void sendNotification(AVIMTypedMessage message, AVIMConversation conversation) {
        String notificationContent = message instanceof AVIMTextMessage ?
                ((AVIMTextMessage)message).getText() : context.getString(R.string.unspport_message_type);

        Intent intent = new Intent(context, NotificationBroadcastReceiver.class);
        intent.putExtra(Constants.CONVERSATION_ID, conversation.getConversationId());
        intent.putExtra(Constants.MEMBER_ID, message.getFrom());
        NotificationUtil.showNotification(context, "", notificationContent, null, intent);
    }

    /**
     * 因为没有 db，所以暂时先把消息广播出去，由接收方自己处理
     * 稍后应该加入 db todo
     * @param message
     * @param conversation
     */
    private void sendEvent(AVIMTypedMessage message, AVIMConversation conversation) {
        ImTypeMessageEvent event = new ImTypeMessageEvent();
        event.message = message;
        event.conversation = conversation;
        EventBus.getDefault().post(event);
    }
}
