/**
 * @Project base-dep-dal
 * @Package com.cds.base.dep.dal.mapper
 * @Class GeneratorMethods.java
 * @Date 2018年1月10日 下午5:43:00
 * @Copyright (c) CandleDrumS.com All Right Reserved.
 */
package com.cds.base.util.generator.mybatis.common;

import org.jdom2.Element;

import com.cds.base.util.generator.mybatis.annotaion.Column;
import com.cds.base.util.generator.mybatis.annotaion.Table;

/**
 * @Description 生成方法实现
 * @Notes 未填写备注
 * @author ming.li
 * @Date 2018年1月10日 下午5:43:00
 * @version 1.0
 * @since JDK 1.7
 */
public class GeneratorMethods {

    public static Element generalResultMap(Table table) {
        String entityName = table.getClassName().substring(table.getClassName().lastIndexOf(".") + 1);
        String resultMap = entityName + "ResultMap";
        Element elements = new Element("resultMap");
        elements.setAttribute("id", resultMap);
        elements.setAttribute("type", table.getClassName());
        for (Column c : table.getFields()) {
            Element element = null;
            if (c.getColumnName().equals("id")) {
                element = new Element("id");
            } else {
                element = new Element("result");
            }
            element.setAttribute("column", c.getColumnName());
            element.setAttribute("property", c.getFieldName());
            element.setAttribute("jdbcType", c.getDbType());
            element.setAttribute("javaType", c.getType());
            elements.addContent(element);
        }
        return elements;
    }

    public static Element generalSql(Table table) {
        StringBuilder textSb = new StringBuilder();
        for (Column c : table.getFields()) {
            textSb.append(c.getColumnName()).append(",");
        }
        Element columns = new Element("sql");
        columns.setAttribute("id",
            table.getClassName().substring(table.getClassName().lastIndexOf(".") + 1) + "Columns");
        columns.setText(textSb.toString().substring(0, textSb.length() - 1));
        return columns;
    }

    /**
     * @description 保存
     * @param table
     * @return
     * @returnType Element
     * @author Administrator
     */
    public static Element save(Table table) {
        Element insert = new Element("insert");
        insert.setAttribute("id", "save");
        insert.setAttribute("keyProperty", "id");
        insert.setAttribute("useGeneratedKeys", "true");
        insert.setAttribute("parameterType", table.getClassName());
        insert.addContent("\n");
        insert.addContent(createInsertSql(table));
        return insert;
    }

    /**
     * @description 保存全部
     * @param table
     * @return
     * @returnType Element
     * @author Administrator
     */
    public static Element saveAll(Table table) {
        Element insert = new Element("insert");
        insert.setAttribute("id", "saveAll");
        insert.setAttribute("parameterType", "java.util.List");
        insert.addContent("\n");
        insert.addContent(createInsertBatchSql(table));
        insert.addContent(createInsertBatchElement(table));
        return insert;
    }

    /**
     * @description 更新（通用Model）
     * @param table
     * @return
     * @returnType Element
     * @author Administrator
     */
    public static Element genenalUpdate(Table table) {
        Element update = new Element("update");
        update.setAttribute("id", "modify");
        update.setAttribute("parameterType", table.getClassName());
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(table.getTableName()).append("\n");
        update.addContent(sql.toString());
        update.addContent(createUpdateSql(table));
        update.addContent(
            "where num = #{num, jdbcType=VARCHAR,javaType=java.lang.String} and version = #{version, jdbcType=INTEGER,javaType=java.lang.Integer}");
        return update;
    }

    /**
     * @description 更新（基础Model）
     * @param table
     * @return
     * @returnType Element
     * @author Administrator
     */
    public static Element basicUpdate(Table table) {
        Element update = new Element("update");
        update.setAttribute("id", "modify");
        update.setAttribute("parameterType", table.getClassName());
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(table.getTableName()).append("\n");
        update.addContent(sql.toString());
        update.addContent(createUpdateSql(table));
        update.addContent(
            "where id = #{id, jdbcType=INTEGER,javaType=java.lang.Integer} and version = #{version, jdbcType=INTEGER,javaType=java.lang.Integer}");
        return update;
    }

    /**
     * @description 逻辑删除
     * @return Element
     */
    public static Element deletedByNum(Table table) {
        Element update = new Element("update");
        update.setAttribute("id", "delete");
        update.setAttribute("parameterType", "java.lang.String");
        StringBuilder sql = new StringBuilder();
        sql.append("\n \tupdate ").append(table.getTableName()).append("\n");
        update.addContent(sql.toString());
        update.addContent(deletedByNumSql(table));
        update.addContent("where num = #{num, jdbcType=VARCHAR,javaType=java.lang.String}");
        return update;
    }

