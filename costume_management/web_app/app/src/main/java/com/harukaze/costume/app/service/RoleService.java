package com.harukaze.costume.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.app.entity.RoleEntity;

import java.util.Map;

/**
 * 
 *
 * @author doki
 * @email harukaze_doki@163.com
 * @date 2022-06-01 16:36:17
 */
public interface RoleService extends IService<RoleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

