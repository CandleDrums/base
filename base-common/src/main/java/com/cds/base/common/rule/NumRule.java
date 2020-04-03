/**
 * @Project base-common
 * @Package com.cds.base.common.rule
 * @Class NumRule.java
 * @Date 2019年9月16日 下午6:07:49
 * @Copyright (c) 2019 YOUWE All Right Reserved.
 */
package com.cds.base.common.rule;

import com.cds.base.common.system.AppType;

/**
 * @Description 编号规则
 * @Notes 枚举名称为表名，一般直接采用DO的命名，eg：AccountDO-->ACCOUNT
 * @author maoshou
 * @Date 2019年9月16日 下午6:07:49
 * @version 1.0
 * @since JDK 1.8
 */
public enum NumRule {

    // 账户
    Account("1001", 7, "account", AppType.ACCOUNT_CENTER),
    // 子账户
    SubAccount("1002", 7, "sub_account", AppType.ACCOUNT_CENTER),
    // 余额
    Balance("1003", 7, "balance", AppType.ACCOUNT_CENTER),
    // 余额记录
    BalanceHistory("1004", 7, "balance_history", AppType.ACCOUNT_CENTER),
    // 余额变更
    BalanceChange("1005", 7, "balance_change", AppType.ACCOUNT_CENTER),
    // 红包规则
    RedpacketRule("1006", 7, "redpacket_rule", AppType.ACCOUNT_CENTER),
    // 友商
    Friendship("2001", 7, "friend_ship", AppType.FRIENDSHIP_CENTER),
    // 开拓者-礼金记录
    Pioneer("3001", 7, "pioneer", AppType.PIONEER_CENTER),
    // 开拓者-礼金记录
    PioneerTeam("3002", 7, "pioneer_team", AppType.PIONEER_CENTER),
    // 开拓者-提现记录
    DrawHistory("3003", 7, "pioneer_team", AppType.PIONEER_CENTER),
    // 开拓者-活动
    PioneerActivity("3004", 7, "pioneer_activity", AppType.PIONEER_CENTER),
    // 外设中心--制造商
    Manufacturer("4001", 7, "manufacturer", AppType.PERIPHERAL_CENTER),
    // 外设中心--设备分类
    DeviceModel("4002", 7, "manufacturer", AppType.PERIPHERAL_CENTER),
    // 外设中心--设备
    Device("4003", 7, "manufacturer", AppType.PERIPHERAL_CENTER),
    // 外设中心--打印模板
    PrintTemplate("4004", 7, "manufacturer", AppType.PERIPHERAL_CENTER);

    // 前缀
    private String prefixCode;
    // 编号拼接规则<see>NumSplicingRule</see>
    // 1=TIMESTAMP;
    // 2=RANDOM;
    // 3=TIMESTAMP+RANDOM;
    // 4=REDIS_INCR;
    // 5=TIMESTAMP+REDIS_INCR;
    // 6=RANDOM+REDIS_INCR;
    // 7=TIMESTAMP+RANDOM+REDIS_INCR
    private int numSplicingRule;
    // 说明
    private String tableName;
    // 所属应用
    private AppType app;

    NumRule(String prefixCode, int numSplicingRule, String tableName, AppType app) {
        this.prefixCode = prefixCode;
        this.numSplicingRule = numSplicingRule;
        this.tableName = tableName;
        this.app = app;
    }

    /**
     * @description 获取规则
     * @return NumRule
     */
    public static NumRule getRule(String name) {
        if (name == null || name.length() == 0) {
            return null;
        }
        return NumRule.valueOf(name);
    }

    public String getPrefixCode() {
        return prefixCode;
    }

    public int getNumSplicingRule() {
        return numSplicingRule;
    }

    public String getTableName() {
        return tableName;
    }

    public AppType getApp() {
        return app;
    }
}