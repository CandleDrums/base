/**
 * @Project base-biz
 * @Package com.cds.candledrums.base.biz.service.impl
 * @Class GeneralServiceImpl.java
 * @Date Oct 31, 2019 6:17:28 PM
 * @Copyright (c) 2019 CandleDrums.com All Right Reserved.
 */
package com.cds.base.biz.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cds.base.biz.service.GeneralService;
import com.cds.base.common.rule.NumRule;
import com.cds.base.dal.dao.GeneralDAO;
import com.cds.base.exception.DAOException;
import com.cds.base.exception.ValidateException;
import com.cds.base.util.bean.BeanUtils;
import com.cds.base.util.bean.CheckUtils;
import com.cds.base.util.generator.num.NumGenerator;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:17:28 PM
 * @version 1.0
 * @since JDK 1.8
 */
public abstract class GeneralServiceImpl<VO, DO> extends BaseServiceImpl<VO, DO> implements GeneralService<VO, DO> {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class,
        noRollbackFor = RuntimeException.class)
    public VO save(VO value) {
        if (CheckUtils.isEmpty(value))
            return null;
        String num = "";
        Object numExtised = BeanUtils.getProperty(value, "num");
        if (CheckUtils.isEmpty(numExtised)) {
            num = NumGenerator.nextNum(NumRule.getRule(getRuleClassName(value)));
            try {
                BeanUtils.setProperty(value, "num", num);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getDAO().save(getDO(value, doType));
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
        Object num = "";
        try {
            num = BeanUtils.getProperty(value, "num");
            if (CheckUtils.isEmpty(num)) {
                num = BeanUtils.getProperty(value, "id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CheckUtils.isEmpty(num)) {
            throw new ValidateException("未传递编号");
        }
        DO oldValue = getDAO().detail(String.valueOf(num));
        if (CheckUtils.isEmpty(oldValue)) {
            throw new ValidateException("数据不存在");
        }
        BeanUtils.copyProperties(value, oldValue);
        getDAO().modify(oldValue);
        BeanUtils.copyProperties(oldValue, value);
        return value;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, DAOException.class},
        noRollbackFor = RuntimeException.class)
    public boolean delete(String num) {
        if (CheckUtils.isEmpty(num))
            return false;
        getDAO().delete(num);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, DAOException.class},
        noRollbackFor = RuntimeException.class)
    public int deleteAll(List<String> idList) {
        if (CheckUtils.isEmpty(idList))
            return 0;
        int result = 0;
        for (String id : idList) {
            boolean success = delete(id);
            if (success) {
                result++;
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public VO detail(String num) {
        if (CheckUtils.isEmpty(num))
            return null;
        DO result = getDAO().detail(num);
        return getVO(result, voType);
    }

    @Override
    public List<VO> findList(List<String> idList) {
        if (CheckUtils.isEmpty(idList))
            return null;
        return getVOList(getDAO().detailList(idList), voType);
    }

    @Override
    protected abstract GeneralDAO<DO> getDAO();
}
