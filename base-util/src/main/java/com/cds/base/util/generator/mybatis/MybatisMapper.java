/**
 * @Project base-server-dal
 * @Package com.cds.base.server.dal.mapper
 * @Class MybatisMapper.java
 * @Date 2017年11月17日 上午11:44:24
 * @Copyright (c) 2019 YOUWE All Right Reserved.
 */
package com.cds.base.util.generator.mybatis;

/**
 * @Description Mybitas Mapper
 * @Notes 未填写备注
 * @author liming
 * @Date 2017年11月17日 上午11:44:24
 * @version 1.0
 * @since JDK 1.7
 */
public interface MybatisMapper {

    /**
     * 根据id查询操作
     */
    String FIND_BY_ID = "findById";
    /**
     * 根据num查询操作
     */
    String FIND_BY_NUM = "findByNum";
    /**
     * 执行修改操作
     */
    String UPDATE = "update";
    /**
     * 删除单条记录操作
     */
    String DELETE_BY_ID = "deletedById";
    /**
     * 批量删除记录操作
     */
    String DELETE_BY_ID_LIST = "deletedByIdList";
    /**
     * 删除单条记录操作(逻辑删除)
     */
    String DELETE_BY_NUM = "deletedByNum";
    /**
     * 批量删除记录操作(逻辑删除)
     */
    String DELETE_BY_NUM_LIST = "deletedByNumList";
    /**
     * 执行存储操作
     */
    String SAVE = "save";
    /**
     * 执行存储操作
     */
    String SAVE_ALL = "saveAll";
    /**
     * 根据条件查询记录条数
     */
    String QUERY_ALL = "queryAll";
    /**
     * 根据条件查询记录条数
     */
    String QUERY_PAGING_LIST = "queryPagingList";

    /**
     * 根据条件查询记录条数
     */
    String QUERY_PAGING_COUNT = "queryPagingCount";
    /**
     * 每页数量
     */
    String PAGE_SIZE = "pageSize";
    /**
     * 当前页
     */
    String CURRENT_PAGE = "currentPage";
    /**
     * 当前页
     */
    String PAGE_SKIP = "pageSkip";
}
