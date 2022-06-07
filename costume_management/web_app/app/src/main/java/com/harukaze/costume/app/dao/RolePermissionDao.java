package com.harukaze.costume.app.dao;

import com.harukaze.costume.app.entity.PermissionEntity;
import com.harukaze.costume.app.entity.RolePermissionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author doki
 * @email harukaze_doki@163.com
 * @date 2022-06-01 16:36:17
 */
@Mapper
public interface RolePermissionDao extends BaseMapper<RolePermissionEntity> {

    List<PermissionEntity> selectPermissionList(@Param("roleId") Long roleId);

    @Delete("delete from hk_role_permission where role_id = #{roleId} and permission_id = #{permissionId}")
    void deleteRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
