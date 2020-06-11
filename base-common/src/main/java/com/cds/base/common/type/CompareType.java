/**
 * @Project base-common
 * @Package com.cds.base.common.type
 * @Class CompareType.java
 * @Date Dec 9, 2019 4:04:49 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.common.type;

/**
 * @Description 比较类型
 * @Notes 未填写备注
 * @author liming
 * @Date Dec 9, 2019 4:04:49 PM
 * @version 1.0
 * @since JDK 1.8
 */
public enum CompareType {

    EQU("等于", 0), NEQ("不等于", 11), LSS("小于", -1), LEQ("小于或等于", -10), GTR("大于", 1), GEQ("大于或等于", 10);

    private final String desc;
    private final int value;

    private CompareType(String desc, int value) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * desc
     *
     * @return desc
     */

    public String getDesc() {
        return desc;
    }

    /**
     * value
     *
     * @return value
     */

    public int getValue() {
        return value;
    }

}
