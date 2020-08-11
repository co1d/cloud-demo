package com.zplus.commonutils;

public enum ResponseEnum
{
    SUCCESS(20000,"操作成功"),
    USER_NOT_FOUND_ERROR(50001,"用户不存在"),
    USER_LOGIN_FAIL_ERROR(50002,"用户名或密码不正确"),
    USER_LOGIN_EXPIRE_ERROR(50003,"登录过期"),
    TOKEN_AUTH_ERROR(40001,"Token验证失败"),
    AUTH_ERROR(40011,"权限错误"),
    UNKNOWN_ERROR(44444,"未知错误");

    private int code;
    private String msg;

    ResponseEnum(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public int getCode()
    {
        return code;
    }

    public String getMsg()
    {
        return msg;
    }

}
