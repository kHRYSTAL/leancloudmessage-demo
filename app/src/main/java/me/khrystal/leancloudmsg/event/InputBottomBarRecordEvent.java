package me.khrystal.leancloudmsg.event;

/**
 * usage: 录音事件 录音完成时触发
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public class InputBottomBarRecordEvent extends InputBottomBarEvent{
    /**
     * 录音本地路径
     */
    public String audioPath;

    /**
     * 录音长度
     */
    public int audioDuration;

    public InputBottomBarRecordEvent(int action, String path, int duration, Object tag) {
        super(action, tag);
        audioDuration = duration;
        audioPath = path;
    }
}
