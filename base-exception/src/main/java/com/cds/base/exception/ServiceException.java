package com.cds.base.exception;

/**
 * @Description 服务层异常
 * @Notes 未填写备注
 * @author Administrator
 * @Date 2017年12月21日 上午10:45:28
 * @version 1.0
 * @since JDK 1.8
 */
public class ServiceException extends BasicException {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance ServiceException.
     *
     * @param code
     */
    public ServiceException(String code){
        super(code);
    }

}
