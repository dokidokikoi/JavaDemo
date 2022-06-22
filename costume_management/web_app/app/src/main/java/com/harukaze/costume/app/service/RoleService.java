package com.harukaze.costume.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.harukaze.costume.app.param.RoleParam;
import com.harukaze.costume.app.vo.RoleVo;
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

    PageUtils listRolePage(Map<String, Object> params);

    RoleVo getRoleById(Long id);

    void setStateByIds(Map<String, Object> params);

    void saveRole(RoleEntity role);

    void updateRole(RoleParam param);

    void setPermission(Long id, Long[] ids);

    boolean removeRoleById(Long id);
}

