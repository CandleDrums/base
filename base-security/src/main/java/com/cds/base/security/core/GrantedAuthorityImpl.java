/**
 * @Project base-security
 * @Package com.cds.base.security.core
 * @Class GrantedAuthorityImpl.java
 * @Date 2021年2月20日 上午10:09:02
 * @Copyright (c) 2021 CandleDrums.com All Right Reserved.
 */
package com.cds.base.security.core;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Description TODO 填写描述信息
 * @Notes 未填写备注
 * @author liming
 * @Date 2021年2月20日 上午10:09:02
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
