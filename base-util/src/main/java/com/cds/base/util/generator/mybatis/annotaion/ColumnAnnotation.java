/**
 * @Project base-server-dal
 * @Package com.cds.base.server.dal.mapper.generator
 * @Class ColumnAnnotation.java
 * @Date 2017年11月20日 下午4:00:13
 * @Copyright (c) 2019 YOUWE All Right Reserved.
 */
package com.cds.base.util.generator.mybatis.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 列注解
 * @Notes 未填写备注
 * @author liming
 * @Date 2017年11月20日 下午4:00:13
 * @version 1.0
 * @since JDK 1.7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Inherited
public @interface ColumnAnnotation {

    /**
     * 列名
     * 
     * @return
     */
    String columnName();

    /**
     * 列备注
     * 
     * @return
     */
    String columnAnno() default "";

    /**
     * 数据库类型
     * 
     * @return
     */
    String dbType();

    /**
     * 显示长度
     * 
     * @return
     */
    int length() default 32;

    /**
     * 是否为空
     * 
     * @return
     */
    boolean isNull() default true;

    /**
     * 是否唯一
     * 
     * @return
     */
    boolean isUnique() default false;
}
