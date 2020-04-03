/**
 * @Project base-module
 * @Package com.cds.base.module.base
 * @Class Printer.java
 * @Date Jan 6, 2020 11:28:48 AM
 * @Copyright (c) 2020 YOUWE All Right Reserved.
 */
package com.cds.base.module.printer;

import com.cds.base.module.printer.data.PrintData;
import com.cds.base.module.printer.data.PrinterResult;

/**
 * @Description 打印机服务
 * @Notes 未填写备注
 * @author liming
 * @Date Jan 6, 2020 11:28:48 AM
 * @version 1.0
 * @since JDK 1.8
 */
public interface Printer<REQ, RESP> {
    /**
     * @description 打印
     * @return void
     */
    void print(PrintData<REQ> data);

    /**
     * @description 绑定打印机
     * @return void
     */
    void bind(REQ req);

    /**
     * @description 解绑打印机
     * @return void
     */
    void unbind(REQ req);

    /**
     * @description 获取结果
     * @return String
     */
    PrinterResult<RESP> result();
}
