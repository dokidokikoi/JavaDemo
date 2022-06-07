package com.harukaze.costume.app.controller;

import java.util.Arrays;
import java.util.Map;

import com.harukaze.costume.app.vo.WareVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.harukaze.costume.app.entity.WareEntity;
import com.harukaze.costume.app.service.WareService;
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
@RequestMapping("app/ware")
public class WareController {
    @Autowired
    private WareService wareService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareService.listWarePage(params);

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareVo ware = wareService.getWareById(id);

        return R.ok().put("ware", ware);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(WareEntity ware){
		wareService.saveWare(ware);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(WareEntity ware){
		wareService.updateById(ware);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
