package me.khrystal.leancloudmsg.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.khrystal.leancloudmsg.R;
import me.khrystal.leancloudmsg.base.BaseViewHolder;
import me.khrystal.leancloudmsg.event.LeftChatItemClickEvent;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/12
 * update time:
 * email: 723526676@qq.com
 */
public class LeftTextViewHolder extends BaseViewHolder {

    @Bind(R.id.chat_left_text_tv_time)
    protected TextView timeView;

    @Bind(R.id.chat_left_text_tv_content)
    protected TextView contentView;

    @Bind(R.id.chat_left_text_tv_name)
    protected TextView nameView;

    public LeftTextViewHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.chat_left_text_view);
    }

    @OnClick({R.id.chat_left_text_tv_content, R.id.chat_left_text_tv_name})
    public void onNameClick(View view) {
        LeftChatItemClickEvent clickEvent = new LeftChatItemClickEvent();
        clickEvent.userId = nameView.getText().toString();
        EventBus.getDefault().post(clickEvent);
    }

    @Override
    public void bindData(Object o) {
        AVIMMessage message = (AVIMMessage)o;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(message.getTimestamp());

        String content =  getContext().getString(R.string.unspport_message_type);
        if (message instanceof AVIMTextMessage) {
            content = ((AVIMTextMessage)message).getText();
        }

        contentView.setText(content);
        timeView.setText(time);
        nameView.setText(message.getFrom());
    }

    public void showTimeView(boolean isShow) {
        timeView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
