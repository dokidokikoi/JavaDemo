package com.harukaze.costume.app.core.aop;

import com.harukaze.costume.app.core.annotation.HasPartPermission;
import com.harukaze.costume.app.core.annotation.HasPermission;
import com.harukaze.costume.app.entity.PermissionEntity;
import com.harukaze.costume.app.entity.RoleEntity;
import com.harukaze.costume.app.entity.UserEntity;
import com.harukaze.costume.app.handler.excepion.NotPermissionException;
import com.harukaze.costume.app.util.UserThreadLocal;
import com.harukaze.costume.app.vo.UserVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @PackageName: com.harukaze.costume.app.core.aop
 * @ClassName: PermissionAop
 * @Description:
 * @Author: doki
 * @Date: 2022/6/4 7:51
 */
@Aspect
@Component
public class PermissionAop {

    @Pointcut("@annotation(com.harukaze.costume.app.core.annotation.HasPermission)")
    public void full(){}

    @Pointcut("@annotation(com.harukaze.costume.app.core.annotation.HasPartPermission)")
    public void part(){}

    @Around("full()")
    public Object full(ProceedingJoinPoint joinPoint) {
        // 鉴权
        // 1.在 threadlocal 中获取用户权限
        UserVo userVo = UserThreadLocal.get();
        List<String> permission = userVo.getPermissions().stream().map(PermissionEntity::getName).collect(Collectors.toList());

        // 2.通过 joinpoint 获取被代理方法的 HasPermission 注解，获取访问所需的权限
        HasPermission annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(HasPermission.class);
        String[] needPermissions = annotation.value();

        // 3.验证匹配
        boolean flag = Stream.of(needPermissions).allMatch(permission::contains);
        if (!flag) {
            throw new NotPermissionException("权限不足，请联系管理员");
        }

        // 4.调用方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }

    @Around("part()")
    public Object part(ProceedingJoinPoint joinPoint) {
        // 鉴权
        // 1.在 threadlocal 中获取用户权限
        UserVo userVo = UserThreadLocal.get();
        List<String> permission = userVo.getPermissions().stream().map(PermissionEntity::getName).collect(Collectors.toList());

        // 2.通过 joinpoint 获取被代理方法的 HasPermission 注解，获取访问所需的权限
        HasPartPermission annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(HasPartPermission.class);
        String[] needPermissions = annotation.value();

        // 3.验证匹配
        boolean flag = Stream.of(needPermissions).allMatch(permission::contains);
        if (!flag) {
            UserEntity args = (UserEntity) joinPoint.getArgs()[0];
            if (UserThreadLocal.get().getId() != args.getId()) {
                throw new NotPermissionException("权限不足，请联系管理员");
            }
        }

        // 4.调用方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }
}
