package me.khrystal.leancloudmsg.event;

/**
 * usage: InputBottombar相关的EventBus事件
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public class InputBottomBarEvent {
//    发送图片
    public static final int INPUTBOTTOMBAR_IMAGE_ACTION = 0;
//    发送短视频
    public static final int INPUTBOTTOMBAR_CAMERA_ACTION = 1;
//    发送定位
    public static final int INPUTBOTTOMBAR_LOCATION_ACTION = 2;
//    发送文字消息
    public static final int INPUTBOTTOMBAR_SEND_TEXT_ACTION = 3;
//     发送语音
    public static final int INPUTBOTTOMBAR_SEND_AUDIO_ACTION = 4;

    public int eventAction;
    public Object tag;

    public InputBottomBarEvent(int action, Object tag) {
        eventAction = action;
        this.tag = tag;
    }
}
