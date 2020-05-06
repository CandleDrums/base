/**
 * @Project base-common
 * @Package com.cds.base.common.system
 * @Class PlatformType.java
 * @Date Apr 30, 2020 10:53:04 AM
 * @Copyright (c) 2020 CandleDrums.com All Right Reserved.
 */
package com.cds.base.common.system;

/**
 * @Description 平台类型
 * @Notes 未填写备注
 * @author liming
 * @Date Apr 30, 2020 10:53:04 AM
 */
public enum PlatformType {
    Any("any"), Linux("Linux"), Mac_OS("Mac OS"), Mac_OS_X("Mac OS X"), Windows("Windows"), OS2("OS/2"),
    Solaris("Solaris"), SunOS("SunOS"), MPEiX("MPE/iX"), HP_UX("HP-UX"), AIX("AIX"), OS390("OS/390"),
    FreeBSD("FreeBSD"), Irix("Irix"), Digital_Unix("Digital Unix"), NetWare_411("NetWare"), OSF1("OSF1"),
    OpenVMS("OpenVMS"), Others("Others");

    private String description;

    private PlatformType(String desc) {
        this.description = desc;
    }

    @Override
    public String toString() {
        return description;
    }

}
