package com.cds.base.common.exception;

/**
 * @Description 业务异常
 * @Notes 未填写备注
 * @author Administrator
 * @Date 2017年12月21日 上午10:44:18
 * @version 1.0
 * @since JDK 1.8
 */
public class BusinessException extends BasicException {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance BusinessException.
     *
     * @param code
     */
    public BusinessException(String message) {
        super(message);
    }

}
