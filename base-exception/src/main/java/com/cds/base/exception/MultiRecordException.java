/**
 * @Project base-exception
 * @Package com.cds.base.exception
 * @Class MultiRecordException.java
 * @Date Nov 4, 2019 1:49:37 PM
 * @Copyright (c) 2019 CandleDrums.com All Right Reserved.
 */
package com.cds.base.exception;

/**
 * @Description 多条重复记录异常
 * @Notes 未填写备注
 * @author liming
 * @Date Nov 4, 2019 1:49:37 PM
 * @version 1.0
 * @since JDK 1.8
 */
public class MultiRecordException extends BusinessException {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance MultiRecordException.
     *
     * @param message
     */
    public MultiRecordException(String message) {
        super(message);
    }

}
