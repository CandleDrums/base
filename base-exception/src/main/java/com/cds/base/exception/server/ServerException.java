/**
 * @Project base-exception
 * @Package com.cds.base.exception.server
 * @Class ServerException.java
 * @Date Jul 7, 2020 2:17:36 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.exception.server;

import com.cds.base.common.exception.BasicException;

/**
 * @Description 服务异常
 * @Notes 未填写备注
 * @author liming
 * @Date Jul 7, 2020 2:17:36 PM
 */
public class ServerException extends BasicException {

    private static final long serialVersionUID = 1L;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String code, String message) {
        super(code, message);
    }
}
