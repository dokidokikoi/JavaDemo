package com.harukaze.costume.app.config;

/**
 * @PackageName: com.harukaze.costume.app.config
 * @ClassName: MybatisConfig
 * @Description:
 * @Author: doki
 * @Date: 2022/6/1 16:40
 */

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @PackageName: com.harukaze.blog.app.config
 * @ClassName: MybatisConfig
 * @Description:
 * @Author: doki
 * @Date: 2022/5/31 10:39
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.harukaze.costume.app.dao")
public class MybatisConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor;
        paginationInterceptor = new PaginationInterceptor();
//        // 设置请求的页面打于最大页后操作，true调回到首页，false继续请求，默认false
//        paginationInterceptor.setOverflow(true);
//        // 设置最大单页限制数量，默认500条， -1不受限制
//        paginationInterceptor.setLimit(1000);
        return paginationInterceptor;
    }
}

