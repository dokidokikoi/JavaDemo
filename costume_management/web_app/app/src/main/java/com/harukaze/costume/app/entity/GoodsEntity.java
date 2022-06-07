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
@TableName("hk_goods")
public class GoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	@NotNull(message = "修改,id不能为空", groups = UpdateGroup.class)
	private Long id;
	/**
	 * 商品名
	 */
	@NotBlank(message = "新增,name不能为空", groups = AddGroup.class)
	private String name;
	/**
	 * 商品单价
	 */
	@NotNull(message = "新增,price不能为空", groups = AddGroup.class)
	private Double price;
	/**
	 * 创建时间
	 */
	private Long createDate;
	/**
	 * 
	 */
	private Long userId;
	/**
	 * 
	 */
	private Long categoryId;
	/**
	 *
	 */
	private Integer state;

	private String cover;

}
