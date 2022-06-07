package com.harukaze.costume.app.controller;

import com.harukaze.costume.app.core.annotation.HasPartPermission;
import com.harukaze.costume.app.core.annotation.HasPermission;
import com.harukaze.costume.app.core.annotation.HasRole;
import com.harukaze.costume.app.entity.GoodsEntity;
import com.harukaze.costume.app.param.GoodsParam;
import com.harukaze.costume.app.service.GoodsService;
import com.harukaze.costume.app.vo.GoodsVo;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.common.utils.R;
import com.harukaze.costume.common.valid.AddGroup;
import com.harukaze.costume.common.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 
 *
 * @author doki
 * @email harukaze_doki@163.com
 * @date 2022-06-01 16:36:17
 */
@RestController
@RequestMapping("app/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(GoodsParam params){
        PageUtils page = goodsService.listGoodsPage(params);

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        GoodsVo goods = goodsService.getGoodsById(id);

        return R.ok().put("data", goods);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@Validated(AddGroup.class) GoodsEntity goods) throws Exception {
		goodsService.saveGoods(goods);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@Validated(UpdateGroup.class) GoodsEntity goods){
		goodsService.updateGoodsById(goods);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/set_state")
    public R delete(@RequestParam("id") Long id, @RequestParam("state") Integer state) throws Exception {
		goodsService.setStateByIds(id, state);

        return R.ok();
    }

}
