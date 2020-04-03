/**
 * @project UAMS_COMMON
 * @package com.epro.uams.common.util
 * @class ValidateUtils.java
 * @author ming.li
 * @version 1.0.0
 * @date 2014-1-20-下午12:30:42 copyright (c) 2014 上海易宝软件有限公司深圳分公司
 */
package com.cds.base.util.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cds.base.util.misc.RegexUtils;

/**
 * @description 验证工具类
 * @author ming.li
 * @modify ming.li
 * @modifyDate 2014-1-20 下午12:30:42
 * @notes 未填写备注
 * @version 1.0.0
 */
public class ValidateUtils {

    private static final String   REGEX_EMAIL          = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    private static final String   REGEX_MOBILE         = "^[1][3,4,5,8][0-9]{9}$";
    private static final String   REGEX_URL            = "^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
    private static final String   REGEX_PASSWORD       = "^[a-zA-Z]\\w{6,12}$";
    private static final String   REGEX_STRING         = "^[a-zA-Z0-9\u4e00-\u9fa5-_]+$";
    private static final String   REGEX_INTEGER        = "^[+]?\\d+$";
    private static final String   REGEX_DIGITS         = "^[0-9]*$";
    private static final String   REGEX_FLOAT          = "^[-\\+]?\\d+(\\.\\d+)?$";
    private static final String   REGEX_PHONE          = "^(\\d{3,4}-?)?\\d{7,9}$";
    private static final String   REGEX_ID_CARD        = "^(\\d{6})()?(\\d{4})(\\d{2})(\\d{2})(\\d{3})(\\w)$";
    private static final String   REGEX_ZIP_CODE       = "^[0-9]{6}$";
    private static final String   REGEX_STRING_DIGITS  = "^[A-Za-z0-9_-]+$";
    private static final String   REGEX_LETTER         = "^[A-Za-z]+$";
    private static final String   REGEX_CHINESE_CHAR   = "^[\u0391-\uFFE5]+$";
    private static final String   REGEX_CHINESE        = "^[\u4e00-\u9fa5]+$";
    private static final String   REGEX_SPECIAL_SYMBOL = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    private static final String[] SPECIAL_SYMBOL       = { "[", "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "=", "|", "{", "}", "'", ":", ";", "'", ",", "[",
            "]", ".", "<", ">", "/", "?", "~", "！", "@", "#", "￥", "%", "…", "&", "*", "（", "）", "—", "+", "|", "{", "}", "【", "】", "‘", "；", "：", "”", "“", "’", "。", "，", "、",
            "？", "]"                                  };

    /**
     * @description 验证电子邮件
     * @param email
     * @return
     * @returnType boolean
     * @exception @since 1.0.0
     */
    public static boolean isEmail(String email) {
        // 首先判断是否为空
        if (CheckUtils.isEmpty(email)) {
            return false;
        }
        return RegexUtils.match(email, REGEX_EMAIL);
    }

    /**
     * @description 验证手机号
     * @param str
     * @return
     * @returnType boolean
     * @exception @since 1.0.0
     */
    public static boolean isMobile(String mobile) {
        // 首先判断是否为空
        if (CheckUtils.isEmpty(mobile)) {
            return false;
        }
        return RegexUtils.match(mobile, REGEX_MOBILE);
    }

    /**
     * @description 验证电话号码
     * @param str
     * @return
     * @returnType boolean
     * @exception @since 1.0.0
     */
    public static boolean isTelephone(String tel) {
        // 首先判断是否为空
        if (CheckUtils.isEmpty(tel)) {
            return false;
        }
        Pattern p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
        Pattern p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
        if (tel.length() > 9) {
            return p1.matcher(tel).matches();
        } else {
            return p2.matcher(tel).matches();
        }
    }

    /**
     * @description 判断字符串长度
     * @param obj
     * @param minLength
     * @param maxLength
     * @return
     * @returnType boolean
     * @exception @since 1.0.0
     */
    public static boolean checkLength(Object obj, int minLength, int maxLength) {
        int length = 0;
        byte[] b = String.valueOf(obj).getBytes();
        for (int i = 0; i < b.length; i++) {
            if (b[i] >= 0) {
                length = length + 1;
            } else {
                length = length + 2;
                i++;
            }
        }
        if (length < maxLength && length > minLength) {
            return true;
        }
        return false;
    }

    /**
     * 匹配URL地址
     * 
     * @param str
     * @return
     * @author liming
     */
    public final static boolean isUrl(String str) {
        if (CheckUtils.isEmpty(str)) {
            return false;
        }
        return RegexUtils.match(str, REGEX_URL);
    }

    /**
     * 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。
     * 
     * @param str
     * @return
     * @author liming
     */
    public final static boolean isPwd(String str) {
        if (CheckUtils.isEmpty(str)) {
            return false;
        }
        return RegexUtils.match(str, REGEX_PASSWORD);
    }

    /**
     * 验证字符，只能包含中文、英文、数字、下划线等字符。
     * 
     * @param str
     * @return
     * @author liming
     */
    public final static boolean stringCheck(String str) {
        if (CheckUtils.isEmpty(str)) {
            return false;
        }
        return RegexUtils.match(str, REGEX_STRING);
    }

    /**
     * 匹配非负整数（正整数+0）
     * 
     * @param str
     * @return
     * @author liming
     */
    public final static boolean isInteger(String str) {
        if (CheckUtils.isEmpty(str)) {
            return false;
        }
        return RegexUtils.match(str, REGEX_INTEGER);
    }

    /**
     * 判断数值类型，包括整数和浮点数
     * 
     * @param str
     * @return
     * @author liming
     */
    public final static boolean isNumeric(String str) {
        if (isFloat(str) || isInteger(str)) return true;
        return false;
    }

    /**
     * 只能输入数字
     * 
     * @param str
     * @return
     * @author liming
     */
    public final static boolean isDigits(String str) {
        if (CheckUtils.isEmpty(str)) {
            return false;
        }
        return RegexUtils.match(str, REGEX_DIGITS);
    }

    /**
     * 匹配正浮点数
     * 
     * @param str
     * @return
     * @author liming
     */
    public final static boolean isFloat(String str) {
        if (CheckUtils.isEmpty(str)) {
            return false;
        }
        return RegexUtils.match(str, REGEX_FLOAT);
    }

    /**
     * 联系电话(手机/电话皆可)验证
     * 
     * @param text
     * @return
     * @author liming
     */
    public final static boolean isTel(String text) {
        if (isMobile(text) || isPhone(text)) return true;
        return false;
    }

    /**
     * 电话号码验证
     * 
     * @param text
     * @return
     * @author liming
     */
    public final static boolean isPhone(String text) {
        if (CheckUtils.isEmpty(text)) {
            return false;
        }
        return RegexUtils.match(text, REGEX_PHONE);
    }

    /**
     * 身份证号码验证
     * 
     * @param text
     * @return
     * @author liming
     */
    public final static boolean isIdCardNo(String text) {
        if (CheckUtils.isEmpty(text)) {
            return false;
        }
        return RegexUtils.match(text, REGEX_ID_CARD);
    }

    /**
     * 邮政编码验证
     * 
     * @param text
     * @return
     * @author liming
     */
    public final static boolean isZipCode(String text) {
        if (CheckUtils.isEmpty(text)) {
            return false;
        }
        return RegexUtils.match(text, REGEX_ZIP_CODE);
    }

    /**
     * 判断整数num是否等于0
     * 
     * @param num
     * @return
     * @author liming
     */
    public final static boolean isIntEqZero(int num) {
        return num == 0;
    }

    /**
     * 判断整数num是否大于0
     * 
     * @param num
     * @return
     * @author liming
     */
    public final static boolean isIntGtZero(int num) {
        return num > 0;
    }

    /**
     * 判断整数num是否大于或等于0
     * 
     * @param num
     * @return
     * @author liming
     */
    public final static boolean isIntGteZero(int num) {
        return num >= 0;
    }

    /**
     * 判断浮点数num是否等于0
     * 
     * @param num 浮点数
     * @return
     * @author liming
     */
    public final static boolean isFloatEqZero(float num) {
        return num == 0f;
    }

    /**
     * 判断浮点数num是否大于0
     * 
     * @param num 浮点数
     * @return
     * @author liming
     */
    public final static boolean isFloatGtZero(float num) {
        return num > 0f;
    }

    /**
     * 判断浮点数num是否大于或等于0
     * 
     * @param num 浮点数
     * @return
     * @author liming
     */
    public final static boolean isFloatGteZero(float num) {
        return num >= 0f;
    }

    /**
     * 判断是否为合法字符(a-zA-Z0-9-_)
     * 
     * @param text
     * @return
     * @author liming
     */
    public final static boolean isRightfulString(String text) {
        if (CheckUtils.isEmpty(text)) {
            return false;
        }
        return RegexUtils.match(text, REGEX_STRING_DIGITS);
    }

    /**
     * 判断英文字符(a-zA-Z)
     * 
     * @param text
     * @return
     * @author liming
     */
    public final static boolean isLetter(String text) {
        if (CheckUtils.isEmpty(text)) {
            return false;
        }
        return RegexUtils.match(text, REGEX_LETTER);
    }

    /**
     * 判断中文字符(包括汉字和符号)
     * 
     * @param text
     * @return
     * @author liming
     */
    public final static boolean isChineseChar(String text) {
        if (CheckUtils.isEmpty(text)) {
            return false;
        }
        return RegexUtils.match(text, REGEX_CHINESE_CHAR);
    }

    /**
     * 匹配汉字
     * 
     * @param text
     * @return
     * @author liming
     */
    public final static boolean isChinese(String text) {
        if (CheckUtils.isEmpty(text)) {
            return false;
        }
        return RegexUtils.match(text, REGEX_CHINESE);
    }

    /**
     * 是否包含中英文特殊字符，除英文"-_"字符外
     * 
     * @param str
     * @return
     */
    public static boolean isContainsSpecialChar(String text) {
        if (CheckUtils.isEmpty(text)) {
            return false;
        }
        for (String ch : SPECIAL_SYMBOL) {
            if (text.contains(ch)) return true;
        }
        return false;
    }

    /**
     * 过滤中英文特殊字符，除英文"-_"字符外
     * 
     * @param text
     * @return
     */
    public static String stringFilter(String text) {
        Pattern p = Pattern.compile(REGEX_SPECIAL_SYMBOL);
        Matcher m = p.matcher(text);
        return m.replaceAll("").trim();
    }

    /**
     * 过滤html代码
     * 
     * @param inputString 含html标签的字符串
     * @return
     */
    public static String htmlFilter(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        java.util.regex.Pattern p_ba;
        java.util.regex.Matcher m_ba;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String patternStr = "\\s+";

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            m_ba = p_ba.matcher(htmlStr);
            htmlStr = m_ba.replaceAll(""); // 过滤空格

            textStr = htmlStr;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }

}
