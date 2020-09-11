/**
 * @Project base-util
 * @Package com.cds.base.util.generator.mybatis.common
 * @Class MybatisOfficialGeneratorBridge.java
 * @Date Sep 2, 2020 4:50:58 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.generator.mybatis.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.cds.base.generator.mybatis.config.DBConnectionConfig;
import com.cds.base.generator.mybatis.config.DbType;
import com.cds.base.generator.mybatis.config.GeneratorConfig;
import com.cds.base.generator.mybatis.plugin.DbRemarksCommentGenerator;
import com.cds.base.generator.mybatis.util.ConfigHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date Sep 2, 2020 4:50:58 PM
 */
@Slf4j
public class MybatisOfficialGeneratorAdapter {

    public void generate(GeneratorConfig generatorConfig, DBConnectionConfig dbConnectionConfig) throws Exception {
        Configuration configuration = new Configuration();
        Context context = new Context(ModelType.CONDITIONAL);
        context.addProperty("javaFileEncoding", "UTF-8");
        String dbType = dbConnectionConfig.getDbType();
        String connectorLibPath = ConfigHelper.findConnectorLibPath(dbType);
        log.info("数据库驱动位置: {}", connectorLibPath);
        context.setId("officialGenerator");
        context.setTargetRuntime("MyBatis3");
        context.addProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING, generatorConfig.getEncoding());
        String projectPath =
            generatorConfig.getProjectName() + "-dep/" + generatorConfig.getProjectName() + "-dep-dal/";

