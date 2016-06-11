package me.khrystal.leancloudmsg.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.khrystal.leancloudmsg.viewholder.MemberViewHolder;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/11
 * update time:
 * email: 723526676@qq.com
 */
public class MembersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /**
     * 所有 Adapter 成员list
     */
    private List<MemberItem> memberList = new ArrayList<>();

    /**
     * 在有序的 memberList 中 MemberItem.sortContent 第一次出现时的字母与位置的 map
     */
    private Map<Character,Integer> indexMap = new HashMap<>();

    /**
     * 简体中文的 Collator
     */
    Collator cmp = Collator.getInstance(Locale.SIMPLIFIED_CHINESE);

    public MembersAdapter(){}

    public void setMemberList(List<String> list) {
        memberList.clear();
        if (null != list){
            for (String name : list) {
                MemberItem item = new MemberItem();
                item.content = name;
//                将字符串转换成相应格式的拼音 不带声调 没有分隔符
                item.sortContent = PinyinHelper.convertToPinyinString(name, "", PinyinFormat.WITHOUT_TONE);
                memberList.add(item);
            }
        }
        Collections.sort(memberList,new SortChineseName());
        updateIndex();
    }

    private void updateIndex() {
        Character lastCharcter = '#';
        indexMap.clear();
        for (int i = 0; i < memberList.size(); i++) {
            Character curChar = Character.toLowerCase(memberList.get(i).sortContent.charAt(0));
            if (!lastCharcter.equals(curChar)) {
                //当前字符第一次出现索引的位置
                indexMap.put(curChar, i);
            }
            lastCharcter = curChar;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberViewHolder(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MemberViewHolder)holder).bindData(memberList.get(position));
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public static class MemberItem{
        public String content;
        public String sortContent;
    }

    /**
     * 比较器
     */
    public class SortChineseName implements Comparator<MemberItem> {

        @Override
        public int compare(MemberItem str1, MemberItem str2) {
            if (null == str1) {
                return -1;
            }
            if (null == str2) {
                return 1;
            }
            if (cmp.compare(str1.sortContent, str2.sortContent)>0){
                return 1;
            }else if (cmp.compare(str1.sortContent, str2.sortContent)<0){
                return -1;
            }
            return 0;
        }
    }
}
