package com.erathink.europa.commons;

/**
 * Created by fengyun on 2017/12/17.
 */
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ErrorHandling {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandling.class);

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object handleError(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (RuntimeException e) {
            Throwable cause = e;
            if (cause instanceof CBCException) {
                LOGGER.debug("Caught CBCException with code {}", cause.getMessage());
                ErrorEnum error = null;
                try {
                    error = Enum.valueOf(ErrorEnum.class, cause.getMessage());
                } catch (IllegalArgumentException ex) {
                    // sms error only for now
                    LOGGER.debug("Error code not defined, code is: {}", cause.getMessage());
                    error = ErrorEnum.COMM_SYSTEM_UNKNOWN;
                    error.setCode(cause.getMessage());
                    return Response.wrap(error);
                }
                String showMessage = null;
                if (null != cause.getCause()) {
                    showMessage = cause.getCause().getMessage();
                    // for other cbc exception, display the message in ErrorEnum
                    if (CBCException.Y.equals(showMessage)) {
                        return Response.wrap(error);
                    }
                    // exception with parameter
                    if (!CBCException.N.equals(showMessage)) {
                        return Response.wrap(error, showMessage);
                    }
                }
                // for other cbc exception, do not display message, use unknown
                // instead
                error.setMsg(ErrorEnum.COMM_SYSTEM_UNKNOWN.getMsg());
                return Response.wrap(error);
            }
            LOGGER.error("Caught undefined error.", e);
            return Response.wrap(ErrorEnum.COMM_SYSTEM_UNKNOWN);
        }
    }
}
