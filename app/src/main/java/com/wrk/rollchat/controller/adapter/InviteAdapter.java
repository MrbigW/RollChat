package com.wrk.rollchat.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.wrk.rollchat.R;
import com.wrk.rollchat.model.bean.InvitationInfo;
import com.wrk.rollchat.model.bean.InvitationStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrbigW on 2016/11/8.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class InviteAdapter extends BaseAdapter {

    private Context mContext;

    private List<InvitationInfo> mInvitationInfos;

    private onInviteChangedListener mOnInviteChangedListener;

    public InviteAdapter(Context context, onInviteChangedListener listener) {
        this.mContext = context;
        this.mOnInviteChangedListener = listener;
        mInvitationInfos = new ArrayList<>();
    }

    // 获取邀请信息的数据
    public void refresh(List<InvitationInfo> invitationInfos) {
        if (invitationInfos != null && invitationInfos.size() >= 0) {
            // 接收数据
            mInvitationInfos.clear();
            mInvitationInfos.addAll(invitationInfos);

            // 刷新适配器
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mInvitationInfos == null ? 0 : mInvitationInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mInvitationInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_invite, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final InvitationInfo info = mInvitationInfos.get(position);

        if (info.getUser_hxid() != null) { // 联系人邀请信息
            holder.tvInviteName.setText(info.getUser_hxid());
            // 控制接受和拒绝按钮的隐藏和显示
            holder.btnInviteAccept.setVisibility(View.GONE);
            holder.btnInviteReject.setVisibility(View.GONE);

            if (InvitationStatus.NEW_INVITE == info.getStatus()) {
                if (info.getReason() != null) {
                    holder.tvInviteReason.setText(info.getReason());
                } else {
                    holder.tvInviteReason.setText("好友邀请");
                }
                // 显示接受和拒绝按钮
                holder.btnInviteAccept.setVisibility(View.VISIBLE);
                holder.btnInviteReject.setVisibility(View.VISIBLE);
            } else if (InvitationStatus.INVITE_ACCEPT == info.getStatus()) {
                if (info.getReason() != null) {
                    holder.tvInviteReason.setText(info.getReason());
                } else {
                    holder.tvInviteReason.setText("接受了邀请");
                }

            } else if (InvitationStatus.INVITE_ACCEPT_BY_PEER == info.getStatus()) {
                if (info.getReason() != null) {
                    holder.tvInviteReason.setText(info.getReason());
                } else {
                    holder.tvInviteReason.setText("邀请被接受了");
                }
            }

            // 对接受和拒绝按钮监听回调
            holder.btnInviteAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInviteChangedListener.onAccept(info);
                }
            });
            holder.btnInviteReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInviteChangedListener.onReject(info);
                }
            });


        } else { // 群邀请信息

        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_invite_name)
        TextView tvInviteName;
        @BindView(R.id.tv_invite_reason)
        TextView tvInviteReason;
        @BindView(R.id.btn_invite_accept)
        Button btnInviteAccept;
        @BindView(R.id.btn_invite_reject)
        Button btnInviteReject;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public interface onInviteChangedListener {

        void onAccept(InvitationInfo invitationInfo);

        void onReject(InvitationInfo invitationInfo);

    }

}
