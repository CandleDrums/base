/**
 * @Project base-util
 * @Package com.cds.base.util.lang
 * @Class BigDecimalUtils.java
 * @Date Dec 9, 2019 4:03:14 PM
 * @Copyright (c) 2019 CandleDrums.com All Right Reserved.
 */
package com.cds.base.util.lang;

import java.math.BigDecimal;

import com.cds.base.common.type.CompareType;

/**
 * @Description BigDecimal工具类
 * @Notes 未填写备注
 * @author liming
 * @Date Dec 9, 2019 4:03:14 PM
 * @version 1.0
 * @since JDK 1.8
 */
public class BigDecimalUtils {

    /**
     * @description 判断大于零
     * @return boolean
     */
    public static boolean gtZero(BigDecimal a) {
        return CompareType.GTR == compare(a, BigDecimal.ZERO);
    }

    /**
     * @description 比较
     * @return CompareType
     */
    public static CompareType compare(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) {
            return CompareType.EQU;
        }
        if (a == null && b != null) {
            return CompareType.LSS;
        }
        if (a != null && b == null) {
            return CompareType.GTR;
        }
        if (a != null && b != null) {
            int result = a.compareTo(b);
            switch (result) {
                // 相等
                case 0: {
                    return CompareType.EQU;
                }
                // 大于
                case 1: {
                    return CompareType.GTR;
                }
                case -1: {
                    return CompareType.LSS;
                }
                default:
                    return CompareType.NEQ;
            }
        }
        return CompareType.NEQ;

    }

}
