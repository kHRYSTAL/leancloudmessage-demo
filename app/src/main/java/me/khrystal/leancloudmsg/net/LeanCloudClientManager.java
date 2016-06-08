package me.khrystal.leancloudmsg.net;

import android.text.TextUtils;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.socks.library.KLog;

/**
 * usage: 登录管理 单例模式
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
public class LeanCloudClientManager {

    private static LeanCloudClientManager imClientManager;

    private AVIMClient avimClient;
    private String clientId;

    public synchronized static LeanCloudClientManager getInstance() {
        if (null == imClientManager) {
            imClientManager = new LeanCloudClientManager();
        }
        return imClientManager;
    }

    private LeanCloudClientManager() {}

    public void open(String clientId, AVIMClientCallback callback) {
        this.clientId = clientId;
        avimClient = AVIMClient.getInstance(clientId);
        avimClient.open(callback);
    }

    public AVIMClient getClient() {
        return avimClient;
    }

    public String getClientId() {
        if (TextUtils.isEmpty(clientId)) {
            KLog.e("Please call AVImClientManager.open first");
            throw new IllegalStateException("Please call AVImClientManager.open first");
        }
        return clientId;
    }


}
