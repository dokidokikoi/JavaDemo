package com.harukaze.costume.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
	@TableId
	private Long id;
	/**
	 * 商品名
	 */
	private String name;
	/**
	 * 商品单价
	 */
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

}
