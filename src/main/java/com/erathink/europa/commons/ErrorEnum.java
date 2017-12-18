package com.erathink.europa.commons;

/**
 * Created by fengyun on 2017/12/17.
 */
public enum ErrorEnum {
    COMM_SYSTEM_UNKNOWN("COMM_SYSTEM_UNKNOWN","未知错误，请联系管理员"),
    COMM_BIZ_NOT_EXISTS("COMM_BIZ_NOT_EXISTS","{0}不存在，请检查"),
    COMM_BIZ_EXISTS("COMM_BIZ_EXISTS","{0}已存在，请使用其它{0}"),
    COMM_PARAM_ERROR("COMM_PARAM_ERROR","请求参数{0}错误，请检查"),
    COMM_NOT_LOGIN("COMM_NOT_LOGIN","当前登录已过期，请重新登录"),
    COMM_ILLEGAL("COMM_ILLEGA","{0}不合法"),

    /**
     * COMM_BIZ_ERROR 用于某些一次性的 特殊的业务逻辑错误的定义
     * 最好是在rest接口中return这个错误 这样易于被前端展示或者在日志中追踪
     * 如果直接在service层中throw这个异常可能会導致后期追踪错误变得困难
     */
    COMM_BIZ_ERROR("COMM_BIZ_ERROR","{0}");

    // property
    private String code;
    private String msg;
    // construct
    private ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setCode(String code){
        this.code = code;
    }
}
