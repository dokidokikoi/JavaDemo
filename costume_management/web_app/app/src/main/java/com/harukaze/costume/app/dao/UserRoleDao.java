package com.harukaze.costume.app.dao;

import com.harukaze.costume.app.entity.RoleEntity;
import com.harukaze.costume.app.entity.UserRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 * 
 * @author doki
 * @email harukaze_doki@163.com
 * @date 2022-06-01 16:36:17
 */
@Mapper
public interface UserRoleDao extends BaseMapper<UserRoleEntity> {

    List<RoleEntity> selectRoleList(@Param("userId") Long userId);

    @Delete("delete from hk_user_role where role_id = #{roleId} and user_id = #{userId}")
    void deleteUserRole(@Param("roleId") Long roleId, @Param("userId") Long userId);
}
