/**
 * @Project base.common
 * @Package com.cds.base.common.bean
 * @Class ExtendPage.java
 * @Date 2016年8月22日 下午4:05:35
 * @Copyright (c) 2016 Rising Moon All Right Reserved.
 */
package com.cds.base.common.page;

/**
 * @Description Page扩展信息
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年8月22日 下午4:05:35
 * @version 1.0
 * @since JDK 1.8
 */
public class ExtendPage<T> extends Page<T> {

    private static final long serialVersionUID = 1L;
    // 排序属性
    private String orderBy;
    // group by
    private String groupBy;
    // 是否降序
    private boolean desc;
    // limit
    private int limit;
    // count计算
    private String count;
    // 求平均
    private String avg;
    // 求最大值
    private String max;
    // 求最小值
    private String min;
    // 求和
    private String sum;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

}
