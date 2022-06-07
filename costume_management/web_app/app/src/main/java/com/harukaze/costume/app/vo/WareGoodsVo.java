package com.harukaze.costume.app.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.harukaze.costume.app.entity.WareEntity;
import lombok.Data;

/**
 * @PackageName: com.harukaze.costume.app.vo
 * @ClassName: WareGoodsVo
 * @Description:
 * @Author: doki
 * @Date: 2022/6/5 13:21
 */
@Data
public class WareGoodsVo {
    @TableId
    private Long id;
    /**
     *
     */
    private Integer amount;
    /**
     *
     */
    private Long createDate;
    /**
     *
     */
    private Long updateDate;
    /**
     *
     */
    private WareEntity ware;
    /**
     *
     */
    private GoodsVo goods;
}
