package com.harukaze.costume.app;

import com.harukaze.costume.app.dao.UserDao;
import com.harukaze.costume.app.entity.UserEntity;
import com.harukaze.costume.app.service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @PackageName: com.harukaze.costume.test
 * @ClassName: MybatisTest
 * @Description:
 * @Author: doki
 * @Date: 2022/6/1 16:41
 */
@SpringBootTest
public class MybatisTest {
    @Autowired
    UserDao userDao;

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testUser() {
        UserEntity userEntity = userDao.selectById(1);
        System.out.println(userEntity.toString());
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void testCaptcha() {
        System.out.println(loginService.getCaptcha());
    }

    @Test
    public void testPassword() {
        String sdsdsd = passwordEncoder.encode("123456");
        System.out.println(sdsdsd);
        System.out.println(passwordEncoder.matches("sdsdsd", sdsdsd));
    }
}
