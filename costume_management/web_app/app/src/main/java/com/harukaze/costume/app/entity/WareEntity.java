package com.harukaze.costume.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("hk_ware")
public class WareEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 创建时间
	 */
	private Long createDate;
	/**
	 * 
	 */
	private Long userId;

}
