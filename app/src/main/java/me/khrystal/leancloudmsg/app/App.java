package me.khrystal.leancloudmsg.app;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import me.khrystal.leancloudmsg.BuildConfig;
import me.khrystal.leancloudmsg.handler.MessageHandler;

/**
 * usage: Application
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public class App extends Application{

    private RefWatcher mRefWatcher;

    private static Context mApplicationContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
        KLog.init(BuildConfig.DEBUG);
        // 这是使用美国节点的 app 信息，如果不使用美国节点，请 comment 这两行
//    AVOSCloud.useAVCloudUS();
//    AVOSCloud.initialize(this, "l8j5lm8c9f9d2l90213i00wsdhhljbrwrn6g0apptblu7l90",
//            "b3uyj9cmk84s5t9n6z1rqs9pvf2azofgacy9bfigmiehhheg");

        // 这是使用中国节点的 app 信息，如果使用中国节点，请 uncomment 这两行
        // 这是用于 SimpleChat 的 app id 和 app key，如果更改将不能进入 demo 中相应的聊天室
        AVOSCloud.initialize(this, "m7baukzusy3l5coew0b3em5uf4df5i2krky0ypbmee358yon",
                "2e46velw0mqrq3hl2a047yjtpxn32frm0m253k258xo63ft9");


        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    public static RefWatcher getRefWatcher(Context context){
        App application = (App)context.getApplicationContext();
        return application.mRefWatcher;
    }


    public static Context getContext(){
        return mApplicationContext;
    }


}
