/*
 * Copyright 2013-2015 duolabao.com All Right Reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.cds.base.util.misc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description MD5工具类
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年9月6日 下午4:30:26
 * @version 1.0
 * @since JDK 1.8
 */
public class MD5Utils {

    public static MessageDigest digest;

    public synchronized static final String md5(String data) {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException nsae) {
                System.err.println("Failed to load the MD5 MessageDigest. " + "Jive will be unable to function normally.");
                nsae.printStackTrace();
            }
        }
        // Now, compute hash.
        digest.update(data.getBytes());
        // 填充
        return encodeHex(digest.digest());

    }

    /**
     * Turns an array of bytes into a String representing each byte as an unsigned hex number.
     * <p>
     * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
     * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
     * Distributed under LGPL.
     * 
     * @param bytes an array of bytes to convert to a hex-string
     * @return generated hex string
     */
    public static final String encodeHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        int i;
        for (i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 0x10) {
                // 如果bytes[i]补码的低8位小于 16 buf添加0
                buf.append("0");
            }
            // bytes[i] 的低8位，换算成16进制数，添加到buf
            buf.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

}
