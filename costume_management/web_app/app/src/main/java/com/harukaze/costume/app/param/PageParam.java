package com.harukaze.costume.app.param;

import lombok.Data;

/**
 * @PackageName: com.harukaze.costume.app.param
 * @ClassName: PageParam
 * @Description:
 * @Author: doki
 * @Date: 2022/6/6 12:09
 */

@Data
public class PageParam {
    private String key;
    private String limit;
    private String page;
}
