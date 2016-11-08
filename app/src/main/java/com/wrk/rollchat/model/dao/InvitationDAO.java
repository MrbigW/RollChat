package com.wrk.rollchat.model.dao;

import com.wrk.rollchat.greenDAO.InvitationInfoDao;
import com.wrk.rollchat.model.bean.InvitationInfo;
import com.wrk.rollchat.model.bean.InvitationStatus;
import com.wrk.rollchat.model.db.DBManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrbigW on 2016/11/4.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 邀请信息操作类
 * -------------------=.=------------------------
 */

public class InvitationDAO {

    private InvitationInfoDao mInvitationInfoDao;

    public InvitationDAO(DBManager dbManager) {
        mInvitationInfoDao = dbManager.getInvitationInfoDao();
    }

    // 添加邀请
    public void addInvitation(InvitationInfo invitationInfo) {
        InvitationInfo info = new InvitationInfo();

        info.setReason(invitationInfo.getReason());
        info.setStatus(invitationInfo.getStatus());

        if (invitationInfo.getUser_hxid() != null) {
            info.setUser_hxid(invitationInfo.getUser_hxid());
            info.setUser_name(invitationInfo.getUser_name());
        } else {
            info.setGroup_hxid(invitationInfo.getGroup_hxid());
            info.setGroup_name(invitationInfo.getGroup_name());
            info.setUser_hxid(invitationInfo.getInvatePerson());
        }
        mInvitationInfoDao.insertOrReplace(info);
    }

    //获取所有邀请信息
    public List<InvitationInfo> getInvitations() {
        List<InvitationInfo> invitationInfos = new ArrayList<>();
        QueryBuilder<InvitationInfo> queryBuilder = mInvitationInfoDao.queryBuilder();
        if (queryBuilder.list().size() <= 0) {
            return null;
        } else {
            for (int i = 0; i < queryBuilder.list().size(); i++) {
                InvitationInfo invitationInfo = new InvitationInfo();
                InvitationInfo tmp = queryBuilder.list().get(i);
                invitationInfo.setReason(tmp.getReason());
                invitationInfo.setStatus(tmp.getStatus());

                String groupId = tmp.getGroup_hxid();
                if (groupId == null) { // 联系人
                    invitationInfo.setUser_hxid(tmp.getUser_hxid());
                    invitationInfo.setUser_name(tmp.getUser_name());
                } else {
                    invitationInfo.setGroup_hxid(tmp.getGroup_hxid());
                    invitationInfo.setGroup_name(tmp.getGroup_name());
                    invitationInfo.setInvatePerson(tmp.getInvatePerson());
                }

                invitationInfos.add(invitationInfo);
            }
        }
        return invitationInfos;
    }

    public void removeInvitation(String hxId) {
        if (hxId == null) {
            return;
        }
        mInvitationInfoDao.deleteByKey(hxId);
    }

    /**
     * 更新邀请状态
     *
     * @param invitationStatus
     * @param hxid
     */
    public void updateInvitationStatus(InvitationStatus invitationStatus, String hxid) {
        if (hxid == null) {
            return;
        }
        InvitationInfo info = null;
        QueryBuilder<InvitationInfo> queryBuilder = mInvitationInfoDao.queryBuilder();
        queryBuilder.where(InvitationInfoDao.Properties.User_hxid.eq(hxid));
        List<InvitationInfo> list = queryBuilder.list();
        if (list != null && list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                info = list.get(i);
                info.setStatus(invitationStatus);
            }
        } else {
            return;
        }

        mInvitationInfoDao.update(info);

    }


}





























