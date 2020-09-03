/**
 * @Project base-generator
 * @Package com.cds.base.generator.mybatis
 * @Class MyBatisOfficialMapperGenerator.java
 * @Date Sep 3, 2020 3:20:16 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.generator.mybatis;

import java.util.List;

import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;

import com.cds.base.generator.mybatis.common.MybatisOfficialGeneratorAdapter;
import com.cds.base.generator.mybatis.config.DBConnectionConfig;
import com.cds.base.generator.mybatis.config.GeneratorConfig;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date Sep 3, 2020 3:20:16 PM
 */
public class MyBatisOfficialMapperGenerator {
    private DBConnectionConfig dbConnectionConfig;
    private List<IgnoredColumn> ignoredColumns;
    private List<ColumnOverride> columnOverrides;

    public void generateCode(GeneratorConfig generatorConfig) {

        MybatisOfficialGeneratorAdapter adapter = new MybatisOfficialGeneratorAdapter();
        adapter.setGeneratorConfig(generatorConfig);
        adapter.setDatabaseConfig(dbConnectionConfig);
        adapter.setIgnoredColumns(ignoredColumns);
        adapter.setColumnOverrides(columnOverrides);
        try {
            adapter.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
