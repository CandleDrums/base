/**
 * @Project base-biz
 * @Package com.cds.candledrums.base.biz.service.impl
 * @Class BaseServiceImpl.java
 * @Date 2019年9月10日 下午2:13:46
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.biz.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cds.base.biz.service.BaseService;
import com.cds.base.common.exception.ValidationException;
import com.cds.base.dal.dao.BaseDAO;
import com.cds.base.util.bean.BeanUtils;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description 基础Service实现
 * @Notes 未填写备注
 * @author liming
 * @Date 2019年9月10日 下午2:15:54
 * @version 1.0
 * @since JDK 1.8
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
    timeout = TransactionDefinition.TIMEOUT_DEFAULT)
public abstract class BaseServiceImpl<VO, DO, Example> implements BaseService<VO> {
    // VO类型
    protected Class<VO> voType;
    // DO类型
    protected Class<DO> doType;

    // 钩子方法
    protected abstract BaseDAO<DO, Serializable, Example> getDAO();

    @Override
    public abstract VO save(VO value);

    /**
     * 获取泛型
     */
    @SuppressWarnings("unchecked")
    public BaseServiceImpl() {
        // 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
        Type[] types = pt.getActualTypeArguments(); // 返回表示此类型实际类型参数的 Type 对象的数组
        voType = (Class<VO>)types[0];
        doType = (Class<DO>)types[1];

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class,
        noRollbackFor = RuntimeException.class)
    public int saveAll(List<VO> valueList) {
        if (CheckUtils.isEmpty(valueList))
            return 0;
        int count = 0;
        for (VO value : valueList) {
            VO result = this.save(value);
            if (CheckUtils.isNotEmpty(result)) {
                count++;
            }
        }
        return count;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public boolean contains(VO value) {
        if (CheckUtils.isEmpty(value))
            return false;
        return getDAO().queryPagingCount(getDO(value, doType)) > 0;
    }

    @Override
    public VO detail(VO value) {
        List<VO> resultList = this.queryAll(value);
        if (CheckUtils.isEmpty(resultList)) {
            return null;
        }
        if (resultList.size() > 1) {
            throw new ValidationException("查询失败，存在多条 相似记录");
        }
        return resultList.get(0);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<VO> queryAll(VO param) {
        if (CheckUtils.isEmpty(param))
            return null;
        return getVOList(getDAO().queryPagingList(getDO(param, doType), new RowBounds()), voType);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<VO> queryPagingList(VO param, int startIndex, int pageSize) {
        if (CheckUtils.isEmpty(param, pageSize))
            return null;
        RowBounds bounds = new RowBounds(startIndex, pageSize);
        return getVOList(getDAO().queryPagingList(getDO(param, doType), bounds), voType);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int queryPagingCount(VO param) {
        if (CheckUtils.isEmpty(param))
            return 0;
        return getDAO().queryPagingCount(getDO(param, doType));
    }

    /**
     * @description 获取类名
     * @return String
     */
    protected String getRuleClassName(VO value) {
        String simpleName = value.getClass().getSimpleName();
        if (simpleName.lastIndexOf("VO") > 0 || simpleName.lastIndexOf("DO") > 0) {
            return simpleName.substring(0, simpleName.length() - 2);
        }
        return simpleName;
    }

    /**
     * @description 获取DO对象
     * @param from
     * @param to
     * @return
     * @returnType To
     * @author liming
     */
    protected <From, To> To getDO(From from, Class<To> clazz) {
        if (CheckUtils.isEmpty(from))
            return null;

        return BeanUtils.getObject(from, clazz);
    }

    /**
     * @description 获取VO对象
     * @param doObj
     * @param toClass
     * @return
     * @returnType Object
     * @author liming
     */
    protected <From, To> To getVO(From from, Class<To> clazz) {
        if (CheckUtils.isEmpty(from))
            return null;
        return BeanUtils.getObject(from, clazz);
    }

    /**
     * @description 获取DO列表
     * @param fromList
     * @param to
     * @return
     * @returnType List<To>
     * @author liming
     */
    protected <From, To> List<To> getDOList(List<From> fromList, Class<To> clazz) {
        if (CheckUtils.isEmpty(fromList))
            return null;
        return BeanUtils.getObjectList(fromList, clazz);
    }

    /**
     * @description 获取VO列表
     * @param fromList
     * @param to
     * @return
     * @returnType List<To>
     * @author liming
     */
    protected <From, To> List<To> getVOList(List<From> fromList, Class<To> clazz) {
        if (CheckUtils.isEmpty(fromList))
            return null;
        return BeanUtils.getObjectList(fromList, clazz);
    }

    protected Map<String, Object> getQueryMap() {
        return new HashMap<String, Object>();
    }

}
