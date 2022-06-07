package com.harukaze.costume.app.controller;

import java.util.Arrays;
import java.util.Map;

import com.harukaze.costume.app.vo.RoleVo;
import com.harukaze.costume.common.valid.AddGroup;
import com.harukaze.costume.common.valid.UpdateGroup;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.harukaze.costume.app.entity.RoleEntity;
import com.harukaze.costume.app.service.RoleService;
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
@RequestMapping("app/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = roleService.listRolePage(params);

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        RoleVo role = roleService.getRoleById(id);

        return R.ok().put("data", role);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Validated(AddGroup.class) @RequestBody RoleEntity role){
		roleService.save(role);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Validated(UpdateGroup.class) @RequestBody RoleEntity role){
		roleService.updateById(role);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/set_state")
    public R delete(@RequestBody Map<String, Object> params){
		roleService.setStateByIds(params);

        return R.ok();
    }

}
