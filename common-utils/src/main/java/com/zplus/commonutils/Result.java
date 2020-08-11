package com.zplus.commonutils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable
{

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    private boolean success = true;

    /**
     * 返回处理消息
     */
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    private T data;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public Result() {

    }

    public Result<T> success(String message) {
        this.message = message;
        this.code = ResponseEnum.SUCCESS.getCode();
        this.success = true;
        return this;
    }


    public static Result<Object> ok() {
        Result<Object> r = new Result<Object>();
        r.setSuccess(true);
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage("成功");
        return r;
    }

    public static Result<Object> ok(String msg) {
        Result<Object> r = new Result<Object>();
        r.setSuccess(true);
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage(msg);
        return r;
    }

    public static Result<Object> ok(Object data) {
        Result<Object> r = new Result<Object>();
        r.setSuccess(true);
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setData(data);
        return r;
    }

    public static Result<Object> error(String msg) {
        return error(ResponseEnum.UNKNOWN_ERROR.getCode(), msg);
    }

    public static Result<Object> error(int code, String msg) {
        Result<Object> r = new Result<Object>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    public static Result<Object> error(ResponseEnum responseEnum)
    {
        return error(responseEnum.getCode(),responseEnum.getMsg());
    }

    /**
     * 无权限访问返回结果
     */
    public static Result<Object> noauth(String msg) {
        return error(ResponseEnum.AUTH_ERROR.getCode(), msg);
    }
}