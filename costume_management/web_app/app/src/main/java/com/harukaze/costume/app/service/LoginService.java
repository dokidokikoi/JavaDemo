package com.harukaze.costume.app.service;

import com.harukaze.costume.app.param.LoginParam;
import com.harukaze.costume.common.utils.R;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    R getCaptcha();

    R login(LoginParam loginParam);

    R logout(HttpServletRequest request);
}
