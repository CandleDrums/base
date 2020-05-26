/**
 * @Project base-server-dal
 * @Package com.cds.base.server.dal.mapper.generator
 * @Class TableAnnotation.java
 * @Date 2017年11月20日 下午4:07:27
 * @Copyright (c) 2019 CandleDrums.com All Right Reserved.
 */
package com.cds.base.util.generator.mybatis.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 表注解
 * @Notes 未填写备注
 * @author liming
 * @Date 2017年11月20日 下午4:07:27
 * @version 1.0
 * @since JDK 1.7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface TableAnnotation {

    /**
     * 表名
     * 
     * @return
     */
    String value();
}
