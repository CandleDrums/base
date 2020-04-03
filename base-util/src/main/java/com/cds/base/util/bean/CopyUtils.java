/**
 * @Project base.util
 * @Package come.candledrums.base.util
 * @Class CopyUtils.java
 * @Date 2016年8月11日 下午2:21:15
 * @Copyright (c) 2016 Rising Moon All Right Reserved.
 */
package com.cds.base.util.bean;

import org.dozer.DozerBeanMapper;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年8月11日 下午2:21:15
 * @version 1.0
 * @since JDK 1.8
 */
public class CopyUtils {

    private final static DozerBeanMapper mapper = new DozerBeanMapper();

    public static Object copyProperties(Object from, Object to) {
        mapper.map(from, to);
        return to;
    }

    public static void main(String[] args) {

    }

}
