package me.khrystal.leancloudmsg.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.khrystal.leancloudmsg.base.BaseViewHolder;
import me.khrystal.leancloudmsg.net.LeanCloudClientManager;
import me.khrystal.leancloudmsg.viewholder.LeftTextViewHolder;
import me.khrystal.leancloudmsg.viewholder.RightTextViewHolder;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/12
 * update time:
 * email: 723526676@qq.com
 */
public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_LEFT_TEXT = 0;
    private final int ITEM_RIGHT_TEXT = 1;

    private final long TIME_INTERVAL = 10 * 60 * 1000;

    private List<AVIMMessage> messageList = new ArrayList<>();

    public MultipleItemAdapter(){}

    public void setMessageList(List<AVIMMessage> messages) {
        messageList.clear();
        if (null != messages) {
            messageList.addAll(messages);
        }
    }

    public void addMessageList(List<AVIMMessage> messages) {
        messageList.addAll(0, messages);
    }

    public void addMessage(AVIMMessage message) {
        messageList.addAll(Arrays.asList(message));
    }

    @Override
    public int getItemViewType(int position) {
        AVIMMessage message = messageList.get(position);
        if (message.getFrom().equals(LeanCloudClientManager.getInstance().getClientId())) {
            return ITEM_RIGHT_TEXT;
        } else {
            return ITEM_LEFT_TEXT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_LEFT_TEXT) {
            return new LeftTextViewHolder(parent.getContext(), parent);
        } else if (viewType == ITEM_RIGHT_TEXT) {
            return new RightTextViewHolder(parent.getContext(), parent);
        } else {
            // TODO: 16/6/12
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        鸭子？
        ((BaseViewHolder)holder).bindData(messageList.get(position));
        if (holder instanceof LeftTextViewHolder) {
            ((LeftTextViewHolder)holder).showTimeView(shouldShowTime(position));
        } else if (holder instanceof RightTextViewHolder) {
            ((RightTextViewHolder)holder).showTimeView(shouldShowTime(position));
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private boolean shouldShowTime(int position) {
        if (position == 0) {
            return true;
        }
        long lastTime = messageList.get(position - 1).getTimestamp();
        long curTime = messageList.get(position).getTimestamp();
        return curTime - lastTime > TIME_INTERVAL;
    }
}
