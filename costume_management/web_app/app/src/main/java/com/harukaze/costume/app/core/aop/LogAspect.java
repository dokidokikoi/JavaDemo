package com.harukaze.costume.app.core.aop;

import com.harukaze.costume.app.service.ThreadService;
import com.harukaze.costume.app.util.HttpContextUtils;
import com.harukaze.costume.app.util.UserThreadLocal;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @PackageName: com.harukaze.blog.app.core.aop
 * @ClassName: LogAop
 * @Description:
 * @Author: doki
 * @Date: 2022/6/9 19:52
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private ThreadService threadService;

    @Pointcut("@annotation(com.harukaze.costume.app.core.annotation.LogAnnotation)")
    public void pc(){}

    @Around("pc()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long time = System.currentTimeMillis() - beginTime;
        threadService.recordLog(joinPoint, time, HttpContextUtils.getHttpServletRequest(), UserThreadLocal.get());
        return result;
    }
}
