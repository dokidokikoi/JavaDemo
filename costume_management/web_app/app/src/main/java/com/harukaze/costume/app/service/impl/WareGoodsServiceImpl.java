package com.harukaze.costume.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harukaze.costume.app.entity.WareEntity;
import com.harukaze.costume.app.service.GoodsService;
import com.harukaze.costume.app.service.WareService;
import com.harukaze.costume.app.vo.GoodsVo;
import com.harukaze.costume.app.vo.WareGoodsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.common.utils.Query;

import com.harukaze.costume.app.dao.WareGoodsDao;
import com.harukaze.costume.app.entity.WareGoodsEntity;
import com.harukaze.costume.app.service.WareGoodsService;
import org.springframework.web.bind.annotation.RequestParam;


@Service("wareGoodsService")
public class WareGoodsServiceImpl extends ServiceImpl<WareGoodsDao, WareGoodsEntity> implements WareGoodsService {

    @Autowired
    private WareService wareService;

    @Autowired
    private GoodsService goodsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareGoodsEntity> page = this.page(
                new Query<WareGoodsEntity>().getPage(params),
                new QueryWrapper<WareGoodsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryWareGoodsPage(Map<String, Object> params) {

        String key = (String) params.get("key");
        String wareAddress = (String) params.get("ware");
        String orderBy = (String) params.get("orderBy");
        Integer currt = Integer.parseInt((String) params.getOrDefault("page", "1"));
        Integer limit = Integer.parseInt((String) params.getOrDefault("limit", "10"));

        Page<WareGoodsEntity> page = new Page<>(currt, limit);
        IPage<WareGoodsEntity> IPage = this.baseMapper.selectWareGoodsByPage(page,
                key,
                wareAddress,
                orderBy);

        PageUtils pageUtils = new PageUtils(IPage);
        List<WareGoodsVo> collect = pageUtils.getList().stream()
                .map(item -> toVo((WareGoodsEntity) item))
                .collect(Collectors.toList());

        pageUtils.setList(collect);

        return pageUtils;
    }

    @Override
    public WareGoodsVo getWareGoodsById(Long id) {
        return toVo(this.baseMapper.selectById(id));
    }

    @Override
    public void saveWareGoods(WareGoodsEntity wareGoods) {
        WareGoodsEntity wareGoodsEntity = new WareGoodsEntity();
        wareGoodsEntity.setCreateDate(System.currentTimeMillis());
        wareGoodsEntity.setGoodsId(wareGoods.getGoodsId());
        wareGoodsEntity.setWareId(wareGoods.getWareId());
        wareGoodsEntity.setAmount(0);
        if (wareGoods.getAmount() != null && wareGoods.getAmount() > 0) {
            wareGoodsEntity.setAmount(wareGoods.getAmount());
        }

        WareGoodsEntity list = this.baseMapper.selectOne(new LambdaQueryWrapper<WareGoodsEntity>()
                .eq(WareGoodsEntity::getWareId, wareGoodsEntity.getWareId())
                .eq(WareGoodsEntity::getGoodsId, wareGoodsEntity.getGoodsId()));

        if (list != null) {
            return;
        }

        this.save(wareGoodsEntity);
    }

    @Override
    public void updateWareGoodsById(WareGoodsEntity wareGoods) {
        WareGoodsEntity wareGoodsEntity = new WareGoodsEntity();
        wareGoodsEntity.setId(wareGoods.getId());
        wareGoodsEntity.setUpdateDate(System.currentTimeMillis());
        if (wareGoods.getAmount() > 0) {
            wareGoodsEntity.setAmount(wareGoods.getAmount());
        }

        this.baseMapper.updateById(wareGoodsEntity);
    }

    @Override
    public void updateWareGoodsById(Map<String, Object> params) {
        Long id = Long.parseLong((String) params.get("id"));
        String type = (String) params.get("type");
        Integer num = Integer.parseInt((String) params.get("num"));

        if (num == null || num <= 0 || id == null || id <= 0) {
            return;
        }

        if ("checkIn".equals(type)) {
            this.baseMapper.checkIn(id, num);
        } else if ("checkOut".equals(type)) {
            this.baseMapper.checkOut(id, num);
        }
    }

    private WareGoodsVo toVo(WareGoodsEntity wareGoodsEntity) {
        WareEntity ware = wareService.getById(wareGoodsEntity.getWareId());
        GoodsVo goods = goodsService.getGoodsById(wareGoodsEntity.getGoodsId());

        WareGoodsVo wareGoodsVo = new WareGoodsVo();
        BeanUtils.copyProperties(wareGoodsEntity, wareGoodsVo);
        wareGoodsVo.setWare(ware);
        wareGoodsVo.setGoods(goods);
        return wareGoodsVo;
    }

}