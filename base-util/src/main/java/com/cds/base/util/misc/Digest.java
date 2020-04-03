/*
 * Copyright 2013-2015 duolabao.com All Right Reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.cds.base.util.misc;

import java.security.MessageDigest;

/**
 * 类Digest.java的实现描述：计算摘要的工具类
 * 
 * @author libing 2015年9月11日 下午5:23:13
 */
public class Digest {

    /**
     * 使用MD5算法计算摘要，并对结果进行hex转换
     * 
     * @param data 源数据
     * @return 摘要信息
     */
    public static String md5Digest(String str) {
        try {
            byte[] data = str.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            MessageDigest md = MessageDigest.getInstance(ConfigureEncryptAndDecrypt.MD5_ALGORITHM);
            return Hex.toHex(md.digest(data));
        } catch (Exception e) {
            throw new RuntimeException("digest fail!", e);
        }
    }

    /**
     * 使用SHA-0算法计算摘要，并对结果进行hex转换
     * 
     * @param str 源数据
     * @return 摘要信息
     */
    public static String shaDigest(String str) {
        try {
            byte[] data = str.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            MessageDigest md = MessageDigest.getInstance(ConfigureEncryptAndDecrypt.SHA_ALGORITHM);
            return Hex.toHex(md.digest(data));
        } catch (Exception e) {
            throw new RuntimeException("digest fail!", e);
        }
    }

    /**
     * 根据指定算法计算摘要
     * 
     * @param str 源数据
     * @param alg 摘要算法
     * @param charencoding 源数据获取字节的编码方式
     * @return 摘要信息
     */
    public static String digest(String str, String alg, String charencoding) {
        try {
            byte[] data = str.getBytes(charencoding);
            MessageDigest md = MessageDigest.getInstance(alg);
            return Hex.toHex(md.digest(data));
        } catch (Exception e) {
            throw new RuntimeException("digest fail!", e);
        }
    }

}
