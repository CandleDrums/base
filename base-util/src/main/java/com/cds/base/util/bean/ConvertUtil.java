/*
 * Copyright 2013-2015 duolabao.com All right reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */

package com.cds.base.util.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.cds.base.util.misc.DateUtils;

/**
 * 类ConvertUtils.java的实现描述：类转换工具
 * 
 * @author george 2015年8月25日 下午3:25:21
 */
public abstract class ConvertUtil {

	public enum Type {
		BOOLEAN(new String[] { "boolean", "java.lang.boolean" }),

		STRING(new String[] { "string", "java.lang.string" }),

		INT(new String[] { "int", "integer", "java.lang.integer" }),

		LONG(new String[] { "long", "java.lang.long" }),

		BIGINT(new String[] { "bigint", "biginteger", "java.math.biginteger" }),

		DOUBLE(new String[] { "double", "java.lang.double" }),

		DATE(new String[] { "date", "java.util.date" }),

		DECIMAL(new String[] { "decimal", "java.math.bigdecimal" });

		private String[] names;

		private Type(String[] names) {
			this.names = names;
		}

		public static Type parseType(String name) {
			if (name != null) {
				name = name.toLowerCase();
			}
			for (Type type : Type.values()) {
				for (String tname : type.names) {
					if (tname.equals(name)) {
						return type;
					}
				}
			}
			return null;
		}

		@SuppressWarnings("rawtypes")
		public static Type parseType(Class clz) {
			if (clz == null) {
				return null;
			}
			return parseType(clz.getName());
		}
	}

	public static Object convert(String toType, String value) {
		Type type = Type.parseType(toType);
		if (type == null) {
			throw new RuntimeException("unsupport type : " + toType);
		}
		return convert(type, value);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object convert(Class toType, String value) {
		if (toType != null && toType.isEnum()) {
			try {
				return Enum.valueOf(toType, value);
			} catch (Exception e) {
			}
		}

		Type type = Type.parseType(toType);
		if (type == null) {
			throw new RuntimeException("unsupport type : " + toType);
		}
		return convert(type, value);
	}

	public static Object convert(Type toType, String value) {
		if (Type.STRING.equals(toType)) {
			return value;
		}
		if (CheckUtils.isEmpty(value)) {
			return null;
		}
		switch (toType) {
		case INT:
			return Integer.parseInt(value);
		case LONG:
			return Long.parseLong(value);
		case BIGINT:
			return new BigInteger(value);
		case DOUBLE:
			return Double.parseDouble(value);
		case DATE:
			return DateUtils.parseDate(value, DateUtils.YYYY_MM_DD_HH_MM_SS);
		case BOOLEAN:
			return Boolean.parseBoolean(value);
		case DECIMAL:
			return new BigDecimal(value);
		default:
			throw new RuntimeException("unsupport type : " + toType);
		}
	}
}
