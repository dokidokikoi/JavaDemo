package com.harukaze.costume.app.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harukaze.costume.app.entity.WareGoodsEntity;
import com.harukaze.costume.app.service.WareGoodsService;
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
@RequestMapping("app/waregoods")
public class WareGoodsController {
    @Autowired
    private WareGoodsService wareGoodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareGoodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareGoodsEntity wareGoods = wareGoodsService.getById(id);

        return R.ok().put("wareGoods", wareGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareGoodsEntity wareGoods){
		wareGoodsService.save(wareGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareGoodsEntity wareGoods){
		wareGoodsService.updateById(wareGoods);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareGoodsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
