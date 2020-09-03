/**
 * @Project base-server-dal
 * @Package com.cds.base.server.dal.util
 * @Class MybatisMapperGenerator.java
 * @Date 2017年11月20日 下午3:45:48
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved.
 */
package com.cds.base.generator.mybatis;

import java.io.File;

import com.cds.base.generator.mybatis.annotaion.Table;
import com.cds.base.generator.mybatis.common.MapperUtils;
import com.cds.base.util.system.OSInfoUtils;

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
        String splitSlash = OSInfoUtils.getSplitSlash();

        boolean isGeneral = MapperUtils.isGeneralModel(c);
        // 获取当前目录
        Table table = MapperUtils.getTable(c);

        createBaseDAOFile(c, table, isGeneral, splitSlash);
        createDAOFile(c, table, splitSlash);
    }

    /**
     * @description 创建基础DAO文件
     * @return void
     */
    private static void createBaseDAOFile(Class c, Table table, boolean isGeneral, String splitSlash) {
        String daoName = MapperUtils.getDAOName(table);
        String baseDaoName = MapperUtils.getBaseDAOName(table);
        String mapperPath = System.getProperty("user.dir") + splitSlash + "src" + splitSlash + "main" + splitSlash
            + "resources" + splitSlash + "mapper" + splitSlash + "base" + splitSlash;
        // daoName:com.cds.example.dep.dal.dao.TableNameDAO
        // 截取TableNameDAO
        // eg:example-dep/example-dep-dal/src/main/resources/mapper/base/TableNameBaseDAO.xml
        String mapperUrl = mapperPath + baseDaoName.substring(baseDaoName.lastIndexOf(".") + 1) + ".xml";
        try {

            File folder = new File(mapperPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            if (isGeneral) {
                table.setBasicModel(false);
                MapperUtils.createGeneralMapper(table, mapperUrl, daoName);
            } else {
                table.setBasicModel(true);
                MapperUtils.createBasiclMapper(table, mapperUrl, daoName);
            }
            // 创建DAO
            // createDAO(c, splitSlash);
            log.info("创建成功，文件位置：");
            log.info(mapperUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 创建DAO文件
     * @return void
     */
    private static void createDAOFile(Class c, Table table, String splitSlash) {
        String daoName = MapperUtils.getDAOName(table);
        int index = daoName.lastIndexOf(".");
        String mapperPath = System.getProperty("user.dir") + splitSlash + "src" + splitSlash + "main" + splitSlash
            + "resources" + splitSlash + "mapper" + splitSlash;
        String mapperUrl = mapperPath + daoName.substring(index + 1) + ".xml";
        try {

            File folder = new File(mapperUrl);
            if (folder.exists()) {
                log.info("文件已存在，自动跳过");

                return;
            }
            MapperUtils.createExtMapper(table, mapperUrl, daoName);
            // 创建DAO
            // createDAO(c, splitSlash);
            log.info("创建成功，文件位置：");
            log.info(mapperUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
