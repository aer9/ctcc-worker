package com.moxie.cloud.services.server.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2018/12/21
 */

@Aspect
@Component
public class HttpAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);


    @Pointcut("execution(public * com.moxie.cloud.services.server.controller.TaskController.*(..))")
    public void log() {
        System.out.println("--------------------");
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        LOGGER.info("url = " + request.getRequestURL().toString());
        LOGGER.info("ip = " + request.getRemoteAddr());
        LOGGER.info("method = " + request.getMethod());
        LOGGER.info("class_method = " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("args = " + Arrays.toString(joinPoint.getArgs()));
    }
}
