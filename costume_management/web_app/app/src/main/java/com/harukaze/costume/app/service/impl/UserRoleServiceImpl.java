package com.harukaze.costume.app.service.impl;

import com.harukaze.costume.app.entity.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.common.utils.Query;

import com.harukaze.costume.app.dao.UserRoleDao;
import com.harukaze.costume.app.entity.UserRoleEntity;
import com.harukaze.costume.app.service.UserRoleService;


@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRoleEntity> implements UserRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserRoleEntity> page = this.page(
                new Query<UserRoleEntity>().getPage(params),
                new QueryWrapper<UserRoleEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<RoleEntity> listRole(Long userId) {

        return this.baseMapper.selectRoleList(userId);
    }

    @Override
    public void removeUserRole(Map<String, Object> params) {
        Long roleId = Long.parseLong((String) params.get("roleId"));
        Long userId = Long.parseLong((String) params.get("userId"));

        this.baseMapper.deleteUserRole(roleId, userId);
    }

}