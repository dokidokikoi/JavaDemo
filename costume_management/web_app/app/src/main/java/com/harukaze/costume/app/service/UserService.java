package com.harukaze.costume.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.harukaze.costume.app.vo.UserVo;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.app.entity.UserEntity;

import java.util.Map;

/**
 * 
 *
 * @author doki
 * @email harukaze_doki@163.com
 * @date 2022-06-01 16:36:17
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserVo toVo(UserEntity user);

    PageUtils listUserPage(Map<String, Object> params);

    UserVo getUserById(Long id);

    void setStateByIds(Map<String, Object> param) throws Exception;

    void updateUserById(UserEntity user);

    void saveUser(UserEntity user);

    void setRoles(Long id, Long[] ids);
}

