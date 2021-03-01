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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cds.base.biz.service.BaseService;
import com.cds.base.common.exception.ValidationException;
import com.cds.base.common.page.Page;
import com.cds.base.dal.dao.BaseDAO;
import com.cds.base.exception.server.DAOException;
import com.cds.base.generator.num.NumGenerator;
import com.cds.base.util.bean.BeanUtils;
import com.cds.base.util.bean.CheckUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

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
public abstract class BaseServiceImpl<VO, DO> implements BaseService<VO> {
    private static final String UPDATE_DATE = "updateDate";
    private static final String CREATE_DATE = "createDate";
    private static final String SERIAL_ID = "serialVersionUID";
    private static final String VERSION = "version";
    private static final String DELETED = "deleted";

    private static final Set<String> IGNORE_PROPERTIES;
    // VO类型
    protected Class<VO> voType;
    // DO类型
    protected Class<DO> doType;

    // 钩子方法
    protected abstract BaseDAO<DO> getDAO();

    static {
        IGNORE_PROPERTIES = new HashSet<>();
        IGNORE_PROPERTIES.add(SERIAL_ID);
        IGNORE_PROPERTIES.add(UPDATE_DATE);
        IGNORE_PROPERTIES.add(CREATE_DATE);
    }

    /**
     * 获取泛型
     */
    @SuppressWarnings("unchecked")
    protected BaseServiceImpl() {
        // 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
        Type[] types = pt.getActualTypeArguments(); // 返回表示此类型实际类型参数的 Type 对象的数组
        voType = (Class<VO>)types[0];
        doType = (Class<DO>)types[1];
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class,
        noRollbackFor = RuntimeException.class)
    public VO save(VO value) {
        if (CheckUtils.isEmpty(value))
            return null;
        String num = null;
        if (isGeneral()) {
            num = NumGenerator.generateAndSetNum(value);
        }
        DO doValue = getDO(value, doType);
        if (isGeneral()) {
            try {
                // 防止数据库未设置默认值
                BeanUtils.setProperty(doValue, DELETED, false);
                BeanUtils.setProperty(doValue, VERSION, 0);
                BeanUtils.setProperty(doValue, UPDATE_DATE, new Date());
                BeanUtils.setProperty(doValue, CREATE_DATE, new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Boolean success = getDAO().insertSelective(doValue) > 0;
        if (!success) {
            return null;
        }
        if (CheckUtils.isNotEmpty(num)) {
            return detail(num);
        }
        return value;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class,
        noRollbackFor = RuntimeException.class)
    public VO modify(VO value) {

        if (CheckUtils.isEmpty(value)) {
            return null;
        }
        int successCount = 0;
        Object pk = null;
        try {
            pk = BeanUtils.getProperty(value, getPkName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CheckUtils.isEmpty(pk)) {
            throw new ValidationException("请指定主键");
        }
        DO oldValue = getDoDetail(pk.toString());
        if (CheckUtils.isEmpty(oldValue)) {
            throw new ValidationException("数据不存在");
        }
        BeanUtils.copyProperties(value, oldValue);

        if (isGeneral()) {
            Condition condition = getCondition();
            Criteria criteria = getCriteria(condition);
            criteria.andEqualTo(getPkName(), pk);

            Object version = BeanUtils.getProperty(oldValue, VERSION);
            if (version != null) {
                criteria.andEqualTo(VERSION, version);
                try {
                    BeanUtils.setProperty(oldValue, VERSION, Integer.parseInt(version.toString()) + 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                BeanUtils.setProperty(oldValue, UPDATE_DATE, new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
            successCount = getDAO().updateByConditionSelective(oldValue, condition);

        } else {
            successCount = getDAO().updateByPrimaryKeySelective(oldValue);
        }

        if (successCount < 1) {
            throw new DAOException("未修改任何数据，请确认版本号或主键！");
        } else if (successCount > 1) {
            throw new DAOException("存在多条记录被修改，需要回滚数据！");
        }
        BeanUtils.copyProperties(oldValue, value);
        return value;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, DAOException.class},
        noRollbackFor = RuntimeException.class)
    public boolean delete(Serializable pk) {
        if (!isGeneral()) {
            return getDAO().deleteByPrimaryKey(Integer.parseInt(pk.toString())) == 1;
        }
        DO doDetail = getDoDetail(pk);
        if (doDetail == null) {
            return false;
        }
        Condition condition = getCondition();
        Criteria criteria = getCriteria(condition);
        criteria.andEqualTo(getPkName(), pk);
        try {
            BeanUtils.setProperty(doDetail, DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDAO().updateByConditionSelective(doDetail, condition) == 1;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public VO detail(Serializable pk) {
        if (!isGeneral()) {
            return getVO(getDAO().selectByPrimaryKey(Integer.parseInt(pk.toString())), voType);
        }
        List<DO> resultList = null;
        Condition condition = getCondition();
        Criteria criteria = getCriteria(condition);
        // 设置主键
        criteria.andEqualTo(getPkName(), pk);

        resultList = getDAO().selectByCondition(condition);
        if (CheckUtils.isEmpty(resultList)) {
            return null;
        }
        if (resultList.size() > 1) {
            throw new ValidationException("查询失败，存在多条 相似记录");
        }
        return getVO(resultList.get(0), voType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, DAOException.class},
        noRollbackFor = RuntimeException.class)
    public int deleteAll(List<Serializable> numList) {
        if (CheckUtils.isEmpty(numList))
            return 0;
        int result = 0;
        for (Serializable num : numList) {
            boolean success = delete(num);
            if (success) {
                result++;
            }
        }
        return result;
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
    public VO detail(VO params) {
        List<VO> resultList = this.queryAll(params);
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
    public List<VO> queryAll(VO params) {
        return this.queryPaging(params, 1, 2000).getList();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<VO> queryPaging(VO params, Integer pageNum, Integer pageSize) {
        if (CheckUtils.isEmpty(pageNum, pageSize)) {
            return null;
        }
        // 分页
        PageHelper.startPage(pageNum, pageSize);

        Condition condition = getCondition();
        Criteria criteria = getCriteria(condition);
        criteria.andEqualTo(params);
        if (isGeneral()) {
            criteria.andEqualTo(DELETED, false);
        }

        List<DO> resultList = getDAO().selectByCondition(condition);
        if (CheckUtils.isEmpty(resultList)) {
            return new Page<>();
        }
        List<VO> voList = getVOList(resultList, voType);

        PageInfo pageInfo = new PageInfo<>(resultList);
        pageInfo.setList(voList);
        Page<VO> pageResult = new Page<>(params);
        // 返回结果
        pageResult.setParam(params);
        BeanUtils.copyProperties(pageInfo, pageResult);
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int count(VO param) {
        return getDAO().selectCount(getDO(param, doType));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public DO getDoDetail(Serializable pk) {
        if (CheckUtils.isEmpty(pk)) {
            return null;
        }
        if (!isGeneral()) {
            return getDAO().selectByPrimaryKey(Integer.parseInt(pk.toString()));
        }
        List<DO> resultList = null;
        Condition condition = getCondition();
        Criteria criteria = getCriteria(condition);
        criteria.andEqualTo(getPkName(), pk);
        resultList = getDAO().selectByCondition(condition);
        if (CheckUtils.isEmpty(resultList)) {
            return null;
        }
        return resultList.get(0);
    }

    /**
     * @description 创建Condition
     * @return Condition
     */
    private Condition getCondition() {
        return new Condition(doType);
    }

    /**
     * @description 获取Criteria
     * @return Criteria
     */
    private Criteria getCriteria(Condition condition) {
        return condition.createCriteria();
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
        if (CheckUtils.isEmpty(from)) {
            return null;
        }

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
        if (CheckUtils.isEmpty(from)) {
            return null;
        }
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
        if (CheckUtils.isEmpty(fromList)) {
            return null;
        }
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
        if (CheckUtils.isEmpty(fromList)) {
            return null;
        }
        return BeanUtils.getObjectList(fromList, clazz);
    }

    /**
     * @description 获取主键名称
     * @return String
     */
    protected String getPkName() {
        String pkName = "id";
        if (isGeneral()) {
            pkName = "num";
        }
        return pkName;
    }

    /**
     * @description 是否为通用Model
     * @return boolean
     */
    protected boolean isGeneral() {
        Class<? super DO> superclass = doType.getSuperclass();
        if (superclass == null) {
            return false;
        }
        if ("GeneralModel".equals(superclass.getSimpleName())) {
            return true;
        }
        return false;
    }
}
