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
@TableName("hk_log")
public class LogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long createDate;
	/**
	 * 
	 */
	private String optionl;
	/**
	 * 
	 */
	private Integer amount;
	/**
	 * 
	 */
	private Long ip;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private String browser;
	/**
	 * 
	 */
	private Long userId;
	/**
	 * 
	 */
	private Long goodsId;

}
