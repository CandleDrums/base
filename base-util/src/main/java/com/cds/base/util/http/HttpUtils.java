/**
 * @Project base-util
 * @Package com.cds.base.util.http
 * @Class HttpUtils.java
 * @Date Jan 16, 2020 4:50:53 PM
 * @Copyright (c) 2020 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

    private static HttpURLConnection getConnection(URL url, String method, String ctype) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript");
        conn.setRequestProperty("User-Agent", "getop");
        conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }

    private static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    private static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    private static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;
        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            // 忽略参数名或参数值为空的参数
            if (areNotEmpty(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }
        return query.toString();
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url
     *            请求地址
     * @param params
     *            请求参数
     * @param charset
     *            字符集如UTF-8, GBK, GB2312
     * @return 响应字符串
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        InputStream in = null;
        String rsp = null;
        String charset = "UTF-8";
        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            conn = getConnection(new URL(url), "POST", ctype);
            out = conn.getOutputStream();
            if (params != null) {
                String query = buildQuery(params, charset);
                out.write(query.getBytes(charset));
            }
            in = conn.getInputStream();
            rsp = getResponseAsString(in, getResponseCharset(conn.getContentType()));
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    /**
     * POST请求，字符串形式数据
     *
     * @param url
     *            请求地址
     * @param param
     *            请求数据
     * @param charset
     *            编码方式
     */
    public static String sendPostStr(String url, String param, String charset) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url
     *            请求地址
     * @return 响应字符串
     * @throws IOException
     */
    public static String doGet(String url) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        InputStream in = null;
        String rsp = null;
        String charset = "UTF-8";
        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            conn = getConnection(new URL(url), "GET", ctype);
            out = conn.getOutputStream();
            in = conn.getInputStream();
            rsp = getResponseAsString(in, getResponseCharset(conn.getContentType()));
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("charset", "UTF-8");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    private static String getResponseAsString(InputStream in, String charset) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
        StringWriter writer = new StringWriter();
        char[] chars = new char[512];
        int count = 0;
        while ((count = reader.read(chars)) > 0) {
            writer.write(chars, 0, count);
        }
        return writer.toString();
    }

    private static String getResponseCharset(String ctype) {
        String charset = "UTF-8";
        if (!isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }
        return charset;
    }

    /**
     * 读取网络资源
     *
     * @throws IOException
     */
    public static String readHttpResource(String url, Map<String, String> param) throws IOException {
        HttpURLConnection httpConn = null;
        BufferedReader rd = null;
        try {
            StringBuilder sb = new StringBuilder();
            StringBuilder params = new StringBuilder();
            params.append("?");
            Set<String> keys = param.keySet();
            for (String key : keys) {
                params.append(key);
                params.append("=");
                params.append(param.get(key));
                params.append("&");
            }
            params.deleteCharAt(params.length() - 1);
            URL realUrl = new URL(url + params.toString());
            httpConn = (HttpURLConnection)realUrl.openConnection();
            int responseCode = httpConn.getResponseCode();
            if (responseCode == 200) {
                rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "gbk"));
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }
        } catch (IOException e) {
            System.out.println("url is :" + url);
            throw e;
        } finally {
            if (rd != null)
                try {
                    rd.close();
                } catch (Exception e) {
                }
            if (httpConn != null)
                try {
                    httpConn.disconnect();
                } catch (Exception e) {
                }
        }

        return null;
    }

    /**
     * 读取网络资源
     *
     * @throws IOException
     */
    public static String readHttpResource(String url, Map<String, String> param, String charset) throws IOException {
        HttpURLConnection httpConn = null;
        BufferedReader rd = null;
        try {
            StringBuilder sb = new StringBuilder();
            StringBuilder params = new StringBuilder("");
            if (param != null) {
                params.append("?");
                Set<String> keys = param.keySet();
                for (String key : keys) {
                    params.append(key);
                    params.append("=");
                    params.append(param.get(key));
                    params.append("&");
                }
                params.deleteCharAt(params.length() - 1);
            }
            URL realUrl = new URL(url + params.toString());
            httpConn = (HttpURLConnection)realUrl.openConnection();
            int responseCode = httpConn.getResponseCode();
            if (responseCode == 200) {
                rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), charset));
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (rd != null)
                try {
                    rd.close();
                } catch (Exception e) {
                }
            if (httpConn != null)
                try {
                    httpConn.disconnect();
                } catch (Exception e) {
                }
        }
        return null;
    }
}
