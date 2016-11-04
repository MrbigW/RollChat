package com.wrk.rollchat.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MrbigW on 2016/11/4.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 联系人实体类
 * -------------------=.=------------------------
 */

@Entity
public class ContactInfo {

    @Id
    private String hxid;

    private String name;

    private String nick;

    private String photo;

    private int is_contact;

    @Generated(hash = 111759393)
    public ContactInfo(String hxid, String name, String nick, String photo,
            int is_contact) {
        this.hxid = hxid;
        this.name = name;
        this.nick = nick;
        this.photo = photo;
        this.is_contact = is_contact;
    }

    @Generated(hash = 2019856331)
    public ContactInfo() {
    }

    public String getHxid() {
        return this.hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getIs_contact() {
        return this.is_contact;
    }

    public void setIs_contact(int is_contact) {
        this.is_contact = is_contact;
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "hxid='" + hxid + '\'' +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", photo='" + photo + '\'' +
                ", is_contact=" + is_contact +
                '}';
    }
}
