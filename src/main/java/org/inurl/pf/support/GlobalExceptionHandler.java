package org.inurl.pf.support;

import org.inurl.pf.model.ApiResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ApiResult handleRouteError(ServiceException e) {
        return ApiResult.fail(e.getMessage());
    }


}