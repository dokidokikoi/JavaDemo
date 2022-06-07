package com.harukaze.costume.app.handler.excepion;

/**
 * @PackageName: com.harukaze.costume.app.handler.excepion
 * @ClassName: NotPermissionException
 * @Description:
 * @Author: doki
 * @Date: 2022/6/4 11:14
 */

public class NotPermissionException extends RuntimeException {
    public NotPermissionException() {
        super();
    }

    public NotPermissionException(String message) {
        super(message);
    }

    public NotPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
