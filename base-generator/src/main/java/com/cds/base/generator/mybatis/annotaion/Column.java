/**
 * @Project base-server-dal
 * @Package com.cds.base.server.dal.mapper.generator
 * @Class Column.java
 * @Date 2017年11月20日 下午3:50:15
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.generator.mybatis.annotaion;

import java.io.Serializable;

/**
 * @Description 列
 * @Notes 未填写备注
 * @author liming
 * @Date 2017年11月20日 下午3:50:15
 * @version 1.0
 * @since JDK 1.7
 */
public class Column implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 列名
     */
    private String columnName;
    /**
     * 列注释
     */
    private String columnAnno;
    /**
     * 类型
     */
    private String type;
    /**
     * 类型类类型
     */
    private Class typeClass;
    /**
     * 属性名
     */
    private String fieldName;
    /**
     * db类型
     */
    private String dbType;
    /**
     * 长度
     */
    private int length;
    /**
     * 是否为空
     */
    private boolean isNull;
    /**
     * 是否唯一
     */
    private boolean isUnique;

    /**
     * @param columnName
     * @param type
     * @param fieldName
     * @param dbType
     */
    public Column(String columnName, String type, String fieldName, String dbType) {
        super();
        this.columnName = columnName;
        this.type = type;
        this.fieldName = fieldName;
        this.dbType = dbType;
    }

    /**
     * @param columnName
     * @param type
     * @param fieldName
     * @param dbType
     * @param length
     * @param isNull
     * @param isUnique
     */
    public Column(String columnName, String type, String fieldName, String dbType, int length, boolean isNull,
        boolean isUnique) {
        super();
        this.columnName = columnName;
        this.type = type;
        this.fieldName = fieldName;
        this.dbType = dbType;
        this.length = length;
        this.isNull = isNull;
        this.isUnique = isUnique;
    }

    public Column() {
        super();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean isNull) {
        this.isNull = isNull;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    /**
     * @return the columnAnno
     */
    public String getColumnAnno() {
        return columnAnno;
    }

    /**
     * @param columnAnno
     *            the columnAnno to set
     */
    public void setColumnAnno(String columnAnno) {
        this.columnAnno = columnAnno;
    }

    @Override
    public String toString() {
        return "Column [columnName=" + columnName + ", type=" + type + ", fieldName=" + fieldName + ", dbType=" + dbType
            + ", length=" + length + ", isNull=" + isNull + "]";
    }

    /**
     * typeClass
     *
     * @return typeClass
     */

    public Class getTypeClass() {
        return typeClass;
    }

    /**
     * @param typeClass
     *            the typeClass to set
     */
    public void setTypeClass(Class typeClass) {
        this.typeClass = typeClass;
    }

}
