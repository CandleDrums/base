
package com.cds.base.util.bean;

import java.beans.BeanInfo;
import java.beans.Beans;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtilsBean;

import com.cds.base.util.file.PropertyUtils;

/**
 * 
 * @Description bean工具
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 18, 2019 3:41:20 PM
 * @version 1.0
 * @since JDK 1.8
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BeanUtils {
    /**
     * @description 获取对象
     * @param from
     * @param toClass
     * @return
     * @returnType Object
     * @author liming
     */
    public static <From, To> To getObject(From from, Class<To> clazz) {
        if (CheckUtils.isEmpty(from)) {
            return null;
        }
        try {
            To to = clazz.newInstance();
            copyProperties(from, to);
            return to;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 获取对象List
     * @param fromList
     * @param to
     * @return
     * @returnType List<To>
     * @author liming
     */
    public static <From, To> List<To> getObjectList(List<From> fromList, Class<To> clazz) {
        if (CheckUtils.isEmpty(fromList))
            return null;
        List<To> toList = new ArrayList<To>();
        for (From from : fromList) {
            To to = getObject(from, clazz);
            if (CheckUtils.isNotEmpty(to)) {
                toList.add(to);
            }
        }
        return toList;
    }

    /**
     * 将source的所有对象copy到target中
     * 
     * @param source
     *            原对象列表
     * @param target
     *            目标对象列表
     * @param descClazz
     *            目标对象类型
     */
    public static void copyListProperties(List source, List target, Class descClazz) {
        for (Object o : source) {
            try {
                Object d = descClazz.newInstance();
                copyProperties(o, d);
                target.add(d);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 将source对象的属性指copy到target中
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        String[] properties = getPropertyNames(source);
        copyProperties(source, target, properties, true, true);
    }

    /**
     * 将source对象的属性指copy到target中
     * 
     * @param source
     *            原对象
     * @param target
     *            目标对象
     * @param properties
     *            要拷贝的属性列表
     */
    public static void copyProperties(Object source, Object target, String[] properties) {
        copyProperties(source, target, properties, true, true);
    }

    /**
     * 将source对象的属性指copy到target中
     * 
     * @param source
     *            原对象
     * @param target
     *            目标对象
     * @param convertType
     *            是否需要自动转换类型
     * @param ignoreNullValue
     *            是否自动忽略NULL值
     */
    public static void copyProperties(Object source, Object target, boolean convertType, boolean ignoreNullValue) {
        String[] properties = getPropertyNames(source);
        copyProperties(source, target, properties, convertType, ignoreNullValue);
    }

    /**
     * 将source对象的属性指copy到target中
     * 
     * @param source
     *            原对象
     * @param target
     *            目标对象
     * @param properties
     *            要拷贝的属性列表
     * @param convertType
     *            是否需要自动转换类型
     * @param ignoreNullValue
     *            是否自动忽略NULL值
     */
    public static void copyProperties(Object source, Object target, String[] properties, boolean convertType,
        boolean ignoreNullValue) {
        Map valueMap = getProperties(source, properties);

        Iterator keys = valueMap.keySet().iterator();
        while (keys.hasNext()) {
            String property = keys.next().toString();
            Object value = valueMap.get(property);
            copyProperty(target, property, value, convertType, ignoreNullValue);
        }
    }

    /**
     * bean转map
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public static Map<String, Object> conventAllToMap(Object... params) {
        Map<String, Object> param = new HashMap<String, Object>();
        for (Object obj : params) {
            if (obj instanceof Map) {
                param.putAll((Map<String, Object>)obj);
                continue;
            }
            Class<? extends Object> clazz = obj.getClass();
            Method[] methods = clazz.getMethods();
            Pattern pattern = Pattern.compile("^get*");
            Matcher matcher = null;
            for (Method m : methods) {
                matcher = pattern.matcher(m.getName());
                if (matcher.find()) {
                    String key = m.getName().replace("get", "");
                    key = (key.charAt(0) + "").toLowerCase().concat(key.substring(1));
                    try {
                        Object res = m.invoke(obj);
                        param.put(key, res);
                    } catch (Exception e) {
                    }
                }
            }
        }
        return param;
    }

    /**
     * 将非空的属性转换到map中
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public static Map<String, Object> conventToMap(Object... params) {
        Map<String, Object> param = new HashMap<String, Object>();
        for (Object obj : params) {
            Class<? extends Object> clazz = obj.getClass();
            Method[] methods = clazz.getMethods();
            Pattern pattern = Pattern.compile("^get*");
            Matcher matcher = null;
            for (Method m : methods) {
                matcher = pattern.matcher(m.getName());
                if (matcher.find()) {
                    String key = m.getName().replace("get", "");
                    key = (key.charAt(0) + "").toLowerCase().concat(key.substring(1));
                    Object res;
                    try {
                        res = m.invoke(obj);
                        if (res != null) {
                            param.put(key, res);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return param;
    }

    /**
     * 将bean转换到map,不包含class属性
     * 
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> convertBeanToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将obj对应的property属性的值设置为 value
     * 
     * @param obj
     *            对象
     * @param property
     *            属性
     * @param value
     *            属性值
     * @param convertType
     *            自动转换类型
     * @param ignoreNullValue
     *            是否自动忽略null
     * @return
     */
    private static boolean copyProperty(Object obj, String property, Object value, boolean convertType,
        boolean ignoreNullValue) {
        if (obj == null) {
            throw new IllegalArgumentException("no bean specified");
        }
        if (property == null) {
            throw new IllegalArgumentException("no property specified");
        }
        if (ignoreNullValue && value == null) {
            return true;
        }

        if (obj instanceof Map) {
            return invokeSetter(obj, property, value, convertType, ignoreNullValue);
        }

        StringTokenizer st = new StringTokenizer(property, ".");
        if (st.countTokens() == 0) {
            return false;
        }

        Object current = obj;
        try {
            while (st.hasMoreTokens()) {
                String currentPropertyName = st.nextToken();
                if (st.hasMoreTokens()) {
                    current = invokeGetter(current, currentPropertyName);
                } else {
                    try {
                        invokeSetter(current, currentPropertyName, value, convertType, ignoreNullValue);
                    } catch (Exception e) {
                        return false;
                    }
                }
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * 获得obj对象property属性的值
     * 
     * @param obj
     *            对象
     * @param property
     *            属性
     * @return
     */
    public static Object getProperty(Object obj, String property) {
        if (obj == null) {
            throw new IllegalArgumentException("no bean specified");
        }
        if (property == null) {
            throw new IllegalArgumentException("no property specified");
        }

        if (obj instanceof Map) {
            return ((Map)obj).get(property);
        }

        StringTokenizer st = new StringTokenizer(property, ".");
        if (st.countTokens() == 0) {
            return null;
        }

        Object result = obj;

        try {
            while (st.hasMoreTokens()) {
                String currentPropertyName = st.nextToken();
                if (result != null) {
                    result = PropertyUtils.getProperty(result, currentPropertyName);
                }
            }
            return result;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException {

        BeanUtilsBean.getInstance().setProperty(bean, name, value);
    }

    /**
     * 获取obj对象的properties属性的值
     * 
     * @param obj
     * @param properties
     * @return
     */
    private static Map getProperties(Object obj, String[] properties) {
        if (obj == null) {
            throw new IllegalArgumentException("no bean specified");
        }
        if (properties == null) {
            throw new IllegalArgumentException("no priperties specified");
        }
        Map result = new LinkedHashMap();
        for (int i = 0; i < properties.length; i++) {
            Object value = getProperty(obj, properties[i]);
            result.put(properties[i], value);
        }
        return result;
    }

    /**
     * get property from object
     * 
     * @param obj
     *            target object
     * @param property
     *            target property
     * @return
     */
    private static Object invokeGetter(Object obj, String property) {
        if (obj instanceof Map) {
            return ((Map)obj).get(property);
        } else {
            return PropertyUtils.getProperty(obj, property);
        }
    }

    /**
     * inject value into object
     * 
     * @param obj
     *            target object
     * @param property
     *            target property
     * @param value
     *            injected object
     * @param autoConvert
     *            是否需要自动转换类型
     * @param ingoreNullValue
     *            是否自动忽略NULL值
     * @return
     */
    private static boolean invokeSetter(Object target, String property, Object value, boolean autoConvert,
        boolean ingoreNullValue) {
        if (target instanceof Map) {
            ((Map)target).put(property, value);
            return true;
        }
        Object newValue = null;
        if (autoConvert) {
            Class type = PropertyUtils.getPropertyType(target.getClass(), property);
            if (Beans.isInstanceOf(value, type)) {
                newValue = value;
            } else if (value instanceof String) {
                newValue = ConvertUtil.convert(type, (String)value);
            } else {
                newValue = value;
            }
        } else {
            newValue = value;
        }
        if (!ingoreNullValue || CheckUtils.isNotEmpty(newValue)) {
            boolean r = PropertyUtils.setProperty(target, property, newValue);
            return r;
        }
        return true;
    }

    /**
     * 获取所有属性名称
     * 
     * @param obj
     * @return
     */
    private static String[] getPropertyNames(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("no bean specified");
        }
        if (obj instanceof Map) {
            Object[] keys = ((Map)obj).keySet().toArray();
            String[] results = new String[keys.length];
            for (int i = 0; i < keys.length; i++) {
                results[i] = keys[i] + "";
            }
            return results;
        }
        String[] result = PropertyUtils.getPropertyNames(obj.getClass());
        int index = Arrays.binarySearch(result, "class");

        if (index > 0) {
            if (result.length == 1) {
                return new String[0];
            }
            String[] newResult = new String[result.length - 1];
            System.arraycopy(result, 0, newResult, 0, index);
            if (index < result.length) {
                System.arraycopy(result, index + 1, newResult, index, result.length - index - 1);
            }
            return newResult;
        } else {
            return result;
        }
    }

    /**
     * 该方法过滤掉了class属性，可用于dubbo传输
     * 
     * @param obj
     * @return
     */
    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return map;

    }

    /**
     * 
     * @description TODO 填写描述信息
     * @return Map<String,To>
     */
    public static <To> Map<String, To> converMapEntryType(Map<Object, Object> map, Class<To> toClass) {
        if (map == null) {
            return null;
        }
        Map<String, To> resultMap = new HashMap<String, To>();
        for (Object key : map.keySet()) {
            resultMap.put(String.valueOf(key), getObject(map.get(key), toClass));
        }
        return resultMap;
    }
}
