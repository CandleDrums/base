/**
 * @Project base-biz
 * @Package com.cds.candledrums.base.biz.service.impl
 * @Class BaseServiceImpl.java
 * @Date 2019年9月10日 下午2:13:46
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.biz.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cds.base.biz.service.BaseService;
import com.cds.base.common.exception.ValidationException;
import com.cds.base.dal.dao.BaseDAO;
import com.cds.base.exception.server.DAOException;
import com.cds.base.generator.num.NumGenerator;
import com.cds.base.util.bean.BeanUtils;
import com.cds.base.util.bean.CheckUtils;
import com.cds.base.util.lang.ArrayUtils;

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
    private static final String AND = "and";
    private static final String EQUAL_TO = "EqualTo";
    private static final String CREATE_METHOD_NAME = "createCriteria";
    private static final Set<String> IGNORE_PROPERTIES;
    // VO类型
    protected Class<VO> voType;
    // DO类型
    protected Class<DO> doType;
    // Example类型
    protected Class<Example> exampleType;

    // 钩子方法
    protected abstract BaseDAO<DO, Serializable, Example> getDAO();

    static {
        IGNORE_PROPERTIES = new HashSet<String>();
        IGNORE_PROPERTIES.add("serialVersionUID");
        IGNORE_PROPERTIES.add("createDate");
        IGNORE_PROPERTIES.add("updateDate");
    }

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
        exampleType = (Class<Example>)types[2];
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
                BeanUtils.setProperty(doValue, "deleted", false);
                BeanUtils.setProperty(doValue, "version", 0);
                BeanUtils.setProperty(doValue, "updateDate", new Date());
                BeanUtils.setProperty(doValue, "createDate", new Date());
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
            Example example = newExample();
            Object criteria = newCriteria(example);
            addAndEqualPropertie(getPkName(), pk, pk.getClass(), criteria);
            Object version = BeanUtils.getProperty(oldValue, "version");
            if (version != null) {
                addAndEqualPropertie("version", version, Integer.class, criteria);
                try {
                    BeanUtils.setProperty(oldValue, "version", Integer.parseInt(version.toString()) + 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                BeanUtils.setProperty(oldValue, "updateDate", new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
            successCount = getDAO().updateByExampleSelective(oldValue, example);
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
        Example example = newExample();
        Object criteria = newCriteria(example);
        addAndEqualPropertie(getPkName(), pk, getPkType(), criteria);
        try {
            BeanUtils.setProperty(doDetail, "deleted", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDAO().updateByExample(doDetail, example) == 1;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public VO detail(Serializable pk) {
        if (!isGeneral()) {
            return getVO(getDAO().selectByPrimaryKey(Integer.parseInt(pk.toString())), voType);
        }
        List<DO> resultList = null;
        Example example = newExample();
        Object criteria = newCriteria(example);
        // 设置主键
        addAndEqualPropertie(getPkName(), pk, getPkType(), criteria);

        resultList = getDAO().selectByExample(example);
        if (CheckUtils.isEmpty(resultList)) {
            return null;
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
        return this.queryPagingList(params, 0, 2000);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<VO> queryPagingList(VO params, Integer startIndex, Integer pageSize) {
        if (CheckUtils.isEmpty(startIndex, pageSize))
            return null;
        Example example = newExample();
        Object criteria = newCriteria(example);
        // 添加要查询的参数
        addAllAndEqualPropertie(params, criteria);
        if (isGeneral()) {
            addAndEqualPropertie("deleted", false, Boolean.class, criteria);
        }
        // 设置分页
        setPagingProperties(example, Long.valueOf(startIndex), pageSize);
        List<DO> resultList = getDAO().selectByExample(example);

        return getVOList(resultList, voType);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int queryPagingCount(VO param) {
        Example example = newExample();
        Object criteria = newCriteria(example);
        // 添加要查询的参数
        addAllAndEqualPropertie(param, criteria);
        if (isGeneral()) {
            addAndEqualPropertie("deleted", false, Boolean.class, criteria);
        }
        // 设置分页
        return (int)getDAO().countByExample(example);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    protected DO getDoDetail(Serializable pk) {
        if (CheckUtils.isEmpty(pk)) {
            return null;
        }
        if (!isGeneral()) {
            return getDAO().selectByPrimaryKey(Integer.parseInt(pk.toString()));
        }
        List<DO> resultList = null;
        Example example = newExample();
        Object criteria = newCriteria(example);
        addAndEqualPropertie(getPkName(), pk, getPkType(), criteria);
        resultList = getDAO().selectByExample(example);
        if (CheckUtils.isEmpty(resultList)) {
            return null;
        }
        return resultList.get(0);
    }

    /**
     * @description 设置分页
     * @return void
     */
    private void setPagingProperties(Example example, Long startIndex, Integer pageSize) {
        if (startIndex == null || pageSize == null) {
            return;
        }
        String setOffset = "setOffset";
        String setLimit = "setLimit";

        try {
            Method setOffsetMethod = example.getClass().getMethod(setOffset, Long.class);
            setOffsetMethod.invoke(example, startIndex);
            Method setLimitMethod = example.getClass().getMethod(setLimit, Integer.class);
            setLimitMethod.invoke(example, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description create new Example instance
     * @return Example
     */
    protected Example newExample() {
        try {
            return exampleType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description create new Criteria instance
     * @return Object
     */
    protected Object newCriteria(Example example) {

        try {
            Method createCriteria = example.getClass().getMethod(CREATE_METHOD_NAME);
            return createCriteria.invoke(example);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description add equal query propertie
     * @return void
     */
    protected void addAndEqualPropertie(String propertieName, Object propertieValue, Class propertieType,
        Object criteria) {
        String methodName = getMethodName(propertieName, criteria);
        Method equalMethod;
        try {
            equalMethod = criteria.getClass().getMethod(methodName, propertieType);
            equalMethod.invoke(criteria, propertieValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void addAndInPropertie(String propertieName, Object propertieValue, Class propertieType,
        Object criteria) {
        String methodName = getMethodName(propertieName, criteria);
        Method equalMethod;
        try {
            equalMethod = criteria.getClass().getMethod(methodName, propertieType);
            equalMethod.invoke(criteria, propertieValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void addAllAndEqualPropertie(VO value, Object criteria) {
        if (value == null) {
            return;
        }
        Class type = value.getClass();
        // 取所有字段（包括基类的字段）
        Field[] allFields = type.getDeclaredFields();
        Class superClass = type.getSuperclass();
        while (superClass != null) {
            Field[] superFileds = superClass.getDeclaredFields();
            allFields = ArrayUtils.addAll(allFields, superFileds);
            superClass = superClass.getSuperclass();
        }
        for (Field field : allFields) {
            field.setAccessible(true);
            String propertieName = field.getName();
            Class<?> propertieType = field.getType();
            if (IGNORE_PROPERTIES.contains(propertieName)) {
                continue;
            }
            Object propertieValue = null;
            try {
                propertieValue = field.get(value);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (propertieValue == null) {
                continue;
            }
            this.addAndEqualPropertie(propertieName, propertieValue, propertieType, criteria);
        }

    }

    /**
     * @description 获取example中对应的方法名
     * @return String
     */
    protected String getMethodName(String propertieName, Object criteria) {
        String methodName = AND.toLowerCase() + propertieName.toLowerCase() + EQUAL_TO.toLowerCase();

        Method[] declaredMethods = criteria.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            String name = method.getName().toLowerCase();
            if (name.equals(methodName)) {
                return method.getName();
            }
        }
        return null;
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

    protected String getPkName() {
        String pkName = "id";
        if (isGeneral()) {
            pkName = "num";
        }
        return pkName;
    }

    /**
     * @description 主键类型
     * @return Class
     */
    protected Class getPkType() {
        if (isGeneral()) {
            return String.class;
        }
        return Integer.class;
    }

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
