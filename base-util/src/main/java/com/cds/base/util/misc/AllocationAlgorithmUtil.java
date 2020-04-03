/*
 * Copyright 2013-2015 duolabao.com All Right Reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.cds.base.util.misc;

/**
 * 类AllocationAlgorithmUtil.java的实现描述：分配算法，计算定时任务执行需要开启的线程数量
 * 
 * @author george 2015年10月8日 下午5:34:46
 */
public class AllocationAlgorithmUtil {

    /**
     * 最多开启的线程数量
     */
    public static final int MAX_THREAD_SIZE           = 100;

    /**
     * 每个线程最少执行的任务数量
     */
    public static final int MIN_EACH_THREAD_TASK_SIZE = 100;

    /**
     * 计算开启的线程数量<br/>
     * 如果baseSize为空或小于零返回零<br/>
     * 如果threadSize为空或小于零，根据baseSize和MAX_THREAD_SIZE和MIN_EACH_THREAD_TASK_SIZE决定
     * 返回(baseSize/MIN_EACH_THREAD_TASK_SIZE+1)和MAX_THREAD_SIZE中最小的 <br/>
     * 默认返回threadSize
     * 
     * @param baseSize
     * @param eachSize
     * @return
     */
    public static int calculateThreadNum(Integer baseSize, Integer threadSize, Integer eachSize) {
        if (baseSize == null || baseSize <= 0) {
            return 0;
        }
        int result = returnMin(calculate(baseSize, eachSize), MAX_THREAD_SIZE);
        if (threadSize == null || threadSize <= 0) {
            return result;
        }
        return returnMin(result, threadSize);
    }

    /**
     * 根据当前任务号计算该任务分配的商户数量<br/>
     * 该方法中taskSize由calculateThreadNum(Integer baseSize, Integer eachSize)计算<br/>
     * currentTaskNum由1增长直到taskSize<br/>
     * 平均获取nums中数据
     * 
     * @param nums
     * @param taskSize
     * @param currentTaskNum
     * @return
     */
    public static String[] getEachThreadList(String[] nums, int taskSize, int currentTaskNum) {
        if (currentTaskNum > taskSize) {
            return null;
        }
        if (nums == null || nums.length == 0) {
            return null;
        }
        if (nums.length < taskSize && currentTaskNum > 1) {
            return null;
        }
        // 获取每组数量
        int _size = nums.length % taskSize;
        int perSize = _size > 0 ? (nums.length / taskSize + 1) : nums.length / taskSize;
        int size = (_size != 0 && currentTaskNum == taskSize) ? (nums.length - (taskSize - 1) * perSize) : perSize;
        int beginIndex = perSize * (currentTaskNum - 1);
        int endIndex = beginIndex + size;
        String[] result = new String[size];
        for (int i = 0; i < endIndex - beginIndex; i++) {
            result[i] = nums[beginIndex + i];
        }
        return result;
    }

    public static String[] getEachThreadList2(String[] nums, int taskSize, int currentTaskNum) {
        if (currentTaskNum > taskSize) {
            return null;
        }
        if (nums == null || nums.length == 0) {
            return null;
        }
        if (nums.length < taskSize && currentTaskNum > 1) {
            return null;
        }
        // 获取每组数量
        int _size = nums.length % taskSize;
        int perSize = _size > 0 ? (nums.length / taskSize + 1) : nums.length / taskSize;
        int size = (_size != 0 && currentTaskNum == taskSize) ? (nums.length - (taskSize - 1) * perSize) : perSize;
        int beginIndex = perSize * (currentTaskNum - 1);
        int endIndex = beginIndex + size;
        String[] result = new String[size];
        for (int i = 0; i < endIndex - beginIndex; i++) {
            result[i] = nums[beginIndex + i];
        }
        return result;
    }

    private static int calculate(int total, int eachSize) {
        return total / eachSize + 1;
    }

    private static int returnMin(int first, int second) {
        return first > second ? second : first;
    }

    public static void main(String[] args) {
        String[] ad = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        getEachThreadList(ad, 3, 2);
    }
}
