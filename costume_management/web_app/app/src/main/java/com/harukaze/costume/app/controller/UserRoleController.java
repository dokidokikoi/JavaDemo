package com.harukaze.costume.app.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harukaze.costume.app.entity.UserRoleEntity;
import com.harukaze.costume.app.service.UserRoleService;
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
@RequestMapping("app/userrole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 列表
     */
//    @RequestMapping("/list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = userRoleService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }


    /**
     * 信息
     */
//    @RequestMapping("/info/{roleId}")
//    public R info(@PathVariable("roleId") Long roleId){
//		UserRoleEntity userRole = userRoleService.getById(roleId);
//
//        return R.ok().put("userRole", userRole);
//    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserRoleEntity userRole){
		userRoleService.save(userRole);

        return R.ok();
    }

    /**
     * 修改
     */
//    @RequestMapping("/update")
//    public R update(@RequestBody UserRoleEntity userRole){
//		userRoleService.updateById(userRole);
//
//        return R.ok();
//    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Map<String, Object> params){
		userRoleService.removeUserRole(params);

        return R.ok();
    }

}
