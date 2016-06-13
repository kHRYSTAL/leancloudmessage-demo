package me.khrystal.leancloudmsg.module.chat.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.util.List;

import butterknife.Bind;
import me.khrystal.leancloudmsg.R;
import me.khrystal.leancloudmsg.adapter.MultipleItemAdapter;
import me.khrystal.leancloudmsg.annoation.ContentInject;
import me.khrystal.leancloudmsg.base.BaseFragment;
import me.khrystal.leancloudmsg.event.ImTypeMessageEvent;
import me.khrystal.leancloudmsg.event.ImTypeMessageResendEvent;
import me.khrystal.leancloudmsg.event.InputBottomBarTextEvent;
import me.khrystal.leancloudmsg.utils.NotificationUtil;
import me.khrystal.leancloudmsg.widget.InputBottomBar;

/**
 * usage: 聊天界面 同activity一同初始化
 * author: kHRYSTAL
 * create time: 16/6/8
 * update time:
 * email: 723526676@qq.com
 */
@ContentInject(contentViewId = R.layout.fragment_chat)
public class ChatFragment extends BaseFragment {

    @Bind(R.id.fragment_chat_rv_chat)
    protected RecyclerView recyclerView;

    @Bind(R.id.fragment_chat_srl_pullrefresh)
    protected SwipeRefreshLayout refreshLayout;

    @Bind(R.id.fragment_chat_inputbottombar)
    protected InputBottomBar inputBottomBar;

    protected AVIMConversation imConversation;

    protected LinearLayoutManager layoutManager;

    protected MultipleItemAdapter itemAdapter;


    /**
     * onViewCreated
     * @param fragmentRootView
     */
    @Override
    protected void initView(View fragmentRootView) {
//        initview
        refreshLayout.setEnabled(false);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        itemAdapter = new MultipleItemAdapter();
        recyclerView.setAdapter(itemAdapter);

//        do sth when created
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                按列表位置计算 获取最上面的一条消息 即最早的消息
                AVIMMessage message = itemAdapter.getFirstMessage();
                if (null != imConversation) {
//                从av服务器获取比这条消息还早的消息 限制条数为20条
                  imConversation.queryMessages(message.getMessageId(), message.getTimestamp(), 20, new AVIMMessagesQueryCallback() {
                      @Override
                      public void done(List<AVIMMessage> list, AVIMException e) {
                          refreshLayout.setRefreshing(false);
                          if (filterException(e)) {
                              if (null != list && list.size() >0) {
                                  //更新条目
                                  itemAdapter.addMessageList(list);
                                  itemAdapter.notifyDataSetChanged();
                                  //假设之前有10条  更新后有30条 则从第20条（更新前的0） 则滚动到第19条
                                  layoutManager.scrollToPositionWithOffset(list.size()-1,0);
                              }
                          }
                      }
                  });
                } else {
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != imConversation) {
            NotificationUtil.addTag(imConversation.getConversationId());
        }
    }

    @Override
    public void onPause() {
        super.onResume();
        NotificationUtil.removeTag(imConversation.getConversationId());
    }

    public void setConversation(AVIMConversation conversation) {
        if (null != conversation) {
            imConversation = conversation;
//            设置刷新
            refreshLayout.setEnabled(true);
            inputBottomBar.setTag(imConversation.getConversationId());
//            拉去消息
            fetchMessages();
//            添加至notificationUtil 在当前界面 不弹出通知
            NotificationUtil.addTag(conversation.getConversationId());
        }
    }

    /**
     * 拉取消息，必须加入 conversation 后才能拉取消息
     */
    private void fetchMessages() {
        if (null != imConversation) {
            imConversation.queryMessages(new AVIMMessagesQueryCallback() {
                @Override
                public void done(List<AVIMMessage> list, AVIMException e) {
                    if (filterException(e)) {
                        itemAdapter.setMessageList(list);
                        recyclerView.setAdapter(itemAdapter);
                        itemAdapter.notifyDataSetChanged();
//获取消息 并滚动到最底部（最新的消息）
                        scrollToBottom();
                    }
                }
            });
        }
    }

    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(itemAdapter.getItemCount() - 1, 0);
    }

    /**
     * 发送消息
     * 输入事件处理，接收后构造成 AVIMTextMessage 然后发送
     * 因为不排除某些特殊情况会受到其他页面过来的无效消息，所以此处加了 tag 判断
     */
    public void onEvent(InputBottomBarTextEvent textEvent) {
        if (null != imConversation && null != textEvent) {
            if (!TextUtils.isEmpty(textEvent.textContent) && imConversation.getConversationId().equals(textEvent.tag)) {
                AVIMTextMessage message = new AVIMTextMessage();
                message.setText(textEvent.textContent);
                itemAdapter.addMessage(message);
                itemAdapter.notifyDataSetChanged();
                scrollToBottom();
//                发送后 message会自动增加状态 成功或失败
                imConversation.sendMessage(message, new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        itemAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    /**
     * 接收消息
     * 同理，避免无效消息，此处加了 conversation id 判断
     */
    public void onEvent(ImTypeMessageEvent event) {
        if (null != imConversation && null != event &&
                imConversation.getConversationId().equals(event.conversation.getConversationId())) {
            itemAdapter.addMessage(event.message);
            itemAdapter.notifyDataSetChanged();
            scrollToBottom();
        }
    }

    /**
     * 点击重新发送执行
     * 重新发送已经发送失败的消息
     */
    public void onEvent(ImTypeMessageResendEvent event) {
        if (null != imConversation && null != event) {
            if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusFailed == event.message.getMessageStatus()
                    && imConversation.getConversationId().equals(event.message.getConversationId())) {
                imConversation.sendMessage(event.message, new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        itemAdapter.notifyDataSetChanged();
                    }
                });
                itemAdapter.notifyDataSetChanged();
            }
        }
    }
}
