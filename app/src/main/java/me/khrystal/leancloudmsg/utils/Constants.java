package me.khrystal.leancloudmsg.utils;

/**
 * usage:全局常量
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public class Constants {

    private static final String LEANMESSAGE_CONSTANTS_PREFIX = "com.leancloud.im.guide";

    public static final String MEMBER_ID = getPrefixConstant("member_id");
    public static final String CONVERSATION_ID = getPrefixConstant("conversation_id");

    public static final String ACTIVITY_TITLE = getPrefixConstant("activity_title");


    public static final String SQUARE_CONVERSATION_ID = "55cd829e60b2b52cda834469";

    private static String getPrefixConstant(String str) {
        return LEANMESSAGE_CONSTANTS_PREFIX + str;
    }
}
