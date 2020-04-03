/**
 * @Project base.common
 * @Package com.cds.base.common.bean
 * @Class PageResult.java
 * @Date 2016年8月8日 下午6:35:49
 * @Copyright (c) 2016 Rising Moon All Right Reserved.
 */
package com.cds.base.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 分页返回结果
 * @Notes Page-->ExtendPage-->PageResult
 * @author liming
 * @Date 2016年8月8日 下午6:35:49
 * @version 1.0
 * @since JDK 1.8
 */
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    // 结果列表
    private List<T> resultList;
    // 结果总数
    private int resultCount;

    /**
     * resultList
     *
     * @return resultList
     * @since 1.0.0
     */

    public List<T> getResultList() {
        return resultList;
    }

    /**
     * @param resultList
     *            the resultList to set
     */
    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    /**
     * resultCount
     *
     * @return resultCount
     * @since 1.0.0
     */

    public int getResultCount() {
        return resultCount;
    }

    /**
     * @param resultCount
     *            the resultCount to set
     */
    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

}
