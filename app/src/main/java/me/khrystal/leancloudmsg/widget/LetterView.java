package me.khrystal.leancloudmsg.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import me.khrystal.leancloudmsg.event.MemberLetterEvent;

/**
 * usage: 联系人列表 快速滑动字母导航
 * 此处仅在滑动或点击时发送 MemberLetterEvent,
 * 接收放自己处理相关逻辑
 * 注意:因为长按事件等触发 有可能重复发送
 * author: kHRYSTAL
 * create time: 16/6/10
 * update time:
 * email: 723526676@qq.com
 */
public class LetterView extends LinearLayout{

    public LetterView(Context context) {
        this(context, null);
    }

    public LetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        updateLetters();
    }

    private void updateLetters() {
        setLetters(getSortLetters());
    }

    public void setLetters(List<Character> letters){
        removeAllViews();
        for(Character content : letters){
            TextView view = new TextView(getContext());
            view.setText(content.toString());
            addView(view);
        }

        setOnTouchListener(new OnTouchListener() {
            //简单处理
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = Math.round(event.getX());
                int y = Math.round(event.getY());
                for (int i = 0; i < getChildCount(); i++) {
                    TextView child = (TextView)getChildAt(i);
                    if(y > child.getTop() && y < child.getBottom()) {
                        MemberLetterEvent letterEvent = new MemberLetterEvent();
                        letterEvent.letter = child.getText().toString().charAt(0);
                        EventBus.getDefault().post(letterEvent);
                    }
                }
                //不执行onClick了
                return true;
            }
        });
    }

    private List<Character> getSortLetters(){
        List<Character> letterList = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++){
            letterList.add(c);

        }
        return letterList;
    }


}
