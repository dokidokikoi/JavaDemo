package com.harukaze.costume.app.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.harukaze.costume.app.entity.CategoryEntity;
import jdk.jfr.Category;
import lombok.Data;

/**
 * @PackageName: com.harukaze.costume.app.vo
 * @ClassName: GoodsVo
 * @Description:
 * @Author: doki
 * @Date: 2022/6/4 14:27
 */
@Data
public class GoodsVo {
    @TableId
    private Long id;
    /**
     * 商品名
     */
    private String name;

    private String cover;
    /**
     * 商品单价
     */
    private Double price;
    /**
     * 创建时间
     */
    private Long createDate;
    /**
     *
     */
    private String creatorName;
    /**
     *
     */
    private CategoryEntity category;

    /**
     *
     */
    private Integer state;

}
