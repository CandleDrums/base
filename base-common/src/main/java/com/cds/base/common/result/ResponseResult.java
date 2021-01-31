/**
 * @Project base-common
 * @Package com.cds.base.common.result
 * @Class ResponseResult.java
 * @Date 2019年9月12日 上午11:47:57
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.common.result;

import java.io.Serializable;

import com.cds.base.common.code.ResultCode;

import lombok.Data;

/**
 * @Description 通用返回结果
 * @Notes 未填写备注
 * @author maoshou
 * @Date 2019年9月12日 上午11:47:57
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    // 结果码
    private String result;
    // 消息提示
    private String message;
    // 数据
    private T data;

    public static <T> boolean isSuccess(ResponseResult<T> result) {
        if (result == null || result.getResult() == null) {
            return false;
        }
        return result.getResult().equals(ResultCode.SUCCESS.name());
    }

    public static <T> boolean isNull(ResponseResult<T> result) {
        if (result == null || result.getResult() == null) {
            return true;
        }
        return result.getResult().equals(ResultCode.NULL.name());
    }

    /**
     * @description 返回成功结果
     * @return ResponseResult<T>
     */
    public static ResponseResult<Boolean> returnResult(Boolean success) {
        ResponseResult<Boolean> result = new ResponseResult<Boolean>();
        ResultCode rc = ResultCode.SUCCESS;

        if (!success) {
            rc = ResultCode.FAIL;
        }
        result.setResult(rc.getCode());
        result.setMessage(rc.getDisplayName());
        result.setData(success);
        return result;
    }

    /**
     * @description 返回自定义结果
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> returnResult(String resultCode, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setResult(resultCode);
        result.setMessage(message);
        return result;
    }

    /**
     * @description 返回成功结果
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> returnSuccess(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setResult(ResultCode.SUCCESS.name());
        result.setMessage(ResultCode.SUCCESS.getDisplayName());
        result.setData(data);
        return result;
    }

    /**
     * @description 返回失败结果
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> returnFail(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setResult(ResultCode.FAIL.name());
        result.setMessage(ResultCode.FAIL.getDisplayName());
        result.setData(data);
        return result;
    }

    /**
     * @description 返回失败结果
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> returnFail(T data, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setResult(ResultCode.FAIL.name());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * @description 返回自定义结果
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> returnResult(T data, String resultCode, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setResult(resultCode);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * @description 返回错误结果
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> returnError(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setResult(ResultCode.ERROR.name());
        result.setMessage(ResultCode.ERROR.getDisplayName());
        result.setData(data);
        return result;
    }

    /**
     * @description 返回无数据结果
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> returnNull(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setResult(ResultCode.NULL.name());
        result.setMessage(ResultCode.NULL.getDisplayName());
        result.setData(data);
        return result;
    }

    /**
     * @description 返回未知结果
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> returnUnknown(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setResult(ResultCode.UNKNOWN.name());
        result.setMessage(ResultCode.UNKNOWN.getDisplayName());
        result.setData(data);
        return result;
    }

}
