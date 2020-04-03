/**
 * @Project base-dal
 * @Package com.cds.base.dal.model
 * @Class BasicModel.java
 * @Date 2019年9月10日 上午11:49:27
 * @Copyright (c) 2019 YOUWE All Right Reserved.
 */
package com.cds.base.dal.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Description 基础模型
 * @Notes 所有Model都需包含BasicModel的属性
 * @author ming.li
 * @Date 2017年12月19日 下午3:29:06
 * @version 1.0
 * @since JDK 1.7
 */
@Data
@ToString(callSuper = true, exclude = "id")
@EqualsAndHashCode(callSuper = false)
public class BasicModel implements Serializable {

	private static final long serialVersionUID = 1L;
	// id,自增主键，由数据库自动生成
	private Integer id;
	// 创建时间，非空
	private Date createDate;

}
