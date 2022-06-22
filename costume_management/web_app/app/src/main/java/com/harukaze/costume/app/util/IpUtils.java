package com.harukaze.costume.app.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Method;

@Slf4j
public class IpUtils {

//    private static DbSearcher searcher;
    private static Method method;
    /*
    * 使用nginx等反向代理软件，则不能通过request.getRemoteAddr()获取IP地址
    * */
    public static String getIpAddr(HttpServletRequest request) {
        String unknown = "unknown";
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 0) {
            String[] ips = ip.split(",");
            if (ips.length > 0) {
                ip = ips[0];
            }
        }
        return ip;
    }

    /**
     * 在服务启动时加载 ip2region.db 到内存中
     * 解决打包jar后找不到 ip2region.db 的问题
     */
//    static {
//        try {
//            InputStream inputStream = new ClassPathResource("/ip2region.db").getInputStream();
//            //将 ip2region.db 转为 ByteArray
//            byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
//            DbConfig dbConfig = new DbConfig();
//            searcher = new DbSearcher(dbConfig, dbBinStr);
//            //二进制方式初始化 DBSearcher，需要使用基于内存的查找算法 memorySearch
//            method = searcher.getClass().getMethod("memorySearch", String.class);
//        } catch (Exception e) {
//            log.error("initIp2regionResource exception:", e);
//        }
//    }

    /**
     * 根据ip从 ip2region.db 中获取地理位置
     *
     * @param ip
     * @return
     */
//    public static String getCityInfo(String ip) {
//        if (ip == null || !Util.isIpAddress(ip)) {
//            log.error("Error: Invalid ip address");
//            return null;
//        }
//        try {
//            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
//            String ipInfo = dataBlock.getRegion();
//            if (!StringUtils.isEmpty(ipInfo)) {
//                ipInfo = ipInfo.replace("|0", "");
//                ipInfo = ipInfo.replace("0|", "");
//            }
//            return ipInfo;
//        } catch (Exception e) {
//            log.error("getCityInfo exception:", e);
//        }
//        return null;
//    }

    public static Long getIpAddrNum(HttpServletRequest request) {
        String ipAddr = getIpAddr(request);
        if ("0:0:0:0:0:0:0:1".equals(ipAddr) || StrUtil.isBlank(ipAddr)) {
            return 0L;
        }
        String[] split = ipAddr.split("\\.");
        long ipNum = 0;
        for (int i = 3; i >= 0; i--) {
            long tmp = Long.parseLong(split[i]);
            ipNum |= (tmp << (3-i) * 8);
        }
        return ipNum;
    }

    public static Long getIpAddrNum(String ip) {
        String[] split = ip.split("\\.");
        long ipNum = 0;
        for (int i = 3; i >= 0; i--) {
            long tmp = Long.parseLong(split[i]);
            ipNum |= (tmp << (3-i) * 8);
        }
        return ipNum;
    }

    public static String ipToString(long ip) {
        StringBuilder sb = new StringBuilder();
        long tmp = 0xff;
        for (int i = 3; i >= 0; i--) {
            sb.append(((tmp << i * 8) & ip) >> i * 8).append(".");
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }

}


















