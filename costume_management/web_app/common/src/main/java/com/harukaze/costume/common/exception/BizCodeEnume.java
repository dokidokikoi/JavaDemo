package com.harukaze.costume.common.exception;

/**
 * @PackageName: com.harukaze.common.exception
 * @ClassName: BizCodeEnume
 * @Description: 错误码列表：
 *      10：通用
 *          001：参数格式校验
 *      11：商品
 *      12：订单
 *      13：购物车
 *      14：物流
 * @Author: doki
 * @Date: 2022/4/17 18:52
 */

public enum BizCodeEnume {
    VALID_EXCEPTION(10001, "参数格式校验异常"),
    UNKNOW_EXCEPTION(10000, "系统未知异常"),
    PRODUCT_UP_EXCEPTION(110000, "商品上架异常");

    private int code;
    private String msg;

    BizCodeEnume(int code, String msg) {
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
