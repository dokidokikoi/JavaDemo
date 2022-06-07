package com.harukaze.costume.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.harukaze.costume.app.core.annotation.HasPartPermission;
import com.harukaze.costume.app.entity.*;
import com.harukaze.costume.app.service.*;
import com.harukaze.costume.app.util.UserThreadLocal;
import com.harukaze.costume.app.vo.GoodsVo;
import com.harukaze.costume.app.vo.UserVo;
import com.harukaze.costume.common.constant.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.common.utils.Query;

import com.harukaze.costume.app.dao.UserDao;

@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserVo toVo(UserEntity user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        List<RoleEntity> roles =  userRoleService.listRole(userVo.getId());
        List<PermissionEntity> permissions = new ArrayList<>();
        for (RoleEntity role : roles) {
            List<PermissionEntity> permissionsChild = rolePermissionService.listPermission(role.getId());
            permissions.addAll(permissionsChild);
        }
        userVo.setRoles(roles);
        userVo.setPermissions(permissions);

        return userVo;
    }

    @Override
    public PageUtils listUserPage(Map<String, Object> params) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");

        if (!StrUtil.isBlank(key)) {
            wrapper.and(w -> {
                w.eq(UserEntity::getAccount, key)
                        .or()
                        .like(UserEntity::getAccount, key)
                        .or()
                        .eq(UserEntity::getNickname, key)
                        .or()
                        .like(UserEntity::getNickname, key);
            });
        }

        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                wrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<UserVo> collect = pageUtils.getList().stream().map(item -> toVo((UserEntity) item))
                .collect(Collectors.toList());
        pageUtils.setList(collect);

        return pageUtils;
    }

    @Override
    public UserVo getUserById(Long id) {
        UserEntity userEntity = this.baseMapper.selectById(id);
        return toVo(userEntity);
    }

    @Override
    public void setStateByIds(Map<String, Object> param) throws Exception {
        try {
            List<String> list = (List<String>) param.get("ids");
            List<Long> ids = list.stream().map(item -> Long.parseLong(item)).collect(Collectors.toList());
            boolean state = (boolean) param.get("state");

            if (ids != null && ids.size() != 0) {
                this.baseMapper.update(
                        null,
                        new LambdaUpdateWrapper<UserEntity>()
                                .in(UserEntity::getId, ids)
                                .set(
                                        UserEntity::getState,
                                        state ? UserConstant.Status.USER_UP.getCode() : UserConstant.Status.USER_DOWN.getCode()));
            }
        } catch (Exception e) {
            log.error("{}",e);
            throw new Exception();
        }

    }

    @Override
    public void updateUserById(UserEntity user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setNickname(user.getNickname());
        if (!StrUtil.isBlank(user.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        this.baseMapper.updateById(userEntity);
    }

    @Override
    public void saveUser(UserEntity user) {
        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(user, userEntity);

        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setId(null);
        userEntity.setCreateDate(System.currentTimeMillis());
        userEntity.setState(UserConstant.Status.USER_UP.getCode());

        this.save(userEntity);
    }

}