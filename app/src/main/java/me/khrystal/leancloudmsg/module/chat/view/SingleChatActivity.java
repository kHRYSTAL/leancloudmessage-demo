package me.khrystal.leancloudmsg.module.chat.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import me.khrystal.leancloudmsg.R;
import me.khrystal.leancloudmsg.annoation.ContentInject;
import me.khrystal.leancloudmsg.base.BaseActivity;
import me.khrystal.leancloudmsg.net.LeanCloudClientManager;
import me.khrystal.leancloudmsg.utils.Constants;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/12
 * update time:
 * email: 723526676@qq.com
 */
@ContentInject(contentViewId = R.layout.activity_square)
public class SingleChatActivity extends BaseActivity{

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    protected ChatFragment chatFragment;

    @Override
    protected void initView() {
        chatFragment = (ChatFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_chat);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_navigation_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String memberId = getIntent().getStringExtra(Constants.MEMBER_ID);
        setTitle(memberId);
        getConversation(memberId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (null != extras && extras.containsKey(Constants.MEMBER_ID)) {
            String memberId = extras.getString(Constants.MEMBER_ID);
            setTitle(memberId);
            getConversation(memberId);
        }
    }


    /**
     * 获取 conversation，为了避免重复的创建，此处先 query 是否已经存在只包含该 member 的 conversation
     * 如果存在，则直接赋值给 ChatFragment，否者创建后再赋值
     */
    private void getConversation(final String memberId) {
        final AVIMClient client = LeanCloudClientManager.getInstance().getClient();
        AVIMConversationQuery conversationQuery = client.getQuery();
        conversationQuery.withMembers(Arrays.asList(memberId), true);
        conversationQuery.whereEqualTo("customConversationType",1);
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (filterException(e)) {
                    //注意：此处仍有漏洞，如果获取了多个 conversation，默认取第一个
                    if (null != list && list.size() > 0) {
                        chatFragment.setConversation(list.get(0));
                    } else {
                        HashMap<String,Object> attributes=new HashMap<String, Object>();
                        attributes.put("customConversationType",1);
                        client.createConversation(Arrays.asList(memberId), null, attributes, false , new AVIMConversationCreatedCallback() {
                            @Override
                            public void done(AVIMConversation avimConversation, AVIMException e) {
                                chatFragment.setConversation(avimConversation);
                            }
                        });
                    }
                }
            }
        });
    }
}
