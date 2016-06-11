package me.khrystal.leancloudmsg.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import me.khrystal.leancloudmsg.R;
import me.khrystal.leancloudmsg.adapter.MembersAdapter;
import me.khrystal.leancloudmsg.base.BaseViewHolder;
import me.khrystal.leancloudmsg.module.chat.view.SingleChatActivity;
import me.khrystal.leancloudmsg.utils.Constants;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/12
 * update time:
 * email: 723526676@qq.com
 */
public class MemberViewHolder extends BaseViewHolder<MembersAdapter.MemberItem>{

    @Bind(R.id.member_item_name)
    public TextView tvName;

    public MemberViewHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.item_member);
    }

    @Override
    public void bindData(final MembersAdapter.MemberItem item) {
        tvName.setText(item.content);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity host = (Activity) itemView.getContext();
                Intent intent = new Intent(host, SingleChatActivity.class);
                intent.putExtra(Constants.MEMBER_ID, item.content);
                host.startActivity(intent);
            }
        });
    }
}
