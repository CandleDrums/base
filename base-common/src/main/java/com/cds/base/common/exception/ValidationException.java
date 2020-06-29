/**
 * @Project base-common
 * @Package com.cds.base.common.exception
 * @Class ValidationException.java
 * @Date Jun 29, 2020 10:35:58 AM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.common.exception;

/**
 * @Description 验证异常
 * @Notes 未填写备注
 * @author liming
 * @Date Jun 29, 2020 10:35:58 AM
 */
public class ValidationException extends BasicException {

    private static final long serialVersionUID = 1L;

    /**
     * 创建新的ValidationException实例
     *
     * @param message
     */
    public ValidationException(String message) {
        super(message);
    }
}
