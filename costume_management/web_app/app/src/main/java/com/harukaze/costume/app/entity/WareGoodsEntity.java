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
@TableName("hk_ware_goods")
public class WareGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	@NotNull(message = "修改，id不能为空", groups = UpdateGroup.class)
	private Long id;
	/**
	 * 
	 */
	private Integer amount;
	/**
	 * 
	 */
	private Long createDate;
	/**
	 * 
	 */
	private Long updateDate;
	/**
	 * 
	 */
	@NotNull(message = "新增，wareId不能为空", groups = AddGroup.class)
	private Long wareId;
	/**
	 * 
	 */
	@NotNull(message = "新增，goodsId不能为空", groups = AddGroup.class)
	private Long goodsId;

}
