package com.harukaze.costume.app.vo;

import lombok.Data;

import java.util.List;

/**
 * @PackageName: com.harukaze.blog.app.vo
 * @ClassName: PermissionVo
 * @Description:
 * @Author: doki
 * @Date: 2022/6/13 8:16
 */

@Data
public class PermissionVo {

    private Long id;

    private String name;

    private List<PermissionVo> children;
}
