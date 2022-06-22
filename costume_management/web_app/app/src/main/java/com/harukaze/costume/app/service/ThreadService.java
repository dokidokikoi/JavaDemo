package com.harukaze.costume.app.service;

import com.harukaze.costume.app.vo.UserVo;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ThreadService {

    void recordLog(ProceedingJoinPoint joinPoint, Long time, HttpServletRequest request, UserVo userVo) throws IOException;
}
