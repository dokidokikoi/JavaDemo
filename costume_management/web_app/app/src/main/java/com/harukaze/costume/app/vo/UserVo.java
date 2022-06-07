package com.harukaze.costume.app.vo;

import com.harukaze.costume.app.entity.PermissionEntity;
import com.harukaze.costume.app.entity.RoleEntity;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @PackageName: com.harukaze.costume.app.vo
 * @ClassName: UserVo
 * @Description:
 * @Author: doki
 * @Date: 2022/6/2 10:04
 */

@Data
public class UserVo {
    private Long id;
    /**
     * 账号名
     */
    private String account;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 创建时间
     */
    private Long createDate;
    private Integer state;

    private List<PermissionEntity> permissions;

    private List<RoleEntity> roles;
}
