package com.harukaze.costume.app.util;

import com.harukaze.costume.app.vo.UserVo;

/**
 * @PackageName: com.harukaze.front.util
 * @ClassName: UserThreadLocal
 * @Description:
 * @Author: doki
 * @Date: 27/11/2021 7:31 PM
 */
public class UserThreadLocal {
    // 存储用户数据，一次请求结束后删除
    private static final ThreadLocal<UserVo> LOCAL = new ThreadLocal<>();

    private UserThreadLocal() {
    }

    public static UserVo get() {
        return LOCAL.get();
    }

    public static void set(UserVo userVo) {
        LOCAL.set(userVo);
    }

    public static void remove() {
        LOCAL.remove();
    }

}
