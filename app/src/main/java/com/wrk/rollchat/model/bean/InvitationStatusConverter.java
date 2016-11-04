package com.wrk.rollchat.model.bean;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by MrbigW on 2016/11/4.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 枚举类转String
 * -------------------=.=------------------------
 */

public class InvitationStatusConverter implements PropertyConverter<InvitationStatus, String> {
    @Override
    public InvitationStatus convertToEntityProperty(String databaseValue) {
        return InvitationStatus.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(InvitationStatus entityProperty) {
        return entityProperty.name();
    }
}
