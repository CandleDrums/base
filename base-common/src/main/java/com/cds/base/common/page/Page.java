/**
 * @Project base.common
 * @Package com.cds.base.common.bean
 * @Class Page.java
 * @Date 2016年8月9日 下午5:01:37
 * @Copyright (c) 2016 Rising Moon All Right Reserved.
 */
package com.cds.base.common.page;

import java.io.Serializable;

/**
 * @Description 分页对象
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年8月9日 下午5:01:37
 * @version 1.0
 * @since JDK 1.8
 */
public class Page<T> implements Serializable {

    // 默认序列号
    private static final long serialVersionUID = 1L;
    // 页大小默认值
    private static final int DEFAULT_PAGE_SIZE = 20;
    // 分页参数
    private int currentPage = 1;
    // 分页大小
    private int pageSize = DEFAULT_PAGE_SIZE;
    // 总记录数
    private int totalCount;
    // 总页数
    private int totalPageCount;
    // 需要返回总数
    private boolean needCount;
    // 查询实体
    private T param;

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
    public Page(final int pageSize) {
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
     * @returnType int
     * @author liming
     */
    public int getStartIndex() {
        return (currentPage - 1) * pageSize;
    }

    /**
     * @description 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     * @param pageNo
     * @returnType void
     * @exception @since
     *                1.0.0
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;

        if (currentPage < 1) {
            this.currentPage = 1;
        }
    }

    /**
     * @description 根据pageSize与totalCount计算总页数,默认值为-1.
     * @return
     * @returnType int
     * @exception @since
     *                1.0.0
     */
    public int getTotalPages() {
        if (totalCount == 0)
            return 1;

        if (totalPageCount == 0) {
            int count = totalCount / pageSize;
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
     * @returnType boolean
     * @exception @since
     *                1.0.0
     */
    public boolean isHasNext() {
        return (currentPage + 1 <= getTotalPages());
    }

    /**
     * @description 取得下页的页号,序号从1开始.
     * @return
     * @returnType int
     * @exception @since
     *                1.0.0
     */
    public int getNextPage() {
        if (isHasNext())
            return currentPage + 1;
        else
            return currentPage;
    }

    /**
     * @description 是否还有上一页
     * @return
     * @returnType boolean
     * @exception @since
     *                1.0.0
     */
    public boolean isHasPre() {
        return (currentPage - 1 >= 1);
    }

    /**
     * @description 取得上页的页号,序号从1开始.
     * @return
     * @returnType int
     * @exception @since
     *                1.0.0
     */
    public int getPrePage() {
        if (isHasPre())
            return currentPage - 1;
        else
            return currentPage;
    }

    /**
     * pageSize
     *
     * @return pageSize
     * @since 1.0.0
     */

    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * totalCount
     *
     * @return totalCount
     * @since 1.0.0
     */

    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     *            the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * totalPageCount
     *
     * @return totalPageCount
     * @since 1.0.0
     */

    public int getTotalPageCount() {
        return totalPageCount;
    }

    /**
     * @param totalPageCount
     *            the totalPageCount to set
     */
    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    /**
     * serialversionuid
     *
     * @return serialversionuid
     * @since 1.0.0
     */

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * defaultPageSize
     *
     * @return defaultPageSize
     * @since 1.0.0
     */

    public static int getDefaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    /**
     * currentPage
     *
     * @return currentPage
     * @since 1.0.0
     */

    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * needCount
     *
     * @return needCount
     * @since 1.0.0
     */

    public boolean isNeedCount() {
        return needCount;
    }

    /**
     * @param needCount
     *            the needCount to set
     */
    public void setNeedCount(boolean needCount) {
        this.needCount = needCount;
    }

    /**
     * param
     *
     * @return param
     */

    public T getParam() {
        return param;
    }

    /**
     * @param param
     *            the param to set
     */
    public void setParam(T param) {
        this.param = param;
    }

}
