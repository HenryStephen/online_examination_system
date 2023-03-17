package com.ssm.online_examination_system.utils;

public enum ResponseCode {
    ERROR(0,"ERROR"),
    SUCCESS(1,"SUCCESS"),
    NEED_LOGIN(2,"NEED_LOGIN"),
    YES(3,"YES"),
    NO(4,"NO"),
    DELETE(5,"DELETE"),
    STOP(6,"STOP"),
    START(7,"START"),
    LOGIN_FAIL(11, "LOGIN_FAIL"),
    LOGIN_SUCCESS(12, "LOGIN_SUCCESS"),
    EXIT(13,"EXIT"),
    FAIL(14,"FAIL");

    public final int code;
    public final String description;

    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
