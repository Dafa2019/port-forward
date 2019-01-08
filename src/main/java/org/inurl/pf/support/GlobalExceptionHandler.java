package org.inurl.pf.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inurl.pf.model.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

/**
 * 全局异常捕获
 * @author raylax
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ApiResult handleServiceException(ServiceException e) {
        logger.warn("服务异常", e);
        return ApiResult.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResult handleSystemException(Exception e) {
        logger.error("系统异常", e);
        return ApiResult.fail("系统异常，请稍后再试！");
    }


}