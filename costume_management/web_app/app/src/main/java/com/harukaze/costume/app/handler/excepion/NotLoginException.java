package com.harukaze.costume.app.handler.excepion;

/**
 * @PackageName: com.harukaze.costume.app.handler.excepion
 * @ClassName: NotLoginException
 * @Description:
 * @Author: doki
 * @Date: 2022/6/2 9:38
 */

public class NotLoginException extends RuntimeException {
    public NotLoginException() {
        super();
    }

    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
