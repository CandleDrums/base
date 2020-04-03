/**
 * @Project common.util
 * @Package com.cds.common.util
 * @Class CheckUtils.java
 * @Date 2016年8月18日 下午4:09:49
 * @Copyright (c) 2016 Rising Moon All Right Reserved.
 */
package com.cds.base.util.bean;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.cds.base.exception.ValidateException;

/**
 * @Description 校验工具
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年8月18日 下午4:09:49
 * @version 1.0
 * @since JDK 1.8
 */
public class CheckUtils {

	/**
	 * 判断参数是否非NULL,空字符串，空数组，空的Collection或Map(只有空格的字符串也认为是空串)
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object... objs) {
		if (objs == null) {
			return false;
		}
		for (Object obj : objs) {
			if (obj == null) {
				return true;
			}
			if (obj instanceof String && obj.toString().trim().length() == 0) {
				return true;
			}
			if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
				return true;
			}
			if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
				return true;
			}
			if (obj instanceof Map && ((Map) obj).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @description 判断非空
	 * @param obj
	 * @return
	 * @returnType boolean
	 * @author liming
	 */
	public static boolean isNotEmpty(Object... objs) {
		return !isEmpty(objs);
	}

	/**
	 * @description list只有一个成员
	 * @return boolean
	 */
	public static boolean listMemeberIsSingle(List list) {
		if (isNotEmpty(list)) {
			if (list.size() == 1) {
				if (list.get(0) != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否是基本类型及包装类 true:是,false:否
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean isPrimitive(Object obj) {
		if (obj.getClass().isPrimitive()) {
			return true;
		}
		if (obj instanceof Long || obj instanceof Integer || obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Double) {
			return true;
		}
		return false;
	}

	/**
	 * @description 判断是否是基本类型及包装类
	 * @param obj
	 * @return
	 * @returnType boolean
	 * @author liming
	 */
	public static boolean isNotPrimitive(Object obj) {
		return !isPrimitive(obj);
	}

	/**
	 * @description 验证有效值
	 * @Note 0、0.0f、0.00d、0L 等为无效值
	 * @param objs
	 * @return
	 * @returnType boolean
	 * @author liming
	 */
	public static boolean isValidValue(Object... objs) {
		if (isEmpty(objs)) {
			return false;
		}
		return false;
	}

	/**
	 * 
	 * @description 非有效值
	 * @return boolean
	 */
	public static boolean isNotValidValue(Object... objs) {
		return !isValidValue(objs);
	}

	/**
	 * 验证某个bean的参数
	 *
	 * @param object 被校验的参数
	 * @throws ValidationException 如果参数校验不成功则抛出此异常
	 */
	public static <T> void validate(T object) throws ValidateException {
		// 获得验证器
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		// 执行验证
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
		// 如果有验证信息，则取出来包装成异常返回
		if (isEmpty(constraintViolations)) {
			return;
		}
		throw new ValidateException(convertErrorMsg(constraintViolations));
	}

	/**
	 * 转换异常信息
	 * 
	 * @param set
	 * @param     <T>
	 * @return
	 */
	private static <T> String convertErrorMsg(Set<ConstraintViolation<T>> set) {
		Map<String, StringBuilder> errorMap = new HashMap<>();
		String property;
		for (ConstraintViolation<T> cv : set) {
			// 这里循环获取错误信息，可以自定义格式
			property = cv.getPropertyPath().toString();
			if (errorMap.get(property) != null) {
				errorMap.get(property).append("," + cv.getMessage());
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append(cv.getMessage());
				errorMap.put(property, sb);
			}
		}
		return errorMap.toString();
	}
}
