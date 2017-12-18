package com.erathink.europa.commons;

import com.erathink.europa.util.MessageUtils;

/**
 * Created by fengyun on 2017/12/17.
 */

public class CBCException extends RuntimeException {

    public static final String Y = "Y";
    public static final String N = "N";

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param code
     *            ErrorEnum.XXX.getCode()
     * @param showMessage
     *            返回不同的error.message<br>
     *            true:返回ErrorEnum.XXX.getMessage(); <br>
     *            false:返回ErrorEnum.COMM_SYSTEM_UNKNOWN:未知错误，请联系管理员
     */
    public CBCException(String code, boolean showMessage) {
        super(code, new Throwable(showMessage ? Y : N));
    }

    /**
     *
     * @param code
     *            ErrorEnum.XXX.getCode()
     * @param param
     *            ErrorEnum.XXX.getMsg()中定义了参数，则使用此方法传递
     */
    public CBCException(String code, String param) {
        super(code, new Throwable(MessageUtils.get(param)));
    }

    /**
     *
     * @param code
     *            ErrorEnum.XXX.getCode()
     * @param param
     *            ErrorEnum.XXX.getMsg()中定义了参数，则使用此方法传递
     * @param args key中带的参数，一般由程序中业务传入，如业务id等
     */
    public CBCException(String code, String key, String... args) {
        super(code, new Throwable(MessageUtils.get(key, args)));
    }

    /**
     *
     * @param code
     *            ErrorEnum.XXX.getCode()
     * @param showMessage
     *            返回的error.message为ErrorEnum.COMM_SYSTEM_UNKNOWN:未知错误，请联系管理员
     */
    public CBCException(String code) {
        super(code);
    }

}
