package com.harukaze.costume.app.controller;

import java.util.Arrays;
import java.util.Map;

import com.harukaze.costume.app.core.annotation.LogAnnotation;
import com.harukaze.costume.app.vo.WareGoodsVo;
import com.harukaze.costume.common.valid.AddGroup;
import com.harukaze.costume.common.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
        PageUtils page = wareGoodsService.queryWareGoodsPage(params);

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        WareGoodsVo wareGoods = wareGoodsService.getWareGoodsById(id);

        return R.ok().put("data", wareGoods);
    }

    /**
     * 将商品加入仓库
     */
    @LogAnnotation(module = "仓库模块", operator = "加入仓库")
    @RequestMapping("/save")
    public R save(@Validated(AddGroup.class) WareGoodsEntity wareGoods){
		wareGoodsService.saveWareGoods(wareGoods);

        return R.ok();
    }

    /**
     * 出入库
     */
    @LogAnnotation(module = "仓库模块", operator = "出入库")
    @RequestMapping("/update")
    public R update(@RequestParam  Map<String, Object> params){
		wareGoodsService.updateWareGoodsById(params);

        return R.ok();
    }

    /**
     * 删除
     */
//    @RequestMapping("/delete")
//    public R delete(@RequestBody Long[] ids){
//		wareGoodsService.removeByIds(Arrays.asList(ids));
//
//        return R.ok();
//    }

}
