package com.harukaze.costume.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harukaze.costume.app.entity.WareGoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.core.parameters.P;

/**
 * 
 * 
 * @author doki
 * @email harukaze_doki@163.com
 * @date 2022-06-01 16:36:17
 */
@Mapper
public interface WareGoodsDao extends BaseMapper<WareGoodsEntity> {

    IPage<WareGoodsEntity> selectWareGoodsByPage(
            Page<WareGoodsEntity> page,
            @Param("key") String key,
            @Param("wareAddress") String wareAddress,
            @Param("orderBy") String orderBy);

    @Update("UPDATE hk_ware_goods SET amount = amount + #{num} WHERE id = #{id}")
    void checkIn(@Param("id") Long id, @Param("num") Integer num);

    @Update("UPDATE hk_ware_goods SET amount = amount - #{num} WHERE id = #{id} and amount >= #{num}")
    void checkOut(@Param("id") Long id, @Param("num") Integer num);
}
