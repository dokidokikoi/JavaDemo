package com.harukaze.costume.app.param;

import com.harukaze.costume.common.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @PackageName: com.harukaze.blog.app.param
 * @ClassName: RoleParam
 * @Description:
 * @Author: doki
 * @Date: 2022/6/9 8:41
 */
@Data
public class RoleParam {
    @NotNull(message = "修改，id不能为空", groups = UpdateGroup.class)
    private Long id;
    /**
     * 角色名
     */
    @NotBlank(message = "修改，roleName不能为空", groups = UpdateGroup.class)
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;

    private List<Long> permissions;
}
