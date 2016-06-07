package me.khrystal.leancloudmsg.app;

import android.app.Application;
import android.content.Context;

import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import me.khrystal.leancloudmsg.BuildConfig;

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
