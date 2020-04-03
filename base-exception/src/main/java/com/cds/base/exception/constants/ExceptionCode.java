/**
 * @Project base-exception
 * @Package com.cds.base.exception.constants
 * @Class ExceptionCode.java
 * @Date 2018年2月23日 下午2:50:17
 * @Copyright (c) YOUWE All Right Reserved.
 */
package com.cds.base.exception.constants;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author ming.li
 * @Date 2018年2月23日 下午2:50:17
 * @version 1.0
 * @since JDK 1.8
 */
public class ExceptionCode {

    /**
     * ValidateException
     */
    // 参数为必填项
    public static final String PARAMETER_IS_REQUIRED  = "PARAMETER_IS_REQUIRED";
    /**
     * DAOException
     */
    // 存在多条记录
    public static final String MULTI_RECORD_EXCEPTION = "MULTI_RECORD_EXCEPTION";
    /**
     * LoginException
     */
    // 用户不存在
    public static final String USER_IS_NOT_EXIST      = "USER_IS_NOT_EXIST";
    // 用户名或密码错误
    public static final String PASSWD_IS_INCORRECT    = "PASSWD_IS_INCORRECT";
    // 所属应用无权限登录
    public static final String APP_IS_INCORRECT       = "APP_IS_INCORRECT";

}
