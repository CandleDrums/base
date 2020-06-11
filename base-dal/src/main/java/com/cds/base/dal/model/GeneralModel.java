/**
 * @Project base-dal
 * @Package com.cds.base.dal.model
 * @Class GeneralModel.java
 * @Date 2019年9月10日 上午11:49:36
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.dal.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description 通用模型
 * @Notes 一般用于表达业务实体
 * @author Administrator
 * @Date 2017年12月19日 下午3:42:43
 * @version 1.0
 * @since JDK 1.7
 */
@Data
@ToString(callSuper = true, exclude = "version")
@EqualsAndHashCode(callSuper = true)
public class GeneralModel extends BasicModel {

    private static final long serialVersionUID = 1L;
    // 业务编码，非空，一般由生成器生成，每个表有单独前缀
    private String num;
    // 版本号，非空，用于控制并发更新
    private Integer version;
    // 更新时间，非空，默认与创建时间相同
    private Date updateDate;
    // 逻辑删除标识，0或NULL为正常，1为已删除，删除时需更新updateDate
    private boolean deleted;

}
