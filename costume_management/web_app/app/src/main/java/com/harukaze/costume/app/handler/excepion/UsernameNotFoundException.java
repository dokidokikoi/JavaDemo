package com.harukaze.costume.app.handler.excepion;

/**
 * @PackageName: com.harukaze.costume.app.handler.excepion
 * @ClassName: UsernameNotFoundException
 * @Description:
 * @Author: doki
 * @Date: 2022/6/2 10:01
 */

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException() {
        super();
    }

    public UsernameNotFoundException(String message) {
        super(message);
    }

    public UsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
