package com.harukaze.costume.app.controller;

import java.util.Arrays;
import java.util.Map;

import com.harukaze.costume.app.core.annotation.HasPartPermission;
import com.harukaze.costume.app.core.annotation.HasPartRole;
import com.harukaze.costume.app.vo.UserVo;
import com.harukaze.costume.common.valid.AddGroup;
import com.harukaze.costume.common.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.harukaze.costume.app.entity.UserEntity;
import com.harukaze.costume.app.service.UserService;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.common.utils.R;



/**
 * 
 *
 * @author doki
 * @email harukaze_doki@163.com
 * @date 2022-06-01 16:36:17
 */
@RestController
@RequestMapping("app/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = userService.listUserPage(params);

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        UserVo userVo = userService.getUserById(id);

        return R.ok().put("data", userVo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Validated(AddGroup.class) @RequestBody UserEntity user){
		userService.saveUser(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @HasPartRole("test")
    public R update(@Validated(UpdateGroup.class) @RequestBody UserEntity user){
		userService.updateUserById(user);

        return R.ok();
    }

    /**
     * 批量修改用户状态
     */
    @PutMapping("/set_state")
    public R delete(@RequestBody Map<String, Object> param) throws Exception {
		userService.setStateByIds(param);

        return R.ok();
    }

}
