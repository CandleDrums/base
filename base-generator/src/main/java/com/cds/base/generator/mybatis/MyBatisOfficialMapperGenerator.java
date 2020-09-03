/**
 * @Project base-generator
 * @Package com.cds.base.generator.mybatis
 * @Class MyBatisOfficialMapperGenerator.java
 * @Date Sep 3, 2020 3:20:16 PM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.generator.mybatis;

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
    // private List<IgnoredColumn> ignoredColumns;
    // private List<ColumnOverride> columnOverrides;

    public void generateCode(GeneratorConfig generatorConfig, DBConnectionConfig dbConnectionConfig) {

        MybatisOfficialGeneratorAdapter adapter = new MybatisOfficialGeneratorAdapter();
        // adapter.setIgnoredColumns(ignoredColumns);
        // adapter.setColumnOverrides(columnOverrides);
        try {
            adapter.generate(generatorConfig, dbConnectionConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
