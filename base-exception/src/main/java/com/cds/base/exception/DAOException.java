/**
 * @Project base-exception
 * @Package com.cds.base.exception
 * @Class DAOException.java
 * @Date 2017年12月19日 下午6:47:44
 * @Copyright (c) 2019 YOUWE All Right Reserved.
 */
package com.cds.base.exception;

/**
 * @Description DAO异常
 * @Notes 一般用于错误删除等情况
 * @author Administrator
 * @Date 2017年12月19日 下午6:47:44
 * @version 1.0
 * @since JDK 1.8
 */
public class DAOException extends BasicException {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance DAOException.
     *
     * @param code
     */
    public DAOException(String code){
        super(code);
    }

}
