/**
 * @Project base-exception
 * @Package com.cds.base.exception.server
 * @Class ServiceException.java
 * @Date 2020年12月28日 下午6:39:32
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.exception.server;

import com.cds.base.common.exception.BasicException;

/**
 * @Description 俯卧撑异常
 * @Notes 未填写备注
 * @author liming
 * @Date 2020年12月28日 下午6:39:32
 */
public class ServiceException extends BasicException {

    private static final long serialVersionUID = 1L;

    /**
     * 创建新的DAOException实例
     *
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }

}