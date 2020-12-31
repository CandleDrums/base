/**
 * @Project base.common
 * @Package com.cds.base.common.bean
 * @Class Page.java
 * @Date 2016年8月9日 下午5:01:37
 * @Copyright (c) 2016 Rising Moon All Right Reserved.
 */
package com.cds.base.common.page;

import java.io.Serializable;

import com.github.pagehelper.PageInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 分页对象
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年8月9日 下午5:01:37
 * @version 1.0
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Page<T> extends PageInfo<T> implements Serializable {

    // 默认序列号
    private static final long serialVersionUID = 1L;
    // 页大小默认值
    public static final Integer DEFAULT_PAGE_SIZE = 20;
    public static final Integer DEFAULT_PAGE_NUM = 1;

    // 查询实体
    public T param;

    public Page() {
        new Page<T>(null);
    }

    public Page(T param) {
        new Page<T>(param, DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
    }

    public Page(T param, Integer pageNum, Integer pageSize) {
        this.param = param;
        if (pageNum == null) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        super.setPageNum(pageNum);
        super.setPageSize(pageSize);
    }
}