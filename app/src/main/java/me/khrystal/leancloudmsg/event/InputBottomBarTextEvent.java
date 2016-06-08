package me.khrystal.leancloudmsg.event;

/**
 * usage: 发送文本事件
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public class InputBottomBarTextEvent extends InputBottomBarEvent{

    public String textContent;

    public InputBottomBarTextEvent(int action, String content, Object tag) {
        super(action, tag);
        textContent = content;
    }
}
