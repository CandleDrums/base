/**
 * @Project base-biz
 * @Package com.cds.base.biz.service.impl.custom
 * @Class CustomBaseSeerviceImpl.java
 * @Date Nov 11, 2020 4:42:04 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.biz.service.impl.custom;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cds.base.biz.service.BaseService;
import com.cds.base.common.exception.ValidationException;
import com.cds.base.common.page.Page;
import com.cds.base.dal.custom.dao.CustomBaseDAO;
import com.cds.base.exception.server.DAOException;
import com.cds.base.generator.num.NumGenerator;
import com.cds.base.util.bean.BeanUtils;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date Nov 11, 2020 4:42:04 PM
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
    timeout = TransactionDefinition.TIMEOUT_DEFAULT)
public abstract class CustomBaseServiceImpl<VO, DO> implements BaseService<VO> {

    // VO类型
    protected Class<VO> voType;
    // DO类型
    protected Class<DO> doType;

    // 钩子方法
    protected abstract CustomBaseDAO<DO> getDAO();

    /**
     * 获取泛型
     */
    @SuppressWarnings("unchecked")
    public CustomBaseServiceImpl() {
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
                BeanUtils.setProperty(doValue, "deleted", false);
                BeanUtils.setProperty(doValue, "version", 0);
                BeanUtils.setProperty(doValue, "updateDate", new Date());
                BeanUtils.setProperty(doValue, "createDate", new Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getDAO().save(doValue);
        if (CheckUtils.isNotEmpty(num)) {
            return detail(num);
        }
        return value;

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
        return getVOList(getDAO().queryPagingList(getDO(param, doType), 0, 1000), voType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, DAOException.class},
        noRollbackFor = RuntimeException.class)
    public VO modify(VO value) {
        if (CheckUtils.isEmpty(value)) {
            return null;
        }
        Object pk = "";
        try {
            pk = BeanUtils.getProperty(value, "num");
            if (CheckUtils.isEmpty(pk)) {
                pk = BeanUtils.getProperty(value, "id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CheckUtils.isEmpty(pk)) {
            throw new ValidationException("未传递编号");
        }
        DO oldValue = getDAO().detail(String.valueOf(pk));
        if (CheckUtils.isEmpty(oldValue)) {
            throw new ValidationException("数据不存在");
        }
        BeanUtils.copyProperties(value, oldValue);
        getDAO().modify(oldValue);
        BeanUtils.copyProperties(oldValue, value);
        return value;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, DAOException.class},
        noRollbackFor = RuntimeException.class)
    public boolean delete(Serializable pk) {
        return getDAO().delete(pk) == 1;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public VO detail(Serializable pk) {
        return getVO(getDAO().detail(pk), voType);
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
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<VO> queryPaging(VO params, Integer startIndex, Integer pageSize) {
        if (CheckUtils.isEmpty(params, pageSize))
            return null;
        List<VO> voList = getVOList(getDAO().queryPagingList(getDO(params, doType), startIndex, pageSize), voType);
        int count = count(params);
        Page<VO> page = new Page<>();
        page.setList(voList);
        page.setTotal(count);
        return page;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int count(VO param) {
        if (CheckUtils.isEmpty(param))
            return 0;
        return getDAO().queryPagingCount(getDO(param, doType));
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
