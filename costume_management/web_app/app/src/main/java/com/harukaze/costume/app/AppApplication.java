package com.harukaze.costume.app;

import com.harukaze.costume.common.utils.RedisUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @PackageName: com.harukaze.app
 * @ClassName: AppApplication
 * @Description:
 * @Author: doki
 * @Date: 2022/5/30 8:56
 */
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
