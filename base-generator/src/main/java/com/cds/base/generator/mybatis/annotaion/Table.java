/**
 * @Project base-server-dal
 * @Package com.cds.base.server.dal.mapper.generator
 * @Class Table.java
 * @Date 2017年11月20日 下午3:49:18
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.generator.mybatis.annotaion;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 表
 * @Notes 未填写备注
 * @author liming
 * @Date 2017年11月20日 下午3:49:18
 * @version 1.0
 * @since JDK 1.7
 */
public class Table implements Serializable {

    private static final long serialVersionUID = 1L;
    private String tableName;
    private String className;
    private List<Column> fields;
    private boolean basicModel;

    public Table(String tableName, String className, List<Column> fields) {
        super();
        this.tableName = tableName;
        this.className = className;
        this.fields = fields;
    }

    public Table() {
        super();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Column> getFields() {
        return fields;
    }

    public void setFields(List<Column> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "Table [tableName=" + tableName + ", className=" + className + ", fields=" + fields + "]";
    }

    /**
     * basicModel
     *
     * @return basicModel
     */

    public boolean isBasicModel() {
        return basicModel;
    }

    /**
     * @param basicModel
     *            the basicModel to set
     */
    public void setBasicModel(boolean basicModel) {
        this.basicModel = basicModel;
    }

}
