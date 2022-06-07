package com.harukaze.costume.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.harukaze.costume.app.service.UserService;
import com.harukaze.costume.app.vo.UserVo;
import com.harukaze.costume.app.vo.WareGoodsVo;
import com.harukaze.costume.app.vo.WareVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harukaze.costume.common.utils.PageUtils;
import com.harukaze.costume.common.utils.Query;

import com.harukaze.costume.app.dao.WareDao;
import com.harukaze.costume.app.entity.WareEntity;
import com.harukaze.costume.app.service.WareService;


@Service("wareService")
public class WareServiceImpl extends ServiceImpl<WareDao, WareEntity> implements WareService {

    @Autowired
    private UserService userService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareEntity> page = this.page(
                new Query<WareEntity>().getPage(params),
                new QueryWrapper<WareEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listWarePage(Map<String, Object> params) {
        LambdaQueryWrapper<WareEntity> wrapper = new LambdaQueryWrapper<>();

        String address = (String) params.get("key");
        if (!StrUtil.isBlank(address)) {
            wrapper.and(w -> {
                w.eq(WareEntity::getAddress, address)
                        .or()
                        .like(WareEntity::getAddress, address);
            });
        }

        IPage<WareEntity> page = this.page(
                new Query<WareEntity>().getPage(params),
                wrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<WareVo> collect = pageUtils.getList().stream().map(item -> toVo((WareEntity) item)).collect(Collectors.toList());
        pageUtils.setList(collect);
        return pageUtils;
    }

    @Override
    public WareVo getWareById(Long id) {

        return toVo(this.baseMapper.selectById(id));
    }

    @Override
    public void saveWare(WareEntity ware) {
        WareEntity wareEntity = new WareEntity();
        BeanUtils.copyProperties(ware, wareEntity);

        wareEntity.setId(null);
        wareEntity.setCreateDate(System.currentTimeMillis());

        this.save(wareEntity);
    }

    private WareVo toVo(WareEntity wareEntity) {
        WareVo wareVo = new WareVo();

        BeanUtils.copyProperties(wareEntity, wareVo);

        if (wareEntity.getUserId() != null && wareEntity.getUserId() != 0) {
            wareVo.setManager(userService.getUserById(wareEntity.getUserId()).getAccount());
        }
        return wareVo;
    }

}