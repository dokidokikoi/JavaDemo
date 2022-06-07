package com.harukaze.costume.common.constant;

/**
 * @PackageName: com.harukaze.costume.common.constant
 * @ClassName: UserConstant
 * @Description:
 * @Author: doki
 * @Date: 2022/6/2 8:39
 */

public class UserConstant {

    public enum Status {
        USER_UP(0, "用户激活"),
        USER_DOWN(1, "用户冻结");

        private int code;
        private String msg;

        Status(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
