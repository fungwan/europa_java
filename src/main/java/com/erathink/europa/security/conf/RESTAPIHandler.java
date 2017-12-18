package com.erathink.europa.security.conf;

import com.erathink.europa.commons.ErrorEnum;
import com.erathink.europa.commons.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by fengyun on 2017/12/17.
 */
@ControllerAdvice
public class RESTAPIHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorEnum error = ErrorEnum.COMM_PARAM_ERROR;
        error.setMsg("请求内容格式" + ex.getContentType().toString() + "不支持，目前仅支持application/json格式。");
        Response response = Response.wrap(error);
        return handleExceptionInternal(ex, response, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorEnum error = ErrorEnum.COMM_PARAM_ERROR;
        error.setMsg("请求内容不是正确的json，请检查。");
        Response response = Response.wrap(error);
        return handleExceptionInternal(ex, response, headers, status, request);
    }
}
