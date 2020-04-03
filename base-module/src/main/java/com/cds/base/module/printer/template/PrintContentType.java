/**
 * @Project base-module
 * @Package com.cds.base.module.printer.template
 * @Class PrintTemplate.java
 * @Date Jan 6, 2020 2:51:58 PM
 * @Copyright (c) 2020 YOUWE All Right Reserved.
 */
package com.cds.base.module.printer.template;

/**
 * @Description 打印模板
 * @Notes 未填写备注
 * @author liming
 * @Date Jan 6, 2020 2:51:58 PM
 * @version 1.0
 * @since JDK 1.8
 */
public enum PrintContentType {

    URL("URL", "url请求结果"), HTML("HTML", "html形式"), WORD("WORD", "word形式"), EXCEL("EXCEL", "excel形式"),
    PDF("PDF", "pdf形式"), TXT("TXT", "txt形式"), STRING("STRING", "字符串形式");

    /**
     * 值
     */
    private final String value;
    /**
     * 显示名称
     */
    private String displayName;

    private PrintContentType(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * value
     *
     * @return value
     */

    public String getValue() {
        return value;
    }

    /**
     * displayName
     *
     * @return displayName
     */

    public String getDisplayName() {
        return displayName;
    }

}
