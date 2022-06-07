package com.harukaze.costume.app.controller;

import com.harukaze.costume.app.param.GoodsParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PackageName: com.harukaze.costume.app.controller
 * @ClassName: TestContoller
 * @Description:
 * @Author: doki
 * @Date: 2022/6/6 14:01
 */

@RestController
public class TestController {

    @GetMapping("test")
    public String test(GoodsParam goodsParam) {
        return "hello";
    }
}
