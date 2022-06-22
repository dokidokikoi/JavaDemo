package com.harukaze.costume.app.controller;

import java.util.Arrays;
import java.util.Map;

import com.harukaze.costume.app.param.RoleParam;
import com.harukaze.costume.app.vo.RoleVo;
import com.harukaze.costume.common.valid.AddGroup;
import com.harukaze.costume.common.valid.UpdateGroup;
import com.harukaze.costume.common.constant.ResponseStatus;
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
    public R list(@RequestParam Map<String, Object> params){
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
		roleService.saveRole(role);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Validated(UpdateGroup.class) @RequestBody RoleParam param){
		roleService.updateRole(param);

        return R.ok();
    }

    /**
     * 分配权限
     */
    @PutMapping("/set_permission/{id}")
    public R setPermission(@PathVariable("id") Long id, @RequestBody Long[] ids){
        roleService.setPermission(id, ids);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id){
        boolean flag = roleService.removeRoleById(id);

        return flag ? R.ok() : R.error(ResponseStatus.ROLE_DELETE_ERR.getCode(), ResponseStatus.ROLE_DELETE_ERR.getMsg());
    }

}
