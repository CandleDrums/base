/**
 * @Project base-generator
 * @Package com.cds.base.generator.mybatis.common
 * @Class CDSAbstractXmlElementGenerator.java
 * @Date Sep 3, 2020 5:51:56 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.generator.mybatis.common;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date Sep 3, 2020 5:51:56 PM
 */
public class CustomAbstractXmlElementGenerator extends AbstractXmlElementGenerator {

    @Override
    public void addElements(XmlElement parentElement) {
        // 增加base_query
        XmlElement sql = new XmlElement("sql");
        sql.addAttribute(new Attribute("id", "Base_Object_Query"));
        // 在这里添加where条件
        XmlElement selectTrimElement = new XmlElement("trim"); // 设置trim标签
        selectTrimElement.addAttribute(new Attribute("prefix", "WHERE"));
        selectTrimElement.addAttribute(new Attribute("prefixOverrides", "AND | OR")); // 添加where和and
        StringBuffer sb = new StringBuffer();
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            XmlElement selectNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null");
            selectNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            sb.setLength(0);
            // 添加and
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            // 添加等号
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            selectNotNullElement.addElement(new TextElement(sb.toString()));
            selectTrimElement.addElement(selectNotNullElement);
        }
        sql.addElement(selectTrimElement);
        parentElement.addElement(sql);

        // 公用select
        sb.setLength(0);
        sb.append("select * from ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        TextElement selectText = new TextElement(sb.toString());

        // 公用include
        XmlElement include = new XmlElement("include");
        include.addAttribute(new Attribute("refid", "Base_Object_Query"));

        // 增加pageList
        XmlElement pageList = new XmlElement("select");
        pageList.addAttribute(new Attribute("id", "pageList"));
        pageList.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        pageList.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        pageList.addElement(selectText);
        pageList.addElement(include);
        parentElement.addElement(pageList);

        // 公用select
        sb.setLength(0);
        sb.append("select count(*) from ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        selectText = new TextElement(sb.toString());

        XmlElement pageListCount = new XmlElement("select");
        pageListCount.addAttribute(new Attribute("id", "pageListCount"));
        pageListCount.addAttribute(new Attribute("resultType", "int"));
        pageListCount.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        pageListCount.addElement(selectText);
        pageListCount.addElement(include);
        parentElement.addElement(pageList);
    }

}