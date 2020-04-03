/**
 * @Project common.util
 * @Package com.cds.common.util
 * @Class JsonUtils.java
 * @Date 2016年9月6日 下午2:47:29
 * @Copyright (c) 2016 Rising Moon All Right Reserved.
 */
package com.cds.base.util.bean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年9月6日 下午2:47:29
 * @version 1.0
 * @since JDK 1.8
 */
public class JsonUtils<T> {

    /**
     * @description 获取json
     * @param o
     * @return
     * @returnType String
     * @author liming
     */
    public static <T> String getJson(T t) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @description 获取对象
     * @param json
     * @param clazz
     * @return
     * @returnType T
     * @author liming
     */
    public static <T> T getObject(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
