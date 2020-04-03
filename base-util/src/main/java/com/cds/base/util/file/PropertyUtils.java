package com.cds.base.util.file;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Description 资源文件工具类
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 18, 2019 3:36:10 PM
 * @version 1.0
 * @since JDK 1.8
 */
public class PropertyUtils {

	private static String GET = "get";

	private static String IS = "is";

	@SuppressWarnings("rawtypes")
	private static Map descriptorsCache = new HashMap();

	/**
	 * 属性注入
	 * 
	 * @param object   被注入对象
	 * @param property 属性名
	 * @param value    属性值
	 * @return
	 */
	public static boolean setProperty(Object object, String property, Object value) {
		if ((property == null) || (object == null)) {
			return false;
		}
		try {
			PropertyDescriptor pd = getPropertyDescriptor(object.getClass(), property);
			if (pd == null) {
				return false;
			}
			pd.getWriteMethod().invoke(object, new Object[] { value });
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取对象属性值
	 * 
	 * @param object   对象
	 * @param property 属性名
	 * @return
	 */
	public static Object getProperty(Object object, String property) {
		return invokeProperty(object, property);
	}

	/**
	 * Convert property name into getProperty name ( "something" -> "getSomething" )
	 */
	private static String createMethodName(String prefix, String propertyName) {
		return prefix + propertyName.toUpperCase().charAt(0) + propertyName.substring(1);
	}

	/**
	 * Invoke the method/field getter on the Object. It tries (in order)
	 * obj.getProperty(), obj.isProperty(), obj.property(), obj.property.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object invokeProperty(Object obj, String property) {
		if ((property == null) || (property.length() == 0)) {
			return null; // just in case something silly happens.
		}
		Class cls = obj.getClass();
		Object[] oParams = {};
		Class[] cParams = {};
		try {
			// First try object.getProperty()
			Method method = cls.getMethod(createMethodName(GET, property), cParams);

			return method.invoke(obj, oParams);
		} catch (Exception e1) {
			try {
				// First try object.isProperty()
				Method method = cls.getMethod(createMethodName(IS, property), cParams);
				return method.invoke(obj, oParams);
			} catch (Exception e2) {
				try {
					// Now try object.property()
					Method method = cls.getMethod(property, cParams);

					return method.invoke(obj, oParams);
				} catch (Exception e3) {
					try {
						// Now try object.property()
						Field field = cls.getField(property);

						return field.get(obj);
					} catch (Exception e4) {
						// oh well
						return null;
					}
				}
			}
		}
	}

	/**
	 * 获取对象的所有属性描述
	 * 
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PropertyDescriptor[] getPropertyDescriptors(Class beanClass) {
		if (beanClass == null) {
			throw new IllegalArgumentException("No bean class specified");
		}
		// Look up any cached descriptors for this bean class
		PropertyDescriptor descriptors[] = null;
		descriptors = (PropertyDescriptor[]) descriptorsCache.get(beanClass);
		if (descriptors != null) {
			return (descriptors);
		}

		// Introspect the bean and cache the generated descriptors
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		} catch (IntrospectionException e) {
			return (new PropertyDescriptor[0]);
		}
		descriptors = beanInfo.getPropertyDescriptors();
		if (descriptors == null) {
			descriptors = new PropertyDescriptor[0];
		}
		descriptorsCache.put(beanClass, descriptors);
		return (descriptors);
	}

	/**
	 * 获取对象属性描述
	 * 
	 * @param clz
	 * @param name
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("rawtypes")
	public static PropertyDescriptor getPropertyDescriptor(Class clz, String name)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (name == null) {
			throw new IllegalArgumentException("No property name specified");
		}
		PropertyDescriptor descriptors[] = getPropertyDescriptors(clz);
		if (descriptors != null) {
			for (int i = 0; i < descriptors.length; i++) {
				if (name.equals(descriptors[i].getName()))
					return (descriptors[i]);
			}
		}
		return null;
	}

	/**
	 * 获取CLASS属性类型
	 * 
	 * @param clz
	 * @param propertyName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class getPropertyType(Class clz, String propertyName) {
		PropertyDescriptor desc;
		try {
			desc = getPropertyDescriptor(clz, propertyName);
		} catch (Exception e) {
			return null;
		}
		if (desc == null) {
			return null;
		} else {
			return desc.getPropertyType();
		}
	}

	@SuppressWarnings("rawtypes")
	public static Class[] getPropertyGenericActualTypes(Class clz, String propertyName) {
		PropertyDescriptor desc;
		try {
			desc = getPropertyDescriptor(clz, propertyName);
		} catch (Exception e) {
			return null;
		}
		Type type = desc.getReadMethod().getGenericReturnType();
		if (type == null) {
			return null;
		}
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			Type[] types = pt.getActualTypeArguments();
			if (types != null) {
				Class[] clzs = new Class[types.length];
				for (int i = 0; i < types.length; i++) {
					clzs[i] = (Class) types[i];
				}
				return clzs;
			}
		}
		return null;
	}

	/**
	 * 获取CLASS所有属性名称
	 * 
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String[] getPropertyNames(Class clz) {
		try {
			PropertyDescriptor[] properties = getPropertyDescriptors(clz);
			String[] result = new String[properties.length];
			for (int i = 0; i < properties.length; i++) {
				result[i] = properties[i].getName();
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

}
