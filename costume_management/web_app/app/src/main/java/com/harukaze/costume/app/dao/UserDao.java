package com.harukaze.costume.app.dao;

import com.harukaze.costume.app.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author doki
 * @email harukaze_doki@163.com
 * @date 2022-06-01 16:36:17
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
