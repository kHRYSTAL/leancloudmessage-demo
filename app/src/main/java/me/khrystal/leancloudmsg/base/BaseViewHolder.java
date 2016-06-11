package me.khrystal.leancloudmsg.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/12
 * update time:
 * email: 723526676@qq.com
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(Context context, ViewGroup root, int layoutRes) {
        super(LayoutInflater.from(context).inflate(layoutRes, root, false));
        ButterKnife.bind(this,itemView);
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public abstract void bindData(T t);

    public void setData(T t) {
        bindData(t);
    }
}
