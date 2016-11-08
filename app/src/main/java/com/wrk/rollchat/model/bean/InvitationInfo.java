package com.wrk.rollchat.model.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by MrbigW on 2016/11/2.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 邀请信息的实体类
 * -------------------=.=------------------------
 */


@Entity
public class InvitationInfo {

    @Id
    private String user_hxid;

    private String user_name;

    private String group_hxid;
    private String group_name;

    private String invatePerson;


    private String reason; // 邀请原因

    @Convert(converter = InvitationStatusConverter.class, columnType = String.class)
    private InvitationStatus status; // 邀请的状态

    @Generated(hash = 1270415447)
    public InvitationInfo(String user_hxid, String user_name, String group_hxid,
            String group_name, String invatePerson, String reason, InvitationStatus status) {
        this.user_hxid = user_hxid;
        this.user_name = user_name;
        this.group_hxid = group_hxid;
        this.group_name = group_name;
        this.invatePerson = invatePerson;
        this.reason = reason;
        this.status = status;
    }


    @Generated(hash = 812202610)
    public InvitationInfo() {
    }

    public String getUser_hxid() {
        return this.user_hxid;
    }

    public void setUser_hxid(String user_hxid) {
        this.user_hxid = user_hxid;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getGroup_hxid() {
        return this.group_hxid;
    }

    public void setGroup_hxid(String group_hxid) {
        this.group_hxid = group_hxid;
    }

    public String getGroup_name() {
        return this.group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public InvitationStatus getStatus() {
        return this.status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InvitationInfo{" +
                "user_hxid='" + user_hxid + '\'' +
                ", user_name='" + user_name + '\'' +
                ", group_hxid='" + group_hxid + '\'' +
                ", group_name='" + group_name + '\'' +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                '}';
    }


    public String getInvatePerson() {
        return this.invatePerson;
    }


    public void setInvatePerson(String invatePerson) {
        this.invatePerson = invatePerson;
    }
}
