/**
 * @Project base-generator
 * @Package com.cds.base.generator.mybatis.config
 * @Class DBConnectionConfig.java
 * @Date Sep 3, 2020 10:53:35 AM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.generator.mybatis.config;

import lombok.Data;

/**
 * @Description 数据库连接配置信息
 * @Notes 未填写备注
 * @author liming
 * @Date Sep 3, 2020 10:53:35 AM
 */
@Data
public class DBConnectionConfig {
    private Integer id;
    private String name;
    private String host;
    private String userName;
    private String passwd;
    private String port;
    private String dbType;

    private String schema;
    private String encoding;
    private String lport;
    private String rport;
    private String sshPort;
    private String sshHost;
    private String sshUser;
    private String sshPassword;
    private String privateKeyPassword;
    private String privateKey;
}
