package com.harukaze.costume.app.handler;

import com.harukaze.costume.app.handler.excepion.NotLoginException;
import com.harukaze.costume.common.utils.R;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @PackageName: com.harukaze.costume.app.handler
 * @ClassName: ExceptionHandler
 * @Description:
 * @Author: doki
 * @Date: 2022/6/2 9:34
 */
//@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        return R.error(HttpStatus.SC_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public R notLoginHandler(NotLoginException e, HttpServletResponse resp) {
        resp.setStatus(HttpStatus.SC_FORBIDDEN);
        return R.error(HttpStatus.SC_FORBIDDEN, e.getMessage());
    }
}