    public static Element deletedById(Table table) {
        Element delete = new Element("delete");
        delete.setAttribute("id", "delete");
        delete.setAttribute("parameterType", "java.lang.Integer");
        delete.setText(deletedByIdSql(table));
        return delete;
    }

    public static Element findById(Table table) {
        String entityName = table.getClassName().substring(table.getClassName().lastIndexOf(".") + 1);
        String entityColumns = entityName + "Columns";
        String resultMap = entityName + "ResultMap";
        Element selectById = new Element("select");
        selectById.setAttribute("id", "detail");
        selectById.setAttribute("parameterType", "java.lang.Integer");
        selectById.setAttribute("resultMap", resultMap);
        selectById.addContent("select \n");
        selectById.addContent(new Element("include").setAttribute("refid", entityColumns));
        selectById.addContent("from " + table.getTableName()
            + " \n \t where id=#{id, jdbcType=INTEGER,javaType=java.lang.Integer} limit 1");
        return selectById;
    }

    public static Element findByIdList(Table table) {
        String entityName = table.getClassName().substring(table.getClassName().lastIndexOf(".") + 1);
        String entityColumns = entityName + "Columns";
        String resultMap = entityName + "ResultMap";
        Element selectById = new Element("select");
        selectById.setAttribute("id", "detailList");
        selectById.setAttribute("parameterType", "java.util.List");
        selectById.setAttribute("resultMap", resultMap);
        selectById.addContent("select \n");
        selectById.addContent(new Element("include").setAttribute("refid", entityColumns));
        selectById.addContent("from " + table.getTableName() + " \n \t where id in\n");
        Element forEach = new Element("foreach");
        forEach.setAttribute("collection", "list");
        forEach.setAttribute("item", "item");
        forEach.setAttribute("index", "index");
        forEach.setAttribute("separator", ",");
        forEach.setAttribute("open", "(");
        forEach.setAttribute("close", ")");
        forEach.addContent("\n \t  #{item} \t");
        selectById.addContent(forEach);

        return selectById;
    }

    public static Element findByNum(Table table) {
        String entityName = table.getClassName().substring(table.getClassName().lastIndexOf(".") + 1);
        String entityColumns = entityName + "Columns";
        String resultMap = entityName + "ResultMap";
        Element selectById = new Element("select");
        selectById.setAttribute("id", "detail");
        selectById.setAttribute("parameterType", "string");
        selectById.setAttribute("resultMap", resultMap);
        selectById.addContent("select \n");
        selectById.addContent(new Element("include").setAttribute("refid", entityColumns));
        selectById.addContent("from " + table.getTableName()
            + " \n\twhere num=#{num, jdbcType=VARCHAR,javaType=java.lang.String} limit 1");
        return selectById;
    }

    public static Element findByNumList(Table table) {
        String entityName = table.getClassName().substring(table.getClassName().lastIndexOf(".") + 1);
        String entityColumns = entityName + "Columns";
        String resultMap = entityName + "ResultMap";
        Element selectById = new Element("select");
        selectById.setAttribute("id", "detailList");
        selectById.setAttribute("parameterType", "java.util.List");
        selectById.setAttribute("resultMap", resultMap);
        selectById.addContent("select \n");
        selectById.addContent(new Element("include").setAttribute("refid", entityColumns));
        selectById.addContent("from " + table.getTableName() + " \n\twhere num in\n");

        Element forEach = new Element("foreach");
        forEach.setAttribute("collection", "list");
        forEach.setAttribute("item", "item");
        forEach.setAttribute("index", "index");
        forEach.setAttribute("separator", ",");
        forEach.setAttribute("open", "(");
        forEach.setAttribute("close", ")");
        forEach.addContent("\n \t  #{item} \t");
        selectById.addContent(forEach);
        return selectById;
    }

    public static Element queryAll(Table table) {
        String entityName = table.getClassName().substring(table.getClassName().lastIndexOf(".") + 1);
        String entityColumns = entityName + "Columns";
        String resultMap = entityName + "ResultMap";
        Element selectByParams = new Element("select");
        selectByParams.setAttribute("id", "queryAll");
        selectByParams.setAttribute("parameterType", "hashMap");
        selectByParams.setAttribute("resultMap", resultMap);
        selectByParams.addContent("select \n");
        selectByParams.addContent(new Element("include").setAttribute("refid", entityColumns));
        selectByParams.addContent("from " + table.getTableName() + " \n");
        selectByParams.addContent(createQueryByParamsWhere(table));
        return selectByParams;
    }

