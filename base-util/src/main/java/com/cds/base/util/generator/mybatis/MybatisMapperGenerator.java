/**
 * @Project base-server-dal
 * @Package com.cds.base.server.dal.util
 * @Class MybatisMapperGenerator.java
 * @Date 2017年11月20日 下午3:45:48
 * @Copyright (c) 2019 CandleDrumS.com All Right Reserved
 */
package com.cds.base.util.generator.mybatis;

import java.io.File;

import com.cds.base.util.generator.mybatis.annotaion.Table;
import com.cds.base.util.generator.mybatis.common.MapperUtils;

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
        boolean isGeneral = MapperUtils.isGeneralModel(c);

        try {

            // 获取当前目录
            Table table = MapperUtils.getTable(c);
            String daoName = MapperUtils.getDAOName(table);
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

}
