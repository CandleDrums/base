/**
 * @Project base-util
 * @Package com.cds.base.util.http
 * @Class HttpUtils.java
 * @Date Jan 16, 2020 4:50:53 PM
 * @Copyright (c) 2020 YOUWE All Right Reserved.
 */
package com.cds.base.util.http;

import java.io.IOException;
import java.util.Map;

import com.cds.base.util.bean.BeanUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description Http工具
 * @Notes 未填写备注
 * @author liming
 * @Date Jan 16, 2020 4:50:53 PM
 * @version 1.0
 * @since JDK 1.8
 */
public class HttpUtils {
    /**
     * 
     * @description 获取html
     * @return String
     */
    public static String getHtml(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> getParamsMap(Object value) {
        return BeanUtils.conventToMap(value);
    }

    /**
     * @description 拼接参数链接
     * @return String
     */
    public static String getParamsUrl(Object value) {
        if (value == null) {
            return "";
        }
        Map<String, Object> map = BeanUtils.conventToMap(value);
        if (map == null || map.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder("?");
        for (String key : map.keySet()) {
            sb.append(key + "=" + map.get(key).toString() + "&");
        }
        if (sb.length() > 1) {
            String params = sb.toString();
            return params.substring(0, params.length() - 1);
        }
        return "";
    }
}
