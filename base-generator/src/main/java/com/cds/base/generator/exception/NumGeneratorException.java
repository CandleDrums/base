/**
 * @Project base-generator
 * @Package com.cds.base.generator.exception
 * @Class NumGeneratorException.java
 * @Date Oct 20, 2020 4:14:24 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.generator.exception;

import com.cds.base.common.exception.BasicException;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 20, 2020 4:14:24 PM
 */
public class NumGeneratorException extends BasicException {

    private static final long serialVersionUID = 1L;

    /**
     * 创建新的NumGeneratorException实例
     *
     * @param message
     */
    public NumGeneratorException(String message) {
        super(message);
    }

    public NumGeneratorException(String code, String message) {
        super(message);
    }
}
