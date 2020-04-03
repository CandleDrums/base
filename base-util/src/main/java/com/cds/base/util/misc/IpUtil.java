package com.cds.base.util.misc;

/**
 * @Description IP工具类
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年9月6日 下午6:48:08
 * @version 1.0
 * @since JDK 1.8
 */
public class IpUtil {

    public static boolean isIp(String ip) {
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                       + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        if (ip.matches(regex)) {
            return true;
        }
        return false;
    }

}
