/**
 * @Project base-biz
 * @Package com.cds.candledrums.base.biz.service.impl
 * @Class GeneralServiceImpl.java
 * @Date Oct 31, 2019 6:17:28 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.biz.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cds.base.biz.service.GeneralService;
import com.cds.base.common.exception.ValidationException;
import com.cds.base.dal.model.GeneralModel;
import com.cds.base.exception.server.DAOException;
import com.cds.base.generator.num.NumGenerator;
import com.cds.base.util.bean.BeanUtils;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description 通用Service实现
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:17:28 PM
 * @version 1.0
 * @since JDK 1.8
 */
public abstract class GeneralServiceImpl<VO, DO extends GeneralModel, Example> extends BaseServiceImpl<VO, DO, Example>
    implements GeneralService<VO> {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class,
        noRollbackFor = RuntimeException.class)
    public VO save(VO value) {
        if (CheckUtils.isEmpty(value))
            return null;
        String num = NumGenerator.generateAndSetNum(value);
        Boolean success = getDAO().insertSelective(getDO(value, doType)) > 0;
        if (!success) {
            return null;
        }
        if (CheckUtils.isNotEmpty(num)) {
            return detail(num);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class,
        noRollbackFor = RuntimeException.class)
    public VO modify(VO value) {

        if (CheckUtils.isEmpty(value)) {
            return null;
        }
        Object pk = null;
        String pkName = "num";
        try {
            pk = BeanUtils.getProperty(value, "num");
            // 没有num时
            if (CheckUtils.isEmpty(pk)) {
                pk = BeanUtils.getProperty(value, "id");
                pkName = "id";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CheckUtils.isEmpty(pk)) {
            throw new ValidationException("请指定主键");
        }
        DO oldValue = getDoDetail(pk, pkName);
        if (CheckUtils.isEmpty(oldValue)) {
            throw new ValidationException("数据不存在");
        }
        BeanUtils.copyProperties(value, oldValue);

        Example example = newExample();
        Object criteria = newCriteria(example);
        addAndEqualPropertie(pkName, pk, pk.getClass(), criteria);
        if (oldValue.getVersion() != null) {
            addAndEqualPropertie("version", oldValue.getVersion(), Integer.class, criteria);
            oldValue.setVersion(oldValue.getVersion() + 1);
        }
        int successCount = getDAO().updateByExampleSelective(oldValue, example);
        if (successCount < 1) {
            throw new DAOException("未修改任何数据，请确认主键值！");
        } else if (successCount > 1) {
            throw new DAOException("存在多条记录被修改，需要回滚数据！");
        }
        BeanUtils.copyProperties(oldValue, value);
        return value;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, DAOException.class},
        noRollbackFor = RuntimeException.class)
    public boolean delete(String num) {
        DO doDetail = getDoDetail(num, "num");
        if (doDetail == null) {
            return false;
        }
        Example example = newExample();
        Object criteria = newCriteria(example);
        addAndEqualPropertie("num", num, String.class, criteria);
        doDetail.setDeleted(true);
        return getDAO().updateByExample(doDetail, example) == 1;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public VO detail(String num) {
        List<DO> resultList = null;
        Example example = newExample();
        Object criteria = newCriteria(example);
        addAndEqualPropertie("num", num, String.class, criteria);
        resultList = getDAO().selectByExample(example);
        if (CheckUtils.isEmpty(resultList)) {
            return null;
        }
        return getVO(resultList.get(0), voType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, DAOException.class},
        noRollbackFor = RuntimeException.class)
    public int deleteAll(List<String> numList) {
        if (CheckUtils.isEmpty(numList))
            return 0;
        int result = 0;
        for (String num : numList) {
            boolean success = delete(num);
            if (success) {
                result++;
            }
        }
        return result;
    }

}
