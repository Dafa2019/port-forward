package org.inurl.pf.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class AuthAspect {

    private final HttpServletRequest request;

    private final AuthProperties authProperties;

    private final String HEADER = "X-PF-AUTH";

    @Autowired
    public AuthAspect(HttpServletRequest request, AuthProperties authProperties) {
        this.request = request;
        this.authProperties = authProperties;
    }

    @Around("@annotation(org.inurl.pf.support.Auth)")
    public Object auth(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (request == null) return joinPoint.proceed(args);
        String authCode = request.getHeader(HEADER);
        if (authCode != null && authCode.equals(authProperties.getCode())) {
            return joinPoint.proceed(args);
        }
        throw new ServiceException("AuthCode校验失败");
    }

}
