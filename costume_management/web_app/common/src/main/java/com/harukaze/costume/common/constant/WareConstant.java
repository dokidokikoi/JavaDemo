package com.harukaze.costume.common.constant;

/**
 * @PackageName: com.harukaze.common.constant
 * @ClassName: WareConstant
 * @Description:
 * @Author: doki
 * @Date: 2022/4/20 22:37
 */

public class WareConstant {

    public enum PurchaseStatusEnum {
        /**
         *
         */
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        RECEIVE(2, "已领取"),
        FINISH(3, "已完成"),
        HASERROE(4,"有异常");

        private int code;
        private String msg;

        PurchaseStatusEnum(int code, String msg) {
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

    public enum PurchaseDetailStatusEnum {
        /**
         *
         */
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        RECEIVE(2, "正在采购"),
        FINISH(3, "已完成"),
        HASERROE(4,"采购失败");

        private int code;
        private String msg;

        PurchaseDetailStatusEnum(int code, String msg) {
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
