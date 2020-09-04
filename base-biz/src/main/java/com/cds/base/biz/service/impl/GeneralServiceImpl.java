/**
 * @Project base-biz
 * @Package com.cds.candledrums.base.biz.service.impl
 * @Class GeneralServiceImpl.java
 * @Date Oct 31, 2019 6:17:28 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.biz.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cds.base.biz.service.GeneralService;
import com.cds.base.common.rule.NumRuleMap;
import com.cds.base.dal.dao.BaseDAO;
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
public abstract class GeneralServiceImpl<VO, DO, Example> extends BaseServiceImpl<VO, DO, Example>
    implements GeneralService<VO> {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class,
        noRollbackFor = RuntimeException.class)
    public VO save(VO value) {
        if (CheckUtils.isEmpty(value))
            return null;
        String num = "";
        Object numExtised = BeanUtils.getProperty(value, "num");
        if (CheckUtils.isEmpty(numExtised)) {
            num = NumGenerator.nextNum(NumRuleMap.getNumRule(value.getClass()));
            try {
                BeanUtils.setProperty(value, "num", num);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getDAO().insertSelective(getDO(value, doType));
        if (CheckUtils.isNotEmpty(num)) {
            return detail(num);
        }
        return value;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class,
        noRollbackFor = RuntimeException.class)
    public abstract VO modify(VO value);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, DAOException.class},
        noRollbackFor = RuntimeException.class)
    public abstract boolean delete(String num);

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public abstract VO detail(String num);

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

    @Override
    protected abstract BaseDAO<DO, Serializable, Example> getDAO();

}
