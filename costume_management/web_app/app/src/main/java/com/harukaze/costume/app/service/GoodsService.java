package com.harukaze.costume.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.harukaze.costume.app.param.GoodsParam;
import com.harukaze.costume.app.vo.GoodsVo;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.app.entity.GoodsEntity;

import java.util.Map;

/**
 * 
 *
 * @author doki
 * @email harukaze_doki@163.com
 * @date 2022-06-01 16:36:17
 */
public interface GoodsService extends IService<GoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils listGoodsPage(GoodsParam params);

    GoodsVo getGoodsById(Long id);

    void setStateByIds(Long id, Integer state) throws Exception;

    void saveGoods(GoodsEntity goods) throws Exception;

    void updateGoodsById(GoodsEntity goods);
}

