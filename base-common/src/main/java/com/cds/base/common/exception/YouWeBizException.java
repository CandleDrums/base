package com.cds.base.common.exception;

import java.text.MessageFormat;

/**
 * description: 业务异常基类，所有业务异常都必须继承于此异常
 *
 * @author Careerly
 * @version V1.0
 * @date 2018/8/10 下午3:52
 */
public class YouWeBizException extends RuntimeException {

    private static final long serialVersionUID = -5097768787801034398L;

    public static final String DEFAULT_FAIL_CODE = "1";

    /**
     * 异常ID，用于表示某一异常实例，每一个异常实例都有一个唯一的异常ID
     */
    protected String id;

    /**
     * 异常信息，包含必要的上下文业务信息，用于打印日志
     */
    protected String message;

    /**
     * 具体异常码，即异常码code的后3位，由各具体异常实例化时自己定义
     */
    private String defineCode;

    protected String realClassName;

    public YouWeBizException(String message) {
        super();
        this.defineCode = DEFAULT_FAIL_CODE;
        this.message = message;

    }

    protected YouWeBizException(String defineCode, String message) {
        super();
        this.defineCode = defineCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message, Object... args) {
        this.message = MessageFormat.format(message, args);
    }

    public String getDefineCode() {
        return defineCode;
    }

}
