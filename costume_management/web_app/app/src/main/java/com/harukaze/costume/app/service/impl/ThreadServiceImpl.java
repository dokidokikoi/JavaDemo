package com.harukaze.costume.app.service.impl;

import com.harukaze.costume.app.core.annotation.LogAnnotation;
import com.harukaze.costume.app.dao.CommonDao;
import com.harukaze.costume.app.entity.GoodsEntity;
import com.harukaze.costume.app.entity.LogEntity;
import com.harukaze.costume.app.entity.WareGoodsEntity;
import com.harukaze.costume.app.service.LogService;
import com.harukaze.costume.app.service.ThreadService;
import com.harukaze.costume.app.service.UserService;
import com.harukaze.costume.app.service.WareGoodsService;
import com.harukaze.costume.app.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sun.security.util.ArrayUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @PackageName: com.harukaze.blog.app.service.impl
 * @ClassName: ThreadServiceImpl
 * @Description:
 * @Author: doki
 * @Date: 2022/6/9 10:02
 */
@Slf4j
@Service
public class ThreadServiceImpl implements ThreadService {

    @Autowired
    private LogService logService;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private WareGoodsService wareGoodsService;

    private final int MAX_TRY = 10;

    @Override
    @Async("taskExecutor")
    public void recordLog(ProceedingJoinPoint joinPoint, Long time, HttpServletRequest request, UserVo userVo) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        LogEntity logEntity = new LogEntity();
        if (userVo != null) {
            logEntity.setUserId(userVo.getId());
        }

        logEntity.setCreateDate(System.currentTimeMillis());
        logEntity.setOptionl(logAnnotation.operator());

        // 获取方法参数值
        Object[] args = joinPoint.getArgs();
        // 获取方法参数名称
        String[] parameterNames = signature.getParameterNames();
        if ("出入库".equals(logEntity.getOptionl())) {
            // 根据参数名获取值
            for (int i = 0; i < parameterNames.length; i++) {
                if ("params".equals(parameterNames[i])) {
                    Map<String, Object> params = (Map<String, Object>) args[i];
                    logEntity.setAmount(Integer.parseInt((String) params.getOrDefault("num", null)));
                    WareGoodsEntity wareGoodsEntity = wareGoodsService.getById(Long.parseLong((String) params.get("id")));
                    logEntity.setGoodsId(wareGoodsEntity.getGoodsId());
                    logEntity.setWareId(wareGoodsEntity.getWareId());

                    if ("checkIn".equals(((String) params.get("type")))) {
                        logEntity.setOptionl("入库");
                    } else {
                        logEntity.setOptionl("出库120");
                    }
                }
            }
        }
        if ("加入仓库".equals(logAnnotation.operator())) {
            WareGoodsEntity wareGoods = (WareGoodsEntity) args[0];
            logEntity.setAmount(1);
            logEntity.setGoodsId(wareGoods.getGoodsId());
            logEntity.setWareId(wareGoods.getWareId());
        }

        if ("商品".equals(logAnnotation.module())) {
            if ("添加商品".equals(logAnnotation.operator()) || "修改商品".equals(logAnnotation.operator())) {
                GoodsEntity goods = (GoodsEntity) args[0];
                logEntity.setGoodsId(goods.getId());
                logEntity.setAmount(1);
            } else {
                logEntity.setAmount(1);
                logEntity.setGoodsId(((Long) args[0]));
            }
        }

        logService.save(logEntity);

        log.info("=======================log============================");
        log.info("module:{}",logAnnotation.module());
        log.info("operation:{}",logEntity.getOptionl());
        log.info("goodsId:{}",logEntity.getGoodsId());
        log.info("amount:{}",logEntity.getAmount());

        log.info("=======================log end============================");
    }
}
