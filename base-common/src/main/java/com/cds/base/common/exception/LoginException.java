/**
 * @Project base-exception
 * @Package com.cds.base.custom.exception
 * @Class LoginException.java
 * @Date 2018年1月31日 上午11:34:07
 * @Copyright (c) CandleDrumS.com All Right Reserved
 */
package com.cds.base.common.exception;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author ming.li
 * @Date 2018年1月31日 上午11:34:07
 * @version 1.0
 * @since JDK 1.8
 */
public class LoginException extends BusinessException {

    private static final long serialVersionUID = 1L;

    /**
     * Create a new instance LoginException.
     *
     * @param code
     */
    public LoginException(String code){
        super(code);
    }

}
