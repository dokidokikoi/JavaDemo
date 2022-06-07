package com.harukaze.costume.app.vo;

import lombok.Data;

/**
 * @PackageName: com.harukaze.costume.app.vo
 * @ClassName: WareVo
 * @Description:
 * @Author: doki
 * @Date: 2022/6/5 16:17
 */
@Data
public class WareVo {
    private Long id;
    /**
     * 地址
     */
    private String address;
    /**
     * 创建时间
     */
    private Long createDate;
    /**
     *
     */
    private String manager;
}
