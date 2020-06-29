/**
 * @Project base-exception
 * @Package com.cds.base.exception.server
 * @Class DAOException.java
 * @Date Jun 29, 2020 10:41:08 AM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.exception.server;

import com.cds.base.common.exception.BasicException;

/**
 * @Description DAO异常
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 29, 2020 10:41:08 AM
 */
public class DAOException extends BasicException {

    private static final long serialVersionUID = 1L;

    /**
     * 创建新的DAOException实例
     *
     * @param message
     */
    public DAOException(String message) {
        super(message);
    }

}
