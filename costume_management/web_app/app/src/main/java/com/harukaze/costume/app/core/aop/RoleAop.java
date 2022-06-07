package com.harukaze.costume.app.core.aop;

import com.harukaze.costume.app.core.annotation.HasPartRole;
import com.harukaze.costume.app.core.annotation.HasPermission;
import com.harukaze.costume.app.core.annotation.HasRole;
import com.harukaze.costume.app.entity.PermissionEntity;
import com.harukaze.costume.app.entity.RoleEntity;
import com.harukaze.costume.app.entity.UserEntity;
import com.harukaze.costume.app.handler.excepion.NotPermissionException;
import com.harukaze.costume.app.util.UserThreadLocal;
import com.harukaze.costume.app.vo.UserVo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @PackageName: com.harukaze.costume.app.core.aop
 * @ClassName: RoleAop
 * @Description:
 * @Author: doki
 * @Date: 2022/6/4 11:19
 */
@Component
@Aspect
public class RoleAop {

    @Pointcut("@within(com.harukaze.costume.app.core.annotation.HasRole) || @annotation(com.harukaze.costume.app.core.annotation.HasRole)")
    public void full(){}

    @Pointcut("@within(com.harukaze.costume.app.core.annotation.HasRole) || @annotation(com.harukaze.costume.app.core.annotation.HasPartRole)")
    public void part(){}

    @Around("full()")
    public Object full(ProceedingJoinPoint joinPoint) {
        // 角色
        // 1.在 threadlocal 中获取用户角色
        UserVo userVo = UserThreadLocal.get();
        List<String> roles = userVo.getRoles().stream().map(RoleEntity::getRoleName).collect(Collectors.toList());

        // 2.通过 joinpoint 获取被代理类的 HasRole 注解，获取访问所需的角色
        HasRole annotation = joinPoint.getTarget().getClass().getAnnotation(HasRole.class);
        if (annotation == null) {
            annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(HasRole.class);
        }
        String[] needRoles = annotation.value();

        // 3.验证匹配
        boolean flag = Stream.of(needRoles).allMatch(roles::contains);
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
        // 角色
        // 1.在 threadlocal 中获取用户角色
        UserVo userVo = UserThreadLocal.get();
        List<String> roles = userVo.getRoles().stream().map(RoleEntity::getRoleName).collect(Collectors.toList());

        // 2.通过 joinpoint 获取被代理类的 HasRole 注解，获取访问所需的角色
        HasPartRole annotation = joinPoint.getTarget().getClass().getAnnotation(HasPartRole.class);
        if (annotation == null) {
            annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(HasPartRole.class);
        }
        String[] needRoles = annotation.value();

        // 3.验证匹配
        boolean flag = Stream.of(needRoles).allMatch(roles::contains);
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