    public static Element queryPagingList(Table table) {
        String entityName = table.getClassName().substring(table.getClassName().lastIndexOf(".") + 1);
        String entityColumns = entityName + "Columns";
        String resultMap = entityName + "ResultMap";
        Element selectByParams = new Element("select");
        selectByParams.setAttribute("id", "queryPagingList");
        selectByParams.setAttribute("parameterType", table.getClassName());
        selectByParams.setAttribute("resultMap", resultMap);
        selectByParams.addContent("select \n");
        selectByParams.addContent(new Element("include").setAttribute("refid", entityColumns));
        selectByParams.addContent("from " + table.getTableName() + " \n");
        selectByParams.addContent(createQueryByParamsWhere(table));
        // selectByParams.addContent(
        // new Element("if").setAttribute("test", "pageSkip != null").addContent("limit #{pageSkip},#{pageSize}"));
        return selectByParams;
    }

    public static Element queryPagingCount(Table table) {
        Element selectByParams = new Element("select");
        selectByParams.setAttribute("id", "queryPagingCount");
        selectByParams.setAttribute("parameterType", table.getClassName());
        selectByParams.setAttribute("resultType", "int");
        selectByParams.addContent("\t select \n");
        selectByParams.addContent("\t count(*)\n");
        selectByParams.addContent("\t from " + table.getTableName() + " \n");
        selectByParams.addContent(createQueryByParamsWhere(table));
        return selectByParams;
    }

    public static Element contains(Table table) {
        Element selectByParams = new Element("select");
        selectByParams.setAttribute("id", "contains");
        selectByParams.setAttribute("parameterType", "hashMap");
        selectByParams.setAttribute("resultType", "boolean");
        selectByParams.addContent("\t select \n");
        selectByParams.addContent("\t count(*)\n");
        selectByParams.addContent("\t from " + table.getTableName() + " \n");
        selectByParams.addContent(createQueryByParamsWhere(table));
        selectByParams.addContent(" limit 2");
        return selectByParams;
    }

    public static Element createQueryByParamsWhere(Table table) {
        Element where = new Element("trim");
        // <trim prefix="WHERE" prefixOverrides="and|or">
        where.setAttribute("prefix", "where");
        where.setAttribute("prefixOverrides", "and|or");
        for (Column c : table.getFields()) {
            if (c.getColumnName().equals("id") || c.getColumnName().equals("version")
                || c.getColumnName().equals("create_date") || c.getColumnName().equals("update_date")
                || c.getColumnName().equals("delete_date")) {
                continue;
            }
            if (c.getColumnName().equals("deleted")) {
                where.addContent("and deleted = 0");
                continue;
            }
            Element ifE = new Element("if");
            if (c.getTypeClass().getName().equals("java.lang.String")) {
                ifE.setAttribute("test", c.getFieldName() + " != null and " + c.getFieldName() + " !=''");
            } else {
                ifE.setAttribute("test", c.getFieldName() + " != null");
            }

            ifE.setText("and " + c.getColumnName() + " = #{" + c.getFieldName() + ", jdbcType=" + c.getDbType()
                + ", javaType=" + c.getType() + "}");
            where.addContent(ifE);
        }
        return where;
    }

    public static Element createUpdateSql(Table table) {
        Element set = new Element("set");
        if (!table.isBasicModel()) {
            set.addContent("version = version + 1,");
        }
        for (Column c : table.getFields()) {
            if (c.getColumnName().equals("id") || c.getColumnName().equals("version")
                || c.getColumnName().equals("create_date") || c.getColumnName().equals("update_date")) {
                continue;
            }
            Element ifE = new Element("if");
            if (c.getTypeClass().getName().equals("java.lang.String")) {
                ifE.setAttribute("test", c.getFieldName() + " != null and " + c.getFieldName() + " !=''");
            } else {
                ifE.setAttribute("test", c.getFieldName() + " != null");
            }
            ifE.setText(c.getColumnName() + " = #{" + c.getFieldName() + ", jdbcType=" + c.getDbType() + ", javaType="
                + c.getType() + "},\n");
            set.addContent(ifE);
        }
        set.addContent("update_date = current_timestamp");
        return set;
    }

