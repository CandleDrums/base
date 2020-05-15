/**
 * @Project base.common
 * @Package com.cds.base.common.bean
 * @Class Page.java
 * @Date 2016年8月9日 下午5:01:37
 * @Copyright (c) 2016 Rising Moon All Right Reserved.
 */
package com.cds.base.common.page;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description 分页对象
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年8月9日 下午5:01:37
 * @version 1.0
 * @since JDK 1.8
 */
@Data
public class Page<T> implements Serializable {

    // 默认序列号
    private static final long serialVersionUID = 1L;
    // 页大小默认值
    private static final Integer DEFAULT_PAGE_SIZE = 20;
    // 分页参数
    private Integer currentPage = 1;
    // 分页大小
    private Integer pageSize = DEFAULT_PAGE_SIZE;
    // 总记录数
    private Integer totalCount;
    // 总页数
    private Integer totalPageCount;
    // 需要返回总数
    private Boolean needCount;
    // 查询实体
    private T param;
    // 排序属性
    private String orderBy;
    // 是否降序
    private Boolean desc;

    /**
     * Create a new instance Page.
     */
    public Page() {
        super();
    }

    /**
     * Create a new instance Page.
     * 
     * @param pageSize
     *            页大小
     */
    public Page(final Integer pageSize) {
        if (pageSize > 0 && pageSize <= 10000) {
            this.setPageSize(pageSize);
        }
    }

    /**
     * @description 设置默认分页大小
     * @return void
     */
    public void setDefaultPageSize() {
        this.setPageSize(DEFAULT_PAGE_SIZE);
    }

    /**
     * @description 获取起始记录数
     * @return
     * @returnType Integer
     * @author liming
     */
    public Integer getStartIndex() {
        return (currentPage - 1) * pageSize;
    }

    /**
     * @description 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     * @param pageNo
     * @returnType void
     * @exception @since
     *                1.0.0
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;

        if (currentPage < 1) {
            this.currentPage = 1;
        }
    }

    /**
     * @description 根据pageSize与totalCount计算总页数,默认值为-1.
     * @return
     * @returnType Integer
     * @exception @since
     *                1.0.0
     */
    public Integer getTotalPages() {
        if (totalCount == 0)
            return 1;

        if (totalPageCount == 0) {
            Integer count = totalCount / pageSize;
            if (totalCount % pageSize > 0) {
                count++;
            }
            totalPageCount = count;
        }
        return totalPageCount;
    }

    /**
     * @description 是否还有下一页
     * @return
     * @returnType Boolean
     * @exception @since
     *                1.0.0
     */
    public Boolean isHasNext() {
        return (currentPage + 1 <= getTotalPages());
    }

    /**
     * @description 取得下页的页号,序号从1开始.
     * @return
     * @returnType Integer
     * @exception @since
     *                1.0.0
     */
    public Integer getNextPage() {
        if (isHasNext())
            return currentPage + 1;
        else
            return currentPage;
    }

    /**
     * @description 是否还有上一页
     * @return
     * @returnType Boolean
     * @exception @since
     *                1.0.0
     */
    public Boolean isHasPre() {
        return (currentPage - 1 >= 1);
    }

    /**
     * @description 取得上页的页号,序号从1开始.
     * @return
     * @returnType Integer
     * @exception @since
     *                1.0.0
     */
    public Integer getPrePage() {
        if (isHasPre())
            return currentPage - 1;
        else
            return currentPage;
    }

    /**
     * defaultPageSize
     *
     * @return defaultPageSize
     * @since 1.0.0
     */

    public static Integer getDefaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

}
