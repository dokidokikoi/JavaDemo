package com.harukaze.costume.app.param;

import lombok.Data;

/**
 * @PackageName: com.harukaze.costume.app.param
 * @ClassName: loginParam
 * @Description:
 * @Author: doki
 * @Date: 2022/6/2 10:49
 */
@Data
public class LoginParam {
    private String username;
    private String password;
    private String key;
    private String code;
}
