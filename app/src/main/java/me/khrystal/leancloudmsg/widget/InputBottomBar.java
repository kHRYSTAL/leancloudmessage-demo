package me.khrystal.leancloudmsg.widget;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import me.khrystal.leancloudmsg.R;
import me.khrystal.leancloudmsg.event.InputBottomBarEvent;
import me.khrystal.leancloudmsg.event.InputBottomBarTextEvent;

/**
 * usage:专门负责输入的底部操作栏，与 activity 解耦
 * 当点击相关按钮时发送 InputBottomBarEvent，需要的 View 可以自己去订阅相关消息
 * author: kHRYSTAL
 * create time: 16/6/10
 * update time:
 * email: 723526676@qq.com
 */
public class InputBottomBar extends LinearLayout{


    /**
     * 最小间隔时间为 1 秒，避免多次点击
     */
    private final int MIN_INTERVAL_SEND_MESSAGE = 1000;

    /**
     * 发送文本的Button
     */
    private ImageButton sendTextBtn;

    private EditText contentView;

    public InputBottomBar(Context context) {
        this(context, null);
    }

    public InputBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.input_bottom_bar, this);
        sendTextBtn = (ImageButton) findViewById(R.id.input_bottom_bar_btn_send);
        contentView = (EditText) findViewById(R.id.input_bottom_bar_et_content);
//        // TODO: 16/6/10 可提供接口
        setEditTextChangeListener();
        sendTextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentView.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(getContext(), R.string.message_is_null, Toast.LENGTH_SHORT);
                    return;
                }

                contentView.setText("");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendTextBtn.setEnabled(true);
                    }
                }, MIN_INTERVAL_SEND_MESSAGE);

                EventBus.getDefault().post(
                        new InputBottomBarTextEvent(InputBottomBarEvent.INPUTBOTTOMBAR_SEND_TEXT_ACTION, content, getTag()));
            }
        });

    }

    private void setEditTextChangeListener() {
        contentView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
