package com.harukaze.costume.app.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.harukaze.costume.app.entity.UserEntity;
import com.harukaze.costume.app.param.LoginParam;
import com.harukaze.costume.app.service.LoginService;
import com.harukaze.costume.app.service.UserService;
import com.harukaze.costume.app.util.UserThreadLocal;
import com.harukaze.costume.common.constant.LoginConstant;
import com.harukaze.costume.common.utils.JwtUtils;
import com.harukaze.costume.common.utils.R;
import com.harukaze.costume.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * @PackageName: com.harukaze.costume.app.service.impl
 * @ClassName: LoginServiceImpl
 * @Description:
 * @Author: doki
 * @Date: 2022/6/1 17:03
 */

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public R getCaptcha() {
        //生成文字验证码
        String key = UUID.randomUUID().toString();
        String code = defaultKaptcha.createText();
        redisUtil.set("captcha_" + key, code, LoginConstant.Expire.CAPTCHA_Expire.getTime());

        //生成图片验证码
        BufferedImage image = defaultKaptcha.createImage(code);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.ok().put("key", key).put("imgCode", "data:image/jpeg;base64," + Base64.encode(os.toByteArray()));
    }

    @Override
    public R login(LoginParam loginParam) {
        String code = (String) redisUtil.get("captcha_" + loginParam.getKey());
        if (StrUtil.isBlank(code) || !loginParam.getCode().equals(code)) {
            return R.error(HttpStatus.HTTP_BAD_REQUEST, "验证码错误");
        }

        UserEntity user = userService.getOne(
                new LambdaQueryWrapper<UserEntity>()
                        .eq(UserEntity::getAccount, loginParam.getUsername()));
        if (user == null) {
            return R.error(HttpStatus.HTTP_BAD_REQUEST, "用户不存在");
        }

        if (!passwordEncoder.matches(loginParam.getPassword(), user.getPassword())) {
            return R.error(HttpStatus.HTTP_BAD_REQUEST, "密码错误");
        }
        // 生成 token，设置失效时间
        String token = JwtUtils.generateToken(loginParam.getUsername(), LoginConstant.Expire.LOGIN_Expire.getTime());

        // 将 token 存入 redis，设置失效时间
        redisUtil.set("token_" + token, loginParam.getUsername(), LoginConstant.Expire.LOGIN_Expire.getTime());

        // 将验证码信息从 rides 删除
        redisUtil.del("captcha_"+loginParam.getKey());

        return R.ok("登录成功").put("token", token).put("user_info", userService.toVo(user));
    }

    @Override
    public R logout(HttpServletRequest request) {
        System.out.println(request.getHeader(JwtUtils.getHeader()));
        redisUtil.del("token_"+request.getHeader(JwtUtils.getHeader()));
        return R.ok();
    }
}
