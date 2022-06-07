package com.harukaze.costume.app.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.harukaze.costume.app.entity.PermissionEntity;
import lombok.Data;

import java.security.Permission;
import java.util.List;

/**
 * @PackageName: com.harukaze.costume.app.vo
 * @ClassName: RoleVo
 * @Description:
 * @Author: doki
 * @Date: 2022/6/5 0:35
 */

@Data
public class RoleVo {
    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;

    private List<PermissionEntity> permissions;
}
