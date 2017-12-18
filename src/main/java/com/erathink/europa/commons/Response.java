package com.erathink.europa.commons;

import com.erathink.europa.util.MessageUtils;

import java.text.MessageFormat;
import java.util.regex.Pattern;

/**
 * Created by fengyun on 2017/12/17.
 */
public class Response {

    ErrorInfo error;
    Object data;

    public static Response wrap(Object data) {
        Response res = new Response();
        if (data instanceof ErrorEnum) {
            res.data = null;
            ErrorInfo err = new ErrorInfo((ErrorEnum)data);
            res.error = err;
        }else{
            res.data = data;
            res.error = null;
        }
        return res;
    }

    public static Response wrap(ErrorEnum error,String... errorMsg) {
        Response res = new Response();
        res.data = null;

        ErrorInfo err = new ErrorInfo(error, errorMsg);
        res.error = err;
        return res;
    }
    public ErrorInfo getError() {
        return error;
    }
    public void setError(ErrorInfo error) {
        this.error = error;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

}

class ErrorInfo {
    String code;
    String message;

    public ErrorInfo(ErrorEnum error, String... args){
        if(null != args && args.length > 0){
            for (int i = 0; i < args.length; i++) {
                args[i] = MessageUtils.get(args[i]);
            }
        }
        this.message = replacePattern(MessageFormat.format(error.getMsg(), (Object[])args), "\\{\\d+\\}", "");
        this.code = error.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String replacePattern(final String source, final String regex, final String replacement) {
        return Pattern.compile(regex, Pattern.DOTALL).matcher(source).replaceAll(replacement);
    }
}

