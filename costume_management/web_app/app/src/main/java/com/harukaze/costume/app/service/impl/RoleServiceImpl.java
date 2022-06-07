package com.harukaze.costume.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.harukaze.costume.app.entity.PermissionEntity;
import com.harukaze.costume.app.service.PermissionService;
import com.harukaze.costume.app.service.RolePermissionService;
import com.harukaze.costume.app.vo.RoleVo;
import com.harukaze.costume.app.vo.UserVo;
import org.springframework.beans.BeanUtils;
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

import com.harukaze.costume.app.dao.RoleDao;
import com.harukaze.costume.app.entity.RoleEntity;
import com.harukaze.costume.app.service.RoleService;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(params),
                new QueryWrapper<RoleEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listRolePage(Map<String, Object> params) {
        LambdaQueryWrapper<RoleEntity> wrapper = new LambdaQueryWrapper<>();

        String name = (String) params.get("key");
        if (!StrUtil.isBlank(name)) {
            wrapper.and(w -> {
                w.eq(RoleEntity::getRoleName, name)
                        .or()
                        .like(RoleEntity::getRoleName, name);
            });
        }

        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(params),
                wrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<RoleVo> collect = pageUtils.getList().stream()
                .map(item -> toVo((RoleEntity) item))
                .collect(Collectors.toList());
        pageUtils.setList(collect);

        return pageUtils;
    }

    @Override
    public RoleVo getRoleById(Long id) {
        RoleEntity roleEntity = this.baseMapper.selectById(id);

        return toVo(roleEntity);
    }

    @Override
    public void setStateByIds(Map<String, Object> params) {
        List<String> list = (List<String>) params.get("ids");
        List<Long> ids = list.stream()
                .map(item -> Long.parseLong(item))
                .collect(Collectors.toList());
        boolean flag = (boolean) params.get("state");
        if (list.size() != 0) {
            this.baseMapper
                    .update(null,
                            new LambdaUpdateWrapper<RoleEntity>()
                                    .in(RoleEntity::getId,ids));
        }
    }

    private RoleVo toVo(RoleEntity roleEntity) {
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(roleEntity, roleVo);

        List<PermissionEntity> list = rolePermissionService.getPermissions(roleEntity.getId());

        roleVo.setPermissions(list);
        return roleVo;
    }

}