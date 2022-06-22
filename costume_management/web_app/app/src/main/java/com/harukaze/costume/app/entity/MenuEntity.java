package com.harukaze.costume.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @PackageName: com.harukaze.blog.app.entity
 * @ClassName: MenuEntity
 * @Description:
 * @Author: doki
 * @Date: 2022/6/13 8:17
 */

@Data
@TableName("hk_menu")
public class MenuEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String menuName;
}
