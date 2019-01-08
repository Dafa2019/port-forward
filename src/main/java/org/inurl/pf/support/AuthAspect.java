package org.inurl.pf.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author raylax
 */
@Component
@Aspect
public class AuthAspect {

    private final AuthProperties authProperties;

    private final static String HEADER = "X-PF-AUTH";

    @Autowired
    public AuthAspect(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    @Around("@annotation(org.inurl.pf.support.Auth)")
    public Object auth(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = getRequest();
        if (request == null) {
            return joinPoint.proceed(args);
        }
        String authCode = request.getHeader(HEADER);
        if (authCode != null && authCode.equals(authProperties.getCode())) {
            return joinPoint.proceed(args);
        }
        throw new ServiceException("AuthCode校验失败");
    }

    private HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) attributes).getRequest();
    }

}
