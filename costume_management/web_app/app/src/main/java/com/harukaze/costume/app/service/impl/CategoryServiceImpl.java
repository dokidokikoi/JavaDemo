package com.harukaze.costume.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.common.utils.Query;

import com.harukaze.costume.app.dao.CategoryDao;
import com.harukaze.costume.app.entity.CategoryEntity;
import com.harukaze.costume.app.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryCategoryPage(Map<String, Object> params) {
        LambdaQueryWrapper<CategoryEntity> wrapper = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");

        if (!StrUtil.isBlank(key)) {
            wrapper.and(w -> {
                w.like(CategoryEntity::getCategoryName, key);
            });
        }

        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}