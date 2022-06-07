package com.harukaze.costume.app.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.harukaze.costume.app.entity.UserEntity;
import com.harukaze.costume.app.handler.excepion.NotLoginException;
import com.harukaze.costume.app.handler.excepion.UsernameNotFoundException;
import com.harukaze.costume.app.service.UserService;
import com.harukaze.costume.app.util.UserThreadLocal;
import com.harukaze.costume.app.vo.UserVo;
import com.harukaze.costume.common.constant.ResponseStatus;
import com.harukaze.costume.common.constant.UserConstant;
import com.harukaze.costume.common.utils.JwtUtils;
import com.harukaze.costume.common.utils.R;
import com.harukaze.costume.common.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @PackageName: com.harukaze.costume.app.interceptor
 * @ClassName: JwtInterceptor
 * @Description:
 * @Author: doki
 * @Date: 2022/6/2 9:03
 */

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String token = request.getHeader(JwtUtils.getHeader());
//        token = token.substring(1,token.length()-1);
        R r = null;
        if(token != null){
            Claims claim = JwtUtils.getClaimByToken(token);
            try {
                if (claim != null && !JwtUtils.isTokenExpired(claim)) {
                    String username = claim.getSubject();
                    // 用 token 从 redis 取出 username，查询数据库，将用户信息存入 LocalThread
                    String redisUsername = (String) redisUtil.get("token_" + token);
                    if (!StrUtil.isBlank(username) || !StrUtil.isBlank(redisUsername)) {
                        UserEntity user = userService.getOne(
                                new LambdaQueryWrapper<UserEntity>()
                                        .eq(UserEntity::getAccount, username));
                        if (user != null && user.getState() == UserConstant.Status.USER_UP.getCode()) {
                            UserVo userVo = userService.toVo(user);
                            UserThreadLocal.set(userVo);
                            return true;
                        } else {
                            r = R.error(ResponseStatus.USER_NOT_FIND.getCode(), ResponseStatus.USER_NOT_FIND.getMsg());
                        }
                    } else {
                        r = R.error(ResponseStatus.LOGIN_EXPIRE.getCode(), ResponseStatus.LOGIN_EXPIRE.getMsg());
                    }
                } else {
                    r = R.error(ResponseStatus.LOGIN_EXPIRE.getCode(), ResponseStatus.LOGIN_EXPIRE.getMsg());
                }
            } catch (Exception e) {
                log.error("token 解析错误，error:{}", e.getClass());
                r = R.error(ResponseStatus.LOGIN_EXPIRE.getCode(), ResponseStatus.LOGIN_EXPIRE.getMsg());
                response.getWriter().write(JSONUtil.toJsonStr(r));
                return false;
            }
        } else {
            r = R.error(ResponseStatus.USER_NOT_FIND.getCode(), ResponseStatus.USER_NOT_FIND.getMsg());
        }
        response.getWriter().write(JSONUtil.toJsonStr(r));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserThreadLocal.remove();
        System.out.println(UserThreadLocal.get());
    }
}
