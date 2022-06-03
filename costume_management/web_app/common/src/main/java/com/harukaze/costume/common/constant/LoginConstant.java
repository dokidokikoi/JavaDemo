package com.harukaze.costume.common.constant;

/**
 * @PackageName: com.harukaze.costume.common.constant
 * @ClassName: LoginConstant
 * @Description:
 * @Author: doki
 * @Date: 2022/6/2 15:53
 */

public class LoginConstant {

    public enum Expire {
        LOGIN_Expire(60 * 60 * 24 * 7),
        CAPTCHA_Expire(60 * 3);

        private long time;

        Expire(long time) {
            this.time = time;
        }

        public long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }
    }
}
