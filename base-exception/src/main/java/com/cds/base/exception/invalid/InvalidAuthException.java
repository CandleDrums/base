/**
 * @Project base-exception
 * @Package com.cds.base.exception.invalid
 * @Class InvalidAuthException.java
 * @Date Jun 29, 2020 10:37:15 AM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.exception.invalid;

import com.cds.base.common.exception.BasicException;

/**
 * @Description 权限异常
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 29, 2020 10:37:15 AM
 */
public class InvalidAuthException extends BasicException {

    private static final long serialVersionUID = 5659934162853899044L;

    /**
     * 创建新的InvalidAuthException实例
     *
     * @param message
     */
    protected InvalidAuthException(String message) {
        super("401", message);
    }
}
