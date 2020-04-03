/*
 * Copyright 2013-2015 duolabao.com All Right Reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.cds.base.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

/**
 * 类XSSUtil.java的实现描述：防止XSS注入工具类
 * 
 * @author george 2015年8月26日 下午7:16:52
 */
public class XSSUtils {

    /**
     * 将Bean中 属性为String的进行敏感替换
     * 
     * @author zhiguo.zhang
     * @since 2013-7-4 下午5:36:53
     * @param entity
     * @throws Exception
     */
    public static void replaceEntity(Object entity) throws Exception {
        Class<?> demo = entity.getClass();
        if (demo != null) {
            Field[] declaredField = demo.getDeclaredFields();
            replaceField(entity, declaredField);
            Field[] field = demo.getFields();
            replaceField(entity, field);
        }
    }

    /**
     * 将对象中指定的属性可能存在注入的信息替换
     * 
     * @param entity
     * @param field
     * @throws Exception
     */
    public static void replaceField(Object entity, Field[] field) throws Exception {
        Class<?> demo = entity.getClass();
        if (demo != null) {
            for (int i = 0; i < field.length; i++) {
                // 属性类型
                Class<?> type = field[i].getType();
                if (type.equals(String.class)) {
                    String methodName = "set" + toUpFirst(field[i].getName());
                    String getName = "get" + toUpFirst(field[i].getName());
                    Method me = demo.getMethod(methodName, String.class);
                    Method me1 = demo.getMethod(getName);
                    String fieldValue = (String) me1.invoke(entity);
                    if (!StringUtils.isEmpty(fieldValue)) {
                        fieldValue = repalceValideParam(fieldValue.toLowerCase());
                        me.invoke(entity, fieldValue);
                    }
                }
            }
        }

    }

    public static String toUpFirst(String arg) {
        if (StringUtils.isEmpty(arg)) return "";
        return (arg.charAt(0) + "").toUpperCase() + arg.substring(1);
    }

    public static String repalceValideParam(String a) {
        if (StringUtils.isEmpty(a)) {
            return a;
        }
        a = a.replace("#", "&#x26;");
        a = a.replace(">", "&#x3E;");
        a = a.replace("<", "&#x3C;");
        // a = a.replace(" ", "");
        a = a.replace("\"", "&#x34;");
        a = a.replace(",", "");
        return a;
    }
}