    private static Element createInsertBatchElement(Table table) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(table.getTableName()).append("\n \t").append("(");
        StringBuilder insertColumn = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (Column c : table.getFields()) {
            if (c.getColumnName().equals("id")) {
                // values.append("0,\n ");
                continue;
            }
            if (c.getColumnName().equalsIgnoreCase("delete_date")) {
                continue;
            }
            insertColumn.append(c.getColumnName()).append(",");
            if (c.getColumnName().equals("version")) {
                continue;
            } else if (c.getColumnName().equals("deleted")) {
                values.append("\t 0,\n");
            } else if (c.getColumnName().equals("create_date")) {
                values.append("\t current_timestamp,\n");
            } else if (c.getColumnName().equals("update_date")) {
                values.append("\t current_timestamp \n");
            } else {
                values.append("\t #{").append("item." + c.getFieldName()).append(", ").append("jdbcType=")
                    .append(c.getDbType()).append(", javaType=").append(c.getType()).append("},\n");
            }
        }
        Element forEach = new Element("foreach");
        forEach.setAttribute("collection", "list");
        forEach.setAttribute("item", "item");
        forEach.setAttribute("index", "index");
        forEach.setAttribute("separator", ",");
        forEach.addContent("\n \t (" + values.toString() + "\t)");
        return forEach;
    }

    private static String deletedByNumSql(Table table) {

        StringBuilder sql = new StringBuilder();
        sql.append("\t");
        sql.append("set version = version + 1,");
        sql.append("deleted = 1,");
        sql.append("update_date = current_timestamp \n \t");
        return sql.toString();
    }

    private static String deletedByIdSql(Table table) {
        StringBuilder sql = new StringBuilder();
        sql.append("\n \t delete from ").append(table.getTableName()).append(" \n \t where \n \t id=")
            .append("#{id, jdbcType=INTEGER,javaType=java.lang.Integer}");
        return sql.toString();
    }

    private static String createInsertSql(Table table) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(table.getTableName()).append("\n \t").append("(");
        StringBuilder insertColumn = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (Column c : table.getFields()) {
            if (c.getColumnName().equals("id")) {
                // values.append("0,\n");
                continue;
            }
            if (c.getColumnName().equalsIgnoreCase("delete_date")) {
                continue;
            }
            insertColumn.append(c.getColumnName()).append(",");
            if (c.getColumnName().equals("version")) {
                values.append("\t 0, \n");
                continue;
            } else if (c.getColumnName().equals("deleted")) {
                values.append("\t 0 \n");
            } else if (c.getColumnName().equals("create_date")) {
                values.append("\t current_timestamp,\n");
            } else if (c.getColumnName().equals("update_date")) {
                values.append("\t current_timestamp,\n");
            } else {
                values.append("\t #{").append(c.getFieldName()).append(", ").append("jdbcType=").append(c.getDbType())
                    .append(", javaType=").append(c.getType()).append("},\n");
            }
        }
        String value = values.toString().trim();
        int i2 = value.length();
        int i = value.lastIndexOf(",");
        if (i == (i2 - 1)) {
            value = value.substring(0, value.length() - 1);
        }
        sql.append(insertColumn.toString().substring(0, insertColumn.toString().length() - 1));
        sql.append(") \n \tvalues \n \t (").append(value).append("\t) \n");
        return sql.toString();
    }

    private static String createInsertBatchSql(Table table) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(table.getTableName()).append("\n \t").append("(");
        StringBuilder insertColumn = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (Column c : table.getFields()) {
            if (c.getColumnName().equals("id")) {
                // values.append("0,\n \t");
                continue;
            }
            if (c.getColumnName().equalsIgnoreCase("delete_date")) {
                continue;
            }
            insertColumn.append(c.getColumnName()).append(",");
            if (c.getColumnName().equals("version")) {
                values.append("\t 0, \n");
                continue;
            } else if (c.getColumnName().equals("deleted")) {
                values.append("\t 0\n");
            } else if (c.getColumnName().equals("create_date")) {
                values.append("\t current_timestamp,\n");
            } else if (c.getColumnName().equals("update_date")) {
                values.append("\t current_timestamp,\n");
            } else {
                values.append("#{").append("item." + c.getFieldName()).append(", ").append("jdbcType=")
                    .append(c.getDbType()).append(", javaType=").append(c.getType()).append("},\n");
            }
        }
        Element forEach = new Element("foreach");
        forEach.setAttribute("collection", "list");
        forEach.setAttribute("item", "item");
        forEach.setAttribute("index", "index");
        forEach.setAttribute("separator", ",");
        forEach.addContent("(" + values.toString() + ")");
        sql.append(insertColumn.toString().substring(0, insertColumn.toString().length() - 1))
            .append(") \n \t values  \n \t");
        return sql.toString();
    }

}
