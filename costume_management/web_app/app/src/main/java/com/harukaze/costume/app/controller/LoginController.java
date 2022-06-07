package com.harukaze.costume.app.controller;

import com.harukaze.costume.app.param.LoginParam;
import com.harukaze.costume.app.service.LoginService;
import com.harukaze.costume.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @PackageName: com.harukaze.costume.app.controller
 * @ClassName: LoginController
 * @Description:
 * @Author: doki
 * @Date: 2022/6/1 17:00
 */
@CrossOrigin
@RequestMapping("app")
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("login/captcha")
    public R getCaptcha() {

        return loginService.getCaptcha();
    }

    @PostMapping("login")
    public R login(@RequestBody LoginParam loginParam) {
        return loginService.login(loginParam);
    }

    @PostMapping("logout")
    public R logout(HttpServletRequest request) {
        System.out.println("hello");
        return loginService.logout(request);
    }
}
