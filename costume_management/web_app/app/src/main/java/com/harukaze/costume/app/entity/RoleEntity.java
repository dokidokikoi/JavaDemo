package com.harukaze.costume.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.harukaze.costume.common.valid.AddGroup;
import com.harukaze.costume.common.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * 
 * @author doki
 * @email harukaze_doki@163.com
 * @date 2022-06-01 16:36:17
 */
@Data
@TableName("hk_role")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	@NotNull(message = "修改,id不能为空", groups = UpdateGroup.class)
	private Long id;
	/**
	 * 角色名
	 */
	@NotBlank
	@NotBlank(message = "新增,roleName不能为空", groups = AddGroup.class)
	private String roleName;
	/**
	 * 角色描述
	 */
	@NotBlank(message = "新增,roleDesc不能为空", groups = AddGroup.class)
	private String roleDesc;

}
