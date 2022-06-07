package com.harukaze.costume.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.harukaze.costume.app.vo.WareGoodsVo;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.app.entity.WareGoodsEntity;

import java.util.Map;

/**
 * 
 *
 * @author doki
 * @email harukaze_doki@163.com
 * @date 2022-06-01 16:36:17
 */
public interface WareGoodsService extends IService<WareGoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryWareGoodsPage(Map<String, Object> params);

    WareGoodsVo getWareGoodsById(Long id);

    void saveWareGoods(WareGoodsEntity wareGoods);

    void updateWareGoodsById(WareGoodsEntity wareGoods);

    void updateWareGoodsById(Map<String, Object> params);
}

