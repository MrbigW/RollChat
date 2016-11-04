package com.wrk.rollchat.model.dao;

import com.wrk.rollchat.greenDAO.InvitationInfoDao;
import com.wrk.rollchat.model.db.DBManager;

/**
 * Created by MrbigW on 2016/11/4.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class InvitationDAO {

    private InvitationInfoDao mInvitationInfoDao;

    public InvitationDAO(DBManager dbManager) {
        mInvitationInfoDao = dbManager.getInvitationInfoDao();
    }
}
