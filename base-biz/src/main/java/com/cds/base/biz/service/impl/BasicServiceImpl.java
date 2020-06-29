/**
 * @Project base-biz
 * @Package com.cds.candledrums.base.biz.service.impl
 * @Class BasicServiceImpl.java
 * @Date Oct 31, 2019 6:17:13 PM
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.biz.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cds.base.biz.service.BasicService;
import com.cds.base.dal.dao.BasicDAO;
import com.cds.base.exception.server.DAOException;
import com.cds.base.util.bean.CheckUtils;

/**
 * @Description 基本Service实现
 * @Notes 未填写备注
 * @author liming
 * @Date Oct 31, 2019 6:17:13 PM
 * @version 1.0
 * @since JDK 1.8
 */
public abstract class BasicServiceImpl<VO, DO> extends BaseServiceImpl<VO, DO> implements BasicService<VO, DO> {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class,
        noRollbackFor = RuntimeException.class)
    public VO save(VO value) {
        if (CheckUtils.isEmpty(value))
            return null;
        getDAO().save(getDO(value, doType));
        return value;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, DAOException.class},
        noRollbackFor = RuntimeException.class)
    public boolean delete(Integer id) {
        if (CheckUtils.isEmpty(id))
            return false;
        getDAO().delete(id);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, DAOException.class},
        noRollbackFor = RuntimeException.class)
    public int deleteAll(List<Integer> idList) {
        if (CheckUtils.isEmpty(idList))
            return 0;
        int result = 0;
        for (Integer id : idList) {
            boolean success = delete(id);
            if (success) {
                result++;
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public VO detail(Integer id) {
        if (CheckUtils.isEmpty(id))
            return null;
        DO result = getDAO().detail(id);
        return getVO(result, voType);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<VO> findList(List<Integer> idList) {
        if (CheckUtils.isEmpty(idList))
            return null;
        return getVOList(getDAO().detailList(idList), voType);
    }

    @Override
    protected abstract BasicDAO<DO> getDAO();
}
