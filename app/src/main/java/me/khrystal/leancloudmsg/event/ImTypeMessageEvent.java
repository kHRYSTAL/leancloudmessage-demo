package me.khrystal.leancloudmsg.event;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public class ImTypeMessageEvent {
//    消息类型对象
    public AVIMTypedMessage message;
//    会话对象
    public AVIMConversation conversation;
}
