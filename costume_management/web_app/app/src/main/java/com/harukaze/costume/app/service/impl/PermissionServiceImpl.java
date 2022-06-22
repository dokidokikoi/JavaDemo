package com.harukaze.costume.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.harukaze.costume.app.dao.CommonDao;
import com.harukaze.costume.app.entity.MenuEntity;
import com.harukaze.costume.app.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.common.utils.Query;

import com.harukaze.costume.app.dao.PermissionDao;
import com.harukaze.costume.app.entity.PermissionEntity;
import com.harukaze.costume.app.service.PermissionService;


@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, PermissionEntity> implements PermissionService {

    @Autowired
    private CommonDao menuDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PermissionEntity> page = this.page(
                new Query<PermissionEntity>().getPage(params),
                new QueryWrapper<PermissionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listPermissionPage(Map<String, Object> params) {
        LambdaQueryWrapper<PermissionEntity> wrapper = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");

        if (!StrUtil.isBlank(key)) {
            wrapper.like(PermissionEntity::getName, key);
        }

        IPage<PermissionEntity> page = this.page(
                new Query<PermissionEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<PermissionVo> getPermissionTree() {

        List<MenuEntity> menuEntities = menuDao.selectList(null);

        return menuEntities.stream().map(this::toVo).collect(Collectors.toList());
    }

    private PermissionVo toVo(MenuEntity menu) {
        PermissionVo permissionVo = new PermissionVo();

        permissionVo.setId(menu.getId());
        permissionVo.setName(menu.getMenuName());

        List<PermissionEntity> entities = this.baseMapper.selectList(
                new LambdaQueryWrapper<PermissionEntity>()
                        .eq(PermissionEntity::getMenuId, menu.getId()));

        List<PermissionVo> collect = entities.stream().map(item -> {
            PermissionVo permission = new PermissionVo();
            permission.setId(item.getId());
            permission.setName(item.getName());

            return permission;
        }).collect(Collectors.toList());
        permissionVo.setChildren(collect);

        return permissionVo;
    }

}