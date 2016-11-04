package com.wrk.rollchat.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MrbigW on 2016/11/3.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 用户实体类
 * -------------------=.=------------------------
 */


@Entity
public class UserInfo {

    @Id
    @Property(nameInDb = "hxid")
    private String hxid;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "nick")
    private String nick;
    @Property(nameInDb = "photo")
    private String photo;

    public UserInfo() {
    }


    public UserInfo(String name) {
        this.hxid = name;
        this.name = name;
        this.nick = name;

    }


    @Generated(hash = 1883516978)
    public UserInfo(String hxid, String name, String nick, String photo) {
        this.hxid = hxid;
        this.name = name;
        this.nick = nick;
        this.photo = photo;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "hxid='" + hxid + '\'' +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
