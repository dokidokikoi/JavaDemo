package com.harukaze.costume.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.harukaze.costume.app.entity.PermissionEntity;
import com.harukaze.costume.app.entity.RoleEntity;
import com.harukaze.costume.app.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.common.utils.Query;

import com.harukaze.costume.app.dao.RolePermissionDao;
import com.harukaze.costume.app.entity.RolePermissionEntity;
import com.harukaze.costume.app.service.RolePermissionService;


@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermissionEntity> implements RolePermissionService {

    @Autowired
    private PermissionService permissionService;
    
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolePermissionEntity> page = this.page(
                new Query<RolePermissionEntity>().getPage(params),
                new QueryWrapper<RolePermissionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<PermissionEntity> listPermission(Long roleId) {
        return this.baseMapper.selectPermissionList(roleId);
    }

    @Override
    public List<PermissionEntity> getPermissions(Long id) {
        List<RolePermissionEntity> RolePermissions = this.baseMapper.selectList(
                new LambdaQueryWrapper<RolePermissionEntity>()
                        .eq(RolePermissionEntity::getRoleId, id));

        return RolePermissions.stream()
                .map(item -> permissionService.getById(item.getPermissionId()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeRolePermission(Map<String, Object> params) {
        Long roleId = Long.parseLong((String) params.get("roleId"));
        Long permissionId = Long.parseLong((String) params.get("permissionId"));

        this.baseMapper.deleteRolePermission(roleId, permissionId);
    }

}