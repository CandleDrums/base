/**
 * @Project base-server-dal
 * @Package com.cds.base.server.dal.util
 * @Class MybatisMapperGenerator.java
 * @Date 2017年11月20日 下午3:45:48
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved
 */
package com.cds.base.util.generator.mybatis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.cds.base.common.constants.Amount;
import com.cds.base.util.bean.CheckUtils;
import com.cds.base.util.generator.mybatis.annotaion.Column;
import com.cds.base.util.generator.mybatis.annotaion.ColumnAnnotation;
import com.cds.base.util.generator.mybatis.annotaion.Table;
import com.cds.base.util.generator.mybatis.annotaion.TableAnnotation;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description Mybitas 配置文件生成器
 * @Notes 未填写备注
 * @author liming
 * @Date 2017年11月20日 下午3:45:48
 * @version 1.0
 * @since JDK 1.7
 */
@Slf4j
public class MybatisMapperGenerator {

    static final String PUBLIC_ID = "-//mybatis.org//DTD Mapper 3.0//EN";
    static final String SYSTEM_ID = "http://mybatis.org/dtd/mybatis-3-mapper.dtd";
    public static final char UNDERLINE = '_';

    private static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 生成sql
     * 
     * @param table
     */
    private static void createTableSql(Table table) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append(table.getTableName()).append("(\n");
        for (Column c : table.getFields()) {
            if (c.getColumnName().equals("id")) {
                sb.append("\t" + c.getColumnName()).append(" ").append(c.getDbType())
                    .append(" NOT NULL AUTO_INCREMENT,\n");
                continue;
            }
            if (c.getColumnName().equals("version")) {
                sb.append("\t" + c.getColumnName()).append(" ").append(c.getDbType()).append(" NOT NULL,\n");
                continue;
            }
            if (c.getDbType().equals("TIMESTAMP")) {
                sb.append("\t" + c.getColumnName()).append(" ").append(" DATETIME");
            } else {
                sb.append("\t" + c.getColumnName()).append(" ").append(c.getDbType());
            }

            if (c.getDbType().equals("VARCHAR") || c.getDbType().equals("BIGINT")) {
                sb.append("(").append(c.getLength()).append(") ");
            } else if (c.getDbType().equals("DECIMAL")) {
                sb.append("(15,4) ");
            } else if (c.getDbType().equals("INT")) {
                sb.append("(").append(c.getLength()).append(") ");
            } else if (c.getDbType().equals("SMALLINT") || c.getDbType().equals("Boolean")) {
                sb.append("(1) ");
            } else {
                sb.append(" ");
            }
            if (c.getColumnName().equals("deleted") || c.getColumnName().equals("create_date")
                || c.getColumnName().equals("update_date")) {
                sb.append(" NOT NULL");
            } else {
                sb.append(c.isNull() ? " " : "NOT NULL ");
            }
            // 唯一
            sb.append(c.isUnique() ? " UNIQUE" : "");
            // 备注
            if (c.getColumnAnno() != null && !c.getColumnAnno().equals("")) {
                sb.append(" COMMENT '" + c.getColumnAnno() + "'");
            }
            sb.append(",\n");
        }
        String s = sb.toString();
        s = s + "PRIMARY KEY(id)\n)ENGINE=InnoDB AUTO_INCREMENT=1 CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';\n";
    }

    /**
     * 生成table
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Table getTable(Class clazz) {
        Table table = new Table();
        TableAnnotation tableAnno = (TableAnnotation)clazz.getAnnotation(TableAnnotation.class);
        table.setClassName(clazz.getName());
        table.setTableName(tableAnno.value());
        Map<String, Column> fieldMap = new HashMap();

        Class parent = clazz.getSuperclass();
        if (parent.getSimpleName().equals("GeneralModel")) {
            Class basic = parent.getSuperclass();
            Field[] basicFields = basic.getDeclaredFields();
            addColumn(basicFields, fieldMap);
        }
        Field[] generalfields = parent.getDeclaredFields();
        addColumn(generalfields, fieldMap);
        Field[] subFields = clazz.getDeclaredFields();
        addColumn(subFields, fieldMap);
        table.setFields(putColumn(fieldMap));
        return table;
    }

    private static List<Column> putColumn(Map<String, Column> fieldMap) {
        List<Column> headList = new ArrayList<Column>();
        List<Column> bodyList = new ArrayList<Column>();
        List<Column> tailList = new ArrayList<Column>();
        Column id = fieldMap.get("id");
        if (id != null) {
            headList.add(id);
            fieldMap.remove("id");
        }
        Column version = fieldMap.get("version");
        if (version != null) {
            headList.add(version);
            fieldMap.remove("version");
        }
        Column num = fieldMap.get("num");
        if (num != null) {
            headList.add(num);
            fieldMap.remove("num");
        }
        Column create_date = fieldMap.get("create_date");
        if (create_date != null) {
            tailList.add(create_date);
            fieldMap.remove("create_date");
        }
        Column update_date = fieldMap.get("update_date");
        if (update_date != null) {
            tailList.add(update_date);
            fieldMap.remove("update_date");
        }
        Column deleted = fieldMap.get("deleted");
        if (deleted != null) {
            tailList.add(deleted);
            fieldMap.remove("deleted");
        }
        // Column delete_date = fieldMap.get("delete_date");
        // if (delete_date != null) {
        // tailList.add(delete_date);
        // fieldMap.remove("delete_date");
        // }
        for (String key : fieldMap.keySet()) {
            Column c = fieldMap.get(key);
            bodyList.add(c);
        }
        headList.addAll(bodyList);
        headList.addAll(tailList);
        return headList;
    }

    @SuppressWarnings("rawtypes")
    private static void addColumn(Field[] fieldsParent, Map<String, Column> fieldMap) {
        Column c = null;
        ColumnAnnotation column = null;
        String fieldName = null;
        Class type = null;
        for (Field field : fieldsParent) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            column = field.getAnnotation(ColumnAnnotation.class);
            fieldName = field.getName();
            type = field.getType();
            c = new Column();
            c.setColumnName(camelToUnderline(fieldName));
            c.setType(type.getName());
            c.setTypeClass(type);
            c.setFieldName(fieldName);
            if (column == null) {
                c.setNull(true);
                c.setLength(32);
                if (Long.class.equals(field.getType())) {
                    c.setDbType("BIGINT");
                    c.setFieldName(fieldName);
                    c.setLength(18);
                } else if (String.class.equals(field.getType())) {
                    c.setDbType("VARCHAR");
                    c.setFieldName(fieldName);
                } else if (Integer.class.equals(field.getType())) {
                    c.setDbType("INTEGER");
                    c.setFieldName(fieldName);
                } else if (Date.class.equals(field.getType())) {
                    c.setDbType("TIMESTAMP");
                    c.setFieldName(fieldName);
                } else if (boolean.class.equals(field.getType())) {
                    c.setDbType("SMALLINT");
                    c.setFieldName(fieldName);
                    c.setLength(1);
                } else if (Boolean.class.equals(field.getType())) {
                    c.setDbType("SMALLINT");
                    c.setFieldName(fieldName);
                    c.setLength(1);
                } else if (Amount.class.equals(field.getType())) {
                    c.setDbType("DECIMAL");
                    c.setFieldName(fieldName);
                    c.setLength(1);
                } else {
                    c.setDbType("VARCHAR");
                    c.setFieldName(fieldName);
                    c.setLength(30);
                }
            } else {
                if (CheckUtils.isNotEmpty(column.columnName())) {
                    c.setColumnName(column.columnName());
                }
                if (CheckUtils.isEmpty(column.dbType())) {
                    if (Long.class.equals(field.getType())) {
                        c.setDbType("BIGINT");
                    } else if (String.class.equals(field.getType())) {
                        c.setDbType("VARCHAR");
                    } else if (Integer.class.equals(field.getType())) {
                        c.setDbType("INTEGER");
                    } else if (Date.class.equals(field.getType())) {
                        c.setDbType("TIMESTAMP");
                    } else if (boolean.class.equals(field.getType())) {
                        c.setDbType("SMALLINT");
                    } else if (Boolean.class.equals(field.getType())) {
                        c.setDbType("SMALLINT");
                    } else if (Amount.class.equals(field.getType())) {
                        c.setDbType("DECIMAL");
                        c.setFieldName(fieldName);
                        c.setLength(1);
                    } else {
                        c.setDbType("VARCHAR");
                    }
                } else {
                    c.setDbType(column.dbType());
                }
                c.setLength(column.length());
                c.setNull(column.isNull());
                c.setUnique(column.isUnique());
            }
            fieldMap.put(c.getColumnName(), c);
        }
    }

    private static String getDAOName(Table table) {
        String className = table.getClassName();
        return className.replace("model", "dao").replace("DO", "DAO");
    }

    /**
     * @description 生成通用Mapper
     * @param table
     * @param fileName
     * @throws Exception
     * @returnType void
     * @author Administrator
     */
    private static void createGeneralMapper(Table table, String fileName, String daoName) throws Exception {
        Element root = new Element("mapper");
        root.setAttribute("namespace", daoName);
        Document Doc = new Document(root);
        DocType docType = new DocType("mapper");
        docType.setPublicID(PUBLIC_ID);
        docType.setSystemID(SYSTEM_ID);
        Doc.setDocType(docType);
        // 添加resultMap
        root.addContent(GeneratorMethods.generalResultMap(table));
        // 添加sql
        root.addContent(GeneratorMethods.generalSql(table));
        // 添加save
        root.addContent(GeneratorMethods.save(table));
        // 添加saveAll
        // root.addContent(GeneratorMethods.saveAll(table));
        // 添加update
        root.addContent(GeneratorMethods.genenalUpdate(table));
        // 添加delete
        root.addContent(GeneratorMethods.deletedByNum(table));
        // 添加findByNum
        root.addContent(GeneratorMethods.findByNum(table));
        // 添加findList
        root.addContent(GeneratorMethods.findByNumList(table));
        // 添加contains
        root.addContent(GeneratorMethods.contains(table));
        // 添加queryAll
        // root.addContent(GeneratorMethods.queryAll(table));
        // 添加queryPagingList
        root.addContent(GeneratorMethods.queryPagingList(table));
        // 添加queryPagingCount
        root.addContent(GeneratorMethods.queryPagingCount(table));
        // 输出
        outPut(Doc, fileName);
    }

    /**
     * @description 生成基础Mapper
     * @param table
     * @param fileName
     * @throws Exception
     * @returnType void
     * @author Administrator
     */
    private static void createBasiclMapper(Table table, String fileName, String daoName) throws Exception {
        Element root = new Element("mapper");
        root.setAttribute("namespace", daoName);
        Document Doc = new Document(root);
        DocType docType = new DocType("mapper");
        docType.setPublicID(PUBLIC_ID);
        docType.setSystemID(SYSTEM_ID);
        Doc.setDocType(docType);
        // 添加resultMap
        root.addContent(GeneratorMethods.generalResultMap(table));
        // 添加sql
        root.addContent(GeneratorMethods.generalSql(table));
        // 添加save
        root.addContent(GeneratorMethods.save(table));
        // 添加saveAll
        root.addContent(GeneratorMethods.saveAll(table));
        // 添加update
        // root.addContent(GeneratorMethods.basicUpdate(table));
        // 添加delete
        root.addContent(GeneratorMethods.deletedById(table));
        // 添加findId
        root.addContent(GeneratorMethods.findById(table));
        // 添加findList
        root.addContent(GeneratorMethods.findByIdList(table));
        // 添加contains
        root.addContent(GeneratorMethods.contains(table));
        // 添加queryAll
        // root.addContent(GeneratorMethods.queryAll(table));
        // 添加queryPagingList
        root.addContent(GeneratorMethods.queryPagingList(table));
        // 添加queryPagingCount
        root.addContent(GeneratorMethods.queryPagingCount(table));
        // 输出
        outPut(Doc, fileName);
    }

    private static void outPut(Document doc, String fileName) throws FileNotFoundException, IOException {
        Format format = Format.getPrettyFormat();
        XMLOutputter XMLOut = new XMLOutputter(format);
        XMLOut.output(doc, new FileOutputStream(fileName));
    }

    private static void createDAO(Class c, String splitSlash) {
        String daoPath = System.getProperty("user.dir") + splitSlash + "src" + splitSlash + "main" + splitSlash + "java"
            + splitSlash;
        String packageName = c.getPackage().getName().replace(".", splitSlash).replace("model", "dao");
        File folder = new File(daoPath + packageName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = c.getSimpleName().replace("DO", "DAO");
        File daoFile = new File(daoPath + packageName + splitSlash + className);
    }

    /**
     * @description 是否为通用Model
     * @param c
     * @return
     * @returnType boolean
     * @author Administrator
     */
    private static boolean isGeneralModel(Class c) {
        String name = c.getSuperclass().getSimpleName();
        return "GeneralModel".equals(name);
    }

    /**
     * @description 生成需要的文件，默认生成的路径为当前DO项目根目录src/main/resources/mapper <br/>
     *              默认生成的文件名为：class名+Mapper.xml 例如：c.getSimpleName()+Mapper.xml
     * @param c
     * @returnType void
     * @author Administrator
     */
    @SuppressWarnings("rawtypes")
    public static void build(Class c) {
        if (c == null) {
            log.info("class is null,can not init");
        }
        boolean isGeneral = isGeneralModel(c);

        try {

            // 获取当前目录
            Table table = getTable(c);
            String daoName = getDAOName(table);
            int index = daoName.lastIndexOf(".");
            String splitSlash = "\\";
            String os = System.getProperty("os.name");
            // 说明是Mac系统
            if (os.toLowerCase().startsWith("mac")) {
                splitSlash = "/";
            }
            String mapperPath = System.getProperty("user.dir") + splitSlash + "src" + splitSlash + "main" + splitSlash
                + "resources" + splitSlash + "mapper" + splitSlash;
            String mapperUrl = mapperPath + daoName.substring(index + 1) + ".xml";
            File folder = new File(mapperPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            if (isGeneral) {
                table.setBasicModel(false);
                createGeneralMapper(table, mapperUrl, daoName);
            } else {
                table.setBasicModel(true);
                createBasiclMapper(table, mapperUrl, daoName);
            }
            // 创建DAO
            // createDAO(c, splitSlash);
            log.info("创建成功，文件位置：");
            log.info(mapperUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
