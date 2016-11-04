package com.wrk.rollchat.model.bean;

/**
 * Created by MrbigW on 2016/11/4.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 状态的枚举类
 * -------------------=.=------------------------
 */

public enum InvitationStatus {
    // 联系人邀请信息状态
    NEW_INVITE,// 新邀请
    INVITE_ACCEPT,//接受邀请
    INVITE_ACCEPT_BY_PEER,// 邀请被接受

    // --以下是群组邀请信息状态--

    //收到邀请去加入群
    NEW_GROUP_INVITE,

    //收到申请群加入
    NEW_GROUP_APPLICATION,

    //群邀请已经被对方接受
    GROUP_INVITE_ACCEPTED,

    //群申请已经被批准
    GROUP_APPLICATION_ACCEPTED,

    //接受了群邀请
    GROUP_ACCEPT_INVITE,

    //批准的群加入申请
    GROUP_ACCEPT_APPLICATION,

    //拒绝了群邀请
    GROUP_REJECT_INVITE,

    //拒绝了群申请加入
    GROUP_REJECT_APPLICATION,

    //群邀请被对方拒绝
    GROUP_INVITE_DECLINED,

    //群申请被拒绝
    GROUP_APPLICATION_DECLINED
}
