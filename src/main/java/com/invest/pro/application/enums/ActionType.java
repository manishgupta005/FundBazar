package com.invest.pro.application.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/11/2015
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ActionType {

    ADD("ADD"),
    DELETE("DEL"),
    UPDATE("UPD"),
    READ("READ"),
    REMOVE("REM");
    private final String code;

    private ActionType(String code) {
        this.code = code;
    }

    @JsonValue
    String getCode() {
        return code;
    }

    public boolean isAdd() {
        return ADD.getCode().equals(code);
    }

    public boolean isDelete() {
        return DELETE.getCode().equals(code);
    }

    public boolean isUpdate() {
        return UPDATE.getCode().equals(code);
    }

    public boolean isRead() {
        return READ.getCode().equals(code);
    }

    public boolean isRemove() {
        return REMOVE.getCode().equals(code);
    }
}
