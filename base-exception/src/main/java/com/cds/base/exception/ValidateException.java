package com.cds.base.exception;

/**
 * 展现层验证异常，类型为<code>Exception</code>，此类异常作校验失败后抛出
 * 
 * @author ming.li <a href="http://g21121.iteye.com">iteye blog</a>
 * @version 1.0
 * @since JDK 1.6
 */
public class ValidateException extends BasicException {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a new instance ValidateException.
	 *
	 * @param code
	 */
	public ValidateException(String message) {
		super(message);
	}
}
