package com.harukaze.costume.common.constant;

/**
 * @PackageName: com.harukaze.common.constant
 * @ClassName: ProductConstant
 * @Description:
 * @Author: doki
 * @Date: 2022/4/19 11:58
 */

public class GoodsWareConstant {

    public enum Status {
        NEW_SPU(0, "新建"),
        SPU_UP(1, "商品上架"),
        SPU_DOWN(2, "商品下架");

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

        public void setMsg(String masg) {
            this.msg = masg;
        }
    }
}
