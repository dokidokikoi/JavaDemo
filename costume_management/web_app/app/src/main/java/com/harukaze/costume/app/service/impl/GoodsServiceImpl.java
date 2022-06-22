package com.harukaze.costume.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.harukaze.costume.app.entity.CategoryEntity;
import com.harukaze.costume.app.entity.UserEntity;
import com.harukaze.costume.app.param.GoodsParam;
import com.harukaze.costume.app.service.CategoryService;
import com.harukaze.costume.app.service.UserService;
import com.harukaze.costume.app.util.UserThreadLocal;
import com.harukaze.costume.app.vo.GoodsVo;
import com.harukaze.costume.app.vo.UserVo;
import com.harukaze.costume.common.constant.UserConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.common.utils.Query;

import com.harukaze.costume.app.dao.GoodsDao;
import com.harukaze.costume.app.entity.GoodsEntity;
import com.harukaze.costume.app.service.GoodsService;
import org.springframework.util.StringUtils;


@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GoodsEntity> page = this.page(
                new Query<GoodsEntity>().getPage(params),
                new QueryWrapper<GoodsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listGoodsPage(GoodsParam params) {
        LambdaQueryWrapper<GoodsEntity> wrapper = new LambdaQueryWrapper<>();
        String key = params.getKey();
        Long categoryId = params.getCategoryId();

        if (!StrUtil.isBlank(key)) {
            wrapper.and(w -> {
                w.eq(GoodsEntity::getName, key).or().like(GoodsEntity::getName, key);
            });
        }

        if (categoryId != null && categoryId != 0) {
            wrapper.eq(GoodsEntity::getCategoryId, categoryId);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("limit", params.getLimit());
        map.put("page", params.getPage());

        IPage<GoodsEntity> page = this.page(
                new Query<GoodsEntity>().getPage(map),
                wrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<GoodsVo> collect = pageUtils.getList().stream()
                .map(item -> toVo((GoodsEntity) item))
                .collect(Collectors.toList());
        pageUtils.setList(collect);

        return pageUtils;
    }

    @Override
    public GoodsVo getGoodsById(Long id) {
        GoodsEntity goodsEntity = this.baseMapper.selectById(id);
        return toVo(goodsEntity);
    }

    @Override
    public void setStateByIds(Long id, Integer state) throws Exception {
        this.baseMapper.update(
                null,
                new LambdaUpdateWrapper<GoodsEntity>()
                        .eq(GoodsEntity::getId, id)
                        .set(
                                GoodsEntity::getState,
                                state));
    }

    @Override
    public void saveGoods(GoodsEntity goods) throws Exception {
        goods.setId(null);
        if (goods.getPrice() <= 0) {
            throw new Exception("bad request");
        }
        goods.setCreateDate(System.currentTimeMillis());
        goods.setUserId(UserThreadLocal.get().getId());

        this.save(goods);
    }

    @Override
    public void updateGoodsById(GoodsEntity goods) {
        GoodsEntity goodsEntity = new GoodsEntity();

        goodsEntity.setId(goods.getId());
        if (goods.getPrice() > 0) {
            goodsEntity.setPrice(goods.getPrice());
        }
        goodsEntity.setCategoryId(goods.getCategoryId());
        goodsEntity.setCover(goods.getCover());
        goodsEntity.setName(goods.getName());

        this.baseMapper.updateById(goodsEntity);
    }

    private GoodsVo toVo(GoodsEntity goodsEntity) {
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goodsEntity, goodsVo);
        UserEntity user = userService.getOne(
                new LambdaQueryWrapper<UserEntity>()
                        .eq(UserEntity::getId, (goodsEntity).getUserId()));
        goodsVo.setCreatorName(user.getAccount());
        CategoryEntity category = categoryService.getOne(
                new LambdaQueryWrapper<CategoryEntity>()
                        .eq(CategoryEntity::getId, (goodsEntity).getCategoryId()));
        goodsVo.setCategory(category);

        return goodsVo;
    }

}