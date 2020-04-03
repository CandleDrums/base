/**
 * @Project base-module
 * @Package com.cds.base.module.printer.data
 * @Class PrintResult.java
 * @Date Jan 6, 2020 11:30:58 AM
 * @Copyright (c) 2020 YOUWE All Right Reserved.
 */
package com.cds.base.module.printer.data;

import com.cds.base.common.type.ResultType;

import lombok.Data;

/**
 * @Description 打印结果
 * @Notes 未填写备注
 * @author liming
 * @Date Jan 3, 2020 1:55:23 PM
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class PrinterResult<T> {

    private T data;
    private ResultType result;
    private boolean success;
    private String message;

    /**
     * success
     *
     * @return success
     */

    public boolean isSuccess() {
        if (result == null) {
            return false;
        }
        return result != null ? result == ResultType.SUCCESS : false;
    }

}
