package com.wrk.rollchat.model.dao;

import android.content.Context;

import com.wrk.rollchat.model.db.DBManager;

/**
 * Created by MrbigW on 2016/11/4.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class InvitationContactManager {

    private final ContactDAO mContactDAO;
    private final InvitationDAO mInvitationDAO;
    private DBManager mDBManager;

    public InvitationContactManager(Context context, String name) {
        mDBManager = new DBManager(context, name);
        mContactDAO = new ContactDAO(mDBManager);
        mInvitationDAO = new InvitationDAO(mDBManager);
    }

    public ContactDAO getContactDAO() {
        return mContactDAO;
    }

    public InvitationDAO getInvitationDAO() {
        return mInvitationDAO;
    }

    public void close() {
        mDBManager.close();
    }

}
