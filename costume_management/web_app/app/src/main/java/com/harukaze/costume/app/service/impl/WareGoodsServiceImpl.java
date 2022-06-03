package com.harukaze.costume.app.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.common.utils.Query;

import com.harukaze.costume.app.dao.WareGoodsDao;
import com.harukaze.costume.app.entity.WareGoodsEntity;
import com.harukaze.costume.app.service.WareGoodsService;


@Service("wareGoodsService")
public class WareGoodsServiceImpl extends ServiceImpl<WareGoodsDao, WareGoodsEntity> implements WareGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareGoodsEntity> page = this.page(
                new Query<WareGoodsEntity>().getPage(params),
                new QueryWrapper<WareGoodsEntity>()
        );

        return new PageUtils(page);
    }

}