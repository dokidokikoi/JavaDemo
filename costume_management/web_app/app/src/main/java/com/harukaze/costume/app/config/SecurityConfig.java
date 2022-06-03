package com.harukaze.costume.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @PackageName: com.harukaze.costume.app.config
 * @ClassName: SecurityConfig
 * @Description:
 * @Author: doki
 * @Date: 2022/6/2 11:03
 */

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