        // 设置类型转换
        setJavaTypeResolverConfig(context);
        // 设置表信息
        setTableConfig(context, generatorConfig, dbConnectionConfig);
        // 设置jdbc配置
        setJdbcConfig(context, dbConnectionConfig);
        // 设置model配置
        setModelConfig(context, generatorConfig, projectPath);
        // 设置mapper配置
        setMapperConfig(context, generatorConfig, projectPath);
        // 设置DAO配置
        setDaoConfig(context, generatorConfig, projectPath);
        // 设置注释配置
        setCommentConfig(context, generatorConfig);
        // 设置序列化配置
        setSerialConfig(context);
        // Lombok 插件
        setLombokConfig(context);
        // mysql配置
        setMySqlPageConfig(generatorConfig, context, dbType);
        setGeneratorConfig(context);
        configuration.addClasspathEntry(connectorLibPath);
        configuration.addContext(context);
        List<String> warnings = new ArrayList<>();
        Set<String> fullyqualifiedTables = new HashSet<>();
        Set<String> contexts = new HashSet<>();
        ShellCallback shellCallback = new DefaultShellCallback(true); // override=true
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, shellCallback, warnings);
        // if overrideXML selected, delete oldXML ang generate new one
        if (generatorConfig.isOverrideXML()) {
            String mappingXMLFilePath = getMappingXMLFilePath(generatorConfig);
            File mappingXMLFile = new File(mappingXMLFilePath);
            if (mappingXMLFile.exists()) {
                mappingXMLFile.delete();
            }
        }
        myBatisGenerator.generate(null, contexts, fullyqualifiedTables);
    }

    private void setJavaTypeResolverConfig(Context context) {
        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration
            .setConfigurationType("com.cds.base.generator.mybatis.config.CustomJavaTypeResolver");
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);
    }

    private void setTableConfig(Context context, GeneratorConfig generatorConfig,
        DBConnectionConfig dbConnectionConfig) {
        String dbType = dbConnectionConfig.getDbType();

        TableConfiguration tableConfig = new TableConfiguration(context);
        tableConfig.setTableName(generatorConfig.getTableName());
        tableConfig.setDomainObjectName(generatorConfig.getDomainObjectName());
        tableConfig.setUpdateByExampleStatementEnabled(true);
        tableConfig.setCountByExampleStatementEnabled(true);
        tableConfig.setDeleteByExampleStatementEnabled(true);
        tableConfig.setSelectByExampleStatementEnabled(true);
        tableConfig.setInsertStatementEnabled(true);
        tableConfig.setSelectByPrimaryKeyStatementEnabled(false);
        tableConfig.setUpdateByPrimaryKeyStatementEnabled(false);
        tableConfig.setDeleteByPrimaryKeyStatementEnabled(false);

        context.addProperty("autoDelimitKeywords", "true");
        if (DbType.MySQL.name().equals(dbType) || DbType.MySQL_8.name().equals(dbType)) {
            tableConfig.setSchema(dbConnectionConfig.getSchema());
            // 由于beginningDelimiter和endingDelimiter的默认值为双引号(")，在Mysql中不能这么写，所以还要将这两个默认值改为`
            context.addProperty("beginningDelimiter", "`");
            context.addProperty("endingDelimiter", "`");
        } else {
            tableConfig.setCatalog(dbConnectionConfig.getSchema());
        }
        if (generatorConfig.isUseSchemaPrefix()) {
            if (DbType.MySQL.name().equals(dbType) || DbType.MySQL_8.name().equals(dbType)) {
                tableConfig.setSchema(dbConnectionConfig.getSchema());
            } else if (DbType.Oracle.name().equals(dbType)) {
                // Oracle的schema为用户名，如果连接用户拥有dba等高级权限，若不设schema，会导致把其他用户下同名的表也生成一遍导致mapper中代码重复
                tableConfig.setSchema(dbConnectionConfig.getUserName());
            } else {
                tableConfig.setCatalog(dbConnectionConfig.getSchema());
            }
        }
        // 针对 postgresql 单独配置
        if (DbType.PostgreSQL.name().equals(dbType)) {
            tableConfig.setDelimitIdentifiers(true);
        }

        if (generatorConfig.getMapperName() != null) {
            tableConfig.setMapperName(generatorConfig.getMapperName());
        }
        if (generatorConfig.isUseActualColumnNames()) {
            tableConfig.addProperty("useActualColumnNames", "true");
        }

        if (generatorConfig.isUseTableNameAlias()) {
            tableConfig.setAlias(generatorConfig.getTableName());
        }
        context.addTableConfiguration(tableConfig);

    }

    private void setJdbcConfig(Context context, DBConnectionConfig dbConnectionConfig) throws Exception {
        String dbType = dbConnectionConfig.getDbType();

        JDBCConnectionConfiguration jdbcConfig = new JDBCConnectionConfiguration();
        if (DbType.MySQL.name().equals(dbType) || DbType.MySQL_8.name().equals(dbType)) {
            jdbcConfig.addProperty("nullCatalogMeansCurrent", "true");
            // useInformationSchema可以拿到表注释，从而生成类注释可以使用表的注释
            jdbcConfig.addProperty("useInformationSchema", "true");
        }
        jdbcConfig.setDriverClass(DbType.valueOf(dbType).getDriverClass());
        jdbcConfig.setConnectionURL(ConfigHelper.getConnectionUrlWithSchema(dbConnectionConfig));
        jdbcConfig.setUserId(dbConnectionConfig.getUserName());
        jdbcConfig.setPassword(dbConnectionConfig.getPasswd());
        if (DbType.Oracle.name().equals(dbType)) {
            jdbcConfig.getProperties().setProperty("remarksReporting", "true");
        }
        context.setJdbcConnectionConfiguration(jdbcConfig);

    }

    private void setModelConfig(Context context, GeneratorConfig generatorConfig, String projectPath) {
        // java model
        JavaModelGeneratorConfiguration modelConfig = new JavaModelGeneratorConfiguration();
        modelConfig.setTargetPackage(generatorConfig.getModelPackage());
        modelConfig.setTargetProject(
            generatorConfig.getProjectFolder() + "/" + projectPath + generatorConfig.getModelPackageTargetFolder());
        context.setJavaModelGeneratorConfiguration(modelConfig);

    }

    private void setMapperConfig(Context context, GeneratorConfig generatorConfig, String projectPath) {
        // Mapper configuration
        SqlMapGeneratorConfiguration mapperConfig = new SqlMapGeneratorConfiguration();
        mapperConfig.setTargetPackage(generatorConfig.getMappingXMLPackage());
        mapperConfig.setTargetProject(
            generatorConfig.getProjectFolder() + "/" + projectPath + generatorConfig.getMappingXMLTargetFolder());
        context.setSqlMapGeneratorConfiguration(mapperConfig);
    }

    private void setDaoConfig(Context context, GeneratorConfig generatorConfig, String projectPath) {
        // DAO
        JavaClientGeneratorConfiguration daoConfig = new JavaClientGeneratorConfiguration();
        daoConfig.setConfigurationType("XMLMAPPER");
        daoConfig.setTargetPackage(generatorConfig.getDaoPackage());
        daoConfig.setTargetProject(
            generatorConfig.getProjectFolder() + "/" + projectPath + generatorConfig.getDaoTargetFolder());

        context.setJavaClientGeneratorConfiguration(daoConfig);
    }

    private void setCommentConfig(Context context, GeneratorConfig generatorConfig) {
        // Comment
        CommentGeneratorConfiguration commentConfig = new CommentGeneratorConfiguration();
        commentConfig.setConfigurationType(DbRemarksCommentGenerator.class.getName());
        if (generatorConfig.isComment()) {
            commentConfig.addProperty("columnRemarks", "true");
        }
        if (generatorConfig.isAnnotation()) {
            commentConfig.addProperty("annotations", "true");
        }
        context.setCommentGeneratorConfiguration(commentConfig);

    }

    /**
     * @description TODO 填写描述信息
     * @return void
     */
    private void setGeneratorConfig(Context context) {
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.addProperty("type", "com.cds.base.generator.mybatis.plugin.CustomMyBatisGenertaorPlugin");
        pluginConfiguration.setConfigurationType("com.cds.base.generator.mybatis.plugin.CustomMyBatisGenertaorPlugin");
        context.addPluginConfiguration(pluginConfiguration);
    }

    /**
     * @description TODO 填写描述信息
     * @return void
     */
    private void setMySqlPageConfig(GeneratorConfig generatorConfig, Context context, String dbType) {
        if (DbType.MySQL.name().equals(dbType) || DbType.MySQL_8.name().equals(dbType)
            || DbType.PostgreSQL.name().equals(dbType)) {
            PluginConfiguration examplePluginConfiguration = new PluginConfiguration();
            examplePluginConfiguration.addProperty("useExample", String.valueOf(generatorConfig.isUseExample()));
            examplePluginConfiguration.addProperty("type",
                "com.cds.base.generator.mybatis.plugin.CommonDAOInterfacePlugin");
            examplePluginConfiguration
                .setConfigurationType("com.cds.base.generator.mybatis.plugin.CommonDAOInterfacePlugin");
            context.addPluginConfiguration(examplePluginConfiguration);
            PluginConfiguration pagePluginConfiguration2 = new PluginConfiguration();
            pagePluginConfiguration2.addProperty("type", "com.cds.base.generator.mybatis.plugin.MySQLLimitPlugin");
            pagePluginConfiguration2.setConfigurationType("com.cds.base.generator.mybatis.plugin.MySQLLimitPlugin");
            context.addPluginConfiguration(pagePluginConfiguration2);
        }
    }

    /**
     * @description TODO 填写描述信息
     * @return void
     */
    private void setLombokConfig(Context context) {
        PluginConfiguration pc = new PluginConfiguration();
        pc.addProperty("type", "com.cds.base.generator.mybatis.plugin.LombokPlugin");
        pc.setConfigurationType("com.cds.base.generator.mybatis.plugin.LombokPlugin");
        context.addPluginConfiguration(pc);
    }

    /**
     * @description TODO 填写描述信息
     * @return void
     */
    private void setSerialConfig(Context context) {
        // 实体添加序列化
        PluginConfiguration serializablePluginConfiguration = new PluginConfiguration();
        serializablePluginConfiguration.addProperty("type", "org.mybatis.generator.plugins.SerializablePlugin");
        serializablePluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
        context.addPluginConfiguration(serializablePluginConfiguration);
    }

    private String getMappingXMLFilePath(GeneratorConfig generatorConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append(generatorConfig.getProjectFolder()).append("/");
        sb.append(generatorConfig.getMappingXMLTargetFolder()).append("/");
        String mappingXMLPackage = generatorConfig.getMappingXMLPackage();
        if (StringUtils.isNotEmpty(mappingXMLPackage)) {
            sb.append(mappingXMLPackage.replace(".", "/")).append("/");
        }
        if (StringUtils.isNotEmpty(generatorConfig.getMapperName())) {
            sb.append(generatorConfig.getMapperName()).append(".xml");
        } else {
            sb.append(generatorConfig.getDomainObjectName()).append("Mapper.xml");
        }

        return sb.toString();
    }

}
