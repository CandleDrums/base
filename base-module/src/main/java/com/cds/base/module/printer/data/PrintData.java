/**
 * @Project base-module
 * @Package com.cds.base.module.printer.data
 * @Class PrintData.java
 * @Date Jan 6, 2020 11:30:37 AM
 * @Copyright (c) 2020 YOUWE All Right Reserved.
 */
package com.cds.base.module.printer.data;

import com.cds.base.module.printer.paper.Paper;
import com.cds.base.module.printer.paper.PaperCategory;
import com.cds.base.module.printer.template.PrintContentType;

import lombok.Data;

/**
 * @Description 打印请求参数
 * @Notes 未填写备注
 * @author liming
 * @Date Jan 3, 2020 2:37:29 PM
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class PrintData<T> {
    // 打印数据
    private T data;
    // 模板选择
    private PrintContentType template;
    // 纸张选择
    private Paper paper;
    // 打印纸类型
    private PaperCategory category;
    // 打印份数
    private int copies = 1;
}
