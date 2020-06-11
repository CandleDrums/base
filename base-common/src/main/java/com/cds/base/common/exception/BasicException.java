/**
 * @Project base-exception
 * @Package com.cds.base.exception
 * @Class BasicException.java
 * @Date 2017年12月20日 下午7:00:19
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.common.exception;

import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * @Description 基础异常
 * @Notes 未填写备注
 * @author ming.li
 * @Date 2017年12月20日 下午7:00:19
 * @version 1.0
 * @since JDK 1.8
 */
public class BasicException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    /**
     * 异常ID，用于表示某一异常实例，每一个异常实例都有一个唯一的异常ID
     */
    protected String id;
    /**
     * 具体异常码，由各具体异常实例化时自己定义
     */
    protected String code;
    /**
     * 异常信息，包含必要的上下文业务信息，用于打印日志
     */
    protected String message;

    protected BasicException(String message) {
        super();
        this.message = message;
        initId();
    }

    private void initId() {
        this.id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }

    public String getId() {
        return id;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("exception id : ").append(id).append(",").append(message);
        return sb.toString();
    }

    public void setMessage(String message, Object... args) {
        this.message = MessageFormat.format(message, args);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends BasicException> T newException(T exception, String message, Object... args) {
        if (exception == null) {
            throw new RuntimeException("no exception instance specified");
        }
        try {
            Constructor constructor = exception.getClass().getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            T newException = (T)constructor.newInstance(exception.getcode());
            newException.setMessage(message, args);
            return newException;
        } catch (Throwable e) {
            throw new RuntimeException("create exception instance fail : " + e.getMessage(), e);
        }
    }

    /**
     * 比较异常的class和defineCode是否相同
     * 
     * @param e
     * @return
     */
    public boolean codeEquals(BasicException e) {
        if (e == null) {
            return false;
        }
        if (!e.getClass().equals(this.getClass())) {
            return false;
        }
        if (!e.getcode().equals(getcode())) {
            return false;
        }
        return true;
    }

    /**
     * code
     *
     * @return code
     * @since 1.0.0
     */

    public String getcode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setcode(String code) {
        this.code = code;
    }

    /**
     * serialversionuid
     *
     * @return serialversionuid
     * @since 1.0.0
     */

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
