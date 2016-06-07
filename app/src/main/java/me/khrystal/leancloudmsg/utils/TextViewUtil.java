package me.khrystal.leancloudmsg.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @FileName: me.khrystal.leancloudmsg.utils.TextViewUtil.java
 * @Fuction: TextView相关工具类 应用SpannableString
 * @author: kHRYSTAL
 * @email: 723526676@qq.com
 * @date: 2016-03-03 16:09
 * @UpdateUser:
 * @UpdateDate:
 */
public class TextViewUtil {

    //给TextView设置部分大小
    public static void setPartialSize(TextView tv, int start, int end, int textSize) {
        String s = tv.getText().toString();
        Spannable spannable = new SpannableString(s);
        spannable.setSpan(new AbsoluteSizeSpan(textSize), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);
    }

    //给TextView设置部分颜色
    public static void setPartialColor(TextView tv, int start, int end, int textColor) {
        String s = tv.getText().toString();
        Spannable spannable = new SpannableString(s);
        spannable.setSpan(new ForegroundColorSpan(textColor), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);
    }

    //给TextView设置部分字体大小和颜色
    public static void setPartialSizeAndColor(TextView tv, int start, int end, int textSize, int textColor) {
        String s = tv.getText().toString();
        Spannable spannable = new SpannableString(s);
        spannable.setSpan(new AbsoluteSizeSpan(textSize, false), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(textColor), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);
    }

    //给TextView设置下划线
    public static void setUnderLine(TextView tv) {
        if (tv.getText() != null) {
            String udata = tv.getText().toString();
            SpannableString content = new SpannableString(udata);
            content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
            tv.setText(content);
            content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        } else {
            tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    //取消TextView的置下划线
    public static void clearUnderLine(TextView tv) {
        tv.getPaint().setFlags(0);
    }

    //半角转换为全角
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    //去除特殊字符或将所有中文标号替换为英文标号
    public static String replaceCharacter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!")
                .replaceAll("：", ":").replaceAll("（", "(").replaceAll("（", ")");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 搜索关键词，关键词且模糊关键词 变色展示
     * 如搜索苹果 模糊关键词iPhone、乔布斯也变色
     * 需求服务器将关键词加上<em></em>标签
     */
    public static SpannableStringBuilder keyStyleShow(String content, Context mContext){
        List<Integer> f_keys=searchKeyPosition(content,"<em>");
        List<Integer> e_keys=searchKeyPosition(content,"</em>");
        return keyStyle(content,f_keys,e_keys,mContext);
    }

    public static SpannableStringBuilder keyStyle(String content,List<Integer> f_keys,List<Integer> e_keys,Context mContext){
        content=content.replaceAll("<em>","");
        content=content.replaceAll("</em>","");
        SpannableStringBuilder style=new SpannableStringBuilder(content);
        KLog.e("SpannableStringBuilder","f_keys"+f_keys.size());
        for(int i=0;i<f_keys.size();i++){
            KLog.e("SpannableStringBuilder","SpannableStringBuilder"+i);
            if(i==0){
                // TODO: 16/5/5 以下颜色可以修改为需求颜色
                if(content.length()>=e_keys.get(i)-4){
                    style.setSpan(new ForegroundColorSpan(Color.RED),f_keys.get(i),e_keys.get(i)-4,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }else {
                if(content.length()>=e_keys.get(i)-13&&(f_keys.get(i)-9*i)<e_keys.get(i)-13*(i)){
                    style.setSpan(new ForegroundColorSpan(Color.RED), f_keys.get(i)-9*i, e_keys.get(i)-13*(i), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }
        }
//        for(int i=0;i<f_keys.size();i++){
//            Drawable d = mContext.getResources().getDrawable(R.drawable.tran);
//            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
//            style.setSpan(span, f_keys.get(i), f_keys.get(i)+4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        for(int i=0;i<e_keys.size();i++){
//            Drawable d = mContext.getResources().getDrawable(R.drawable.tran);
//            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//            ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
//            style.setSpan(span, e_keys.get(i), e_keys.get(i) + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
        return  style;
    }

    public static List<Integer> searchKeyPosition(String content,String key){
        if(content.contains(key)){
            List<Integer> mlist=new ArrayList<>();
            int cnt = 0;
            int offset = 0;
            while((offset = content.indexOf(key, offset)) != -1){
                mlist.add(offset);
                offset = offset + key.length();
                cnt++;
            }
            return  mlist;
        }
        return null;
    }

}
