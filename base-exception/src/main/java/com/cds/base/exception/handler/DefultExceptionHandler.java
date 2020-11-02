/**
 * @Project base-exception
 * @Package com.cds.base.exception.handler
 * @Class DefultExceptionHandler.java
 * @Date Oct 30, 2020 6:32:58 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.exception.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cds.base.common.code.ResultCode;
import com.cds.base.common.exception.BasicException;
import com.cds.base.common.exception.BusinessException;
import com.cds.base.common.exception.ValidationException;
import com.cds.base.common.result.ResponseResult;

/**
 * @Description 默认异常处理
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 30, 2020 6:32:58 PM
 */
@RestControllerAdvice
public class DefultExceptionHandler {

    @ExceptionHandler(BasicException.class)
    public ResponseResult handleBasicException(BasicException e) {
        return ResponseResult.returnResult(ResultCode.FAIL.name(), e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseResult handleValidationException(ValidationException e) {
        return ResponseResult.returnResult(ResultCode.FAIL.name(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseResult handleBusinessException(BusinessException e) {
        return ResponseResult.returnResult(ResultCode.FAIL.name(), e.getMessage());
    }
}
