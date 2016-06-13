package me.khrystal.leancloudmsg.module.chat.view;


import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;

import java.util.Arrays;
import java.util.List;

import me.khrystal.leancloudmsg.R;
import me.khrystal.leancloudmsg.annoation.ContentInject;
import me.khrystal.leancloudmsg.base.BaseActivity;
import me.khrystal.leancloudmsg.net.LeanCloudClientManager;
import me.khrystal.leancloudmsg.utils.Constants;

/**
 * usage: 广场页面 即群组聊天页面
 * 1. 根据clientId 获得 AVIMClient 实例
 * 2. 根据 conversationId 获得 AVIMConversation 实例
 * 3. 必须要加入 conversation 后才能拉取消息
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
@ContentInject(contentViewId = R.layout.activity_square)
public class SquareActivity extends BaseActivity{

    private AVIMConversation squareConversation;
    private ChatFragment chatFragment;
    private Toolbar toolbar;

    /**
     * 上一次点击 back 的时间
     * 用于双击退出的判断
     */
    private static long lastBackTime = 0;

    /**
     * 当双击 back 在此间隔内是直接触发 onBackPressed
     */
    private final int BACK_INTERVAL = 1000;


    @Override
    protected void initView() {
        String conversationId = getIntent().getStringExtra(Constants.CONVERSATION_ID);
        String title = getIntent().getStringExtra(Constants.ACTIVITY_TITLE);

        chatFragment = (ChatFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_chat);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setTitle(title);

        getSquare(conversationId);
        queryInSquare(conversationId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_square, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastBackTime < BACK_INTERVAL) {
            super.onBackPressed();
        } else {
            showSnackbar("双击 back 退出");
        }

        lastBackTime = currentTime;
    }

    /**
     * 先查询自己是否已经在该 conversation，如果存在则直接给 chatFragment 赋值，否则先加入，再赋值
     */
    private void queryInSquare(String conversationId) {
        final AVIMClient client = LeanCloudClientManager.getInstance().getClient();
//        查询是否已在当前会话中
        AVIMConversationQuery conversationQuery = client.getQuery();
//        objectId 会话id
//        select * from conversation where objectId = 'xxx'
        conversationQuery.whereEqualTo("objectId", conversationId);
//        链式调用 增加查询条件，指定聊天室的组员包含某些成员即可返回
        conversationQuery.containsMembers(Arrays.asList(LeanCloudClientManager.getInstance().getClientId()));
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (filterException(e)) {
                    if (null != list && list.size() > 0) {
                        chatFragment.setConversation(list.get(0));
                    } else {
                        joinSquare();
                    }
                }
            }
        });
    }

    /**
     * 加入 conversation
     */
    private void joinSquare() {
        squareConversation.join(new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (filterException(e)) {
                    chatFragment.setConversation(squareConversation);
                }
            }
        });
    }

    /**
     * 根据 conversationId 查取本地缓存中的 conversation，如若没有缓存，则返回一个新建的 conversaiton
     */
    private void getSquare(String conversationId) {

        if (TextUtils.isEmpty(conversationId)) {
            throw new IllegalArgumentException("conversationId can not be null");
        }

        AVIMClient client = LeanCloudClientManager.getInstance().getClient();

        if (null != client) {
            //根据 conversationId 查取本地缓存中的 conversation，如若没有缓存，则返回一个新建的 conversaiton
            squareConversation = client.getConversation(conversationId);
        } else {
            finish();
            showSnackbar("Please call AVIMClient.open first!");
        }
    }


}
