package com.wrk.rollchat.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MrbigW on 2016/11/2.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 群信息的bean类
 * -------------------=.=------------------------
 */

@Entity
public class GroupInfo {

    private String groupName; // 群名称
    private String groupId; // 群Id
    private String invatePerson; // 邀请人



    @Generated(hash = 308264931)
    public GroupInfo(String groupName, String groupId, String invatePerson) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.invatePerson = invatePerson;
    }



    @Generated(hash = 1250265142)
    public GroupInfo() {
    }



    @Override
    public String toString() {
        return "GroupInfo{" +
                "groupName='" + groupName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", invatePerson='" + invatePerson + '\'' +
                '}';
    }



    public String getGroupName() {
        return this.groupName;
    }



    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }



    public String getGroupId() {
        return this.groupId;
    }



    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }



    public String getInvatePerson() {
        return this.invatePerson;
    }



    public void setInvatePerson(String invatePerson) {
        this.invatePerson = invatePerson;
    }
}
