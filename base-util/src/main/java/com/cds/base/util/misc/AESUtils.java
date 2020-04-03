package com.cds.base.util.misc;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description AES加密原子服务工具类
 * @Notes 未填写备注
 * @author liming
 * @Date 2016年9月6日 下午4:34:08
 * @version 1.0
 * @since JDK 1.8
 */
public class AESUtils {

    /** 运算法则 **/
    private static final String ALGORITHM   = "AES";

    /** 算法和填充模式 **/
    private static final String AES_CBC     = "AES/CBC/PKCS5Padding";

    /** 密钥 **/
    private static final String AES_KEY     = "chinashanghaijky";

    /** 向量 **/
    private static final String ivParameter = "chinashanghaijky";

    public AESUtils(){
    }

    /**
     * 哈希值转换（未考虑线程安全问题）
     * 
     * @author shihua.jiang
     * @param bts[] 字符数组
     * @return String 转换后16进制的字符串
     */
    private static String bytes2Hex1(byte bts[]) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = Integer.toHexString(bts[i] & 255);
            if (tmp.length() == 1) des = (new StringBuilder(String.valueOf(des))).append("0").toString();
            des = (new StringBuilder(String.valueOf(des))).append(tmp).toString();
        }

        return des;
    }

    /**
     * 数据加密方法
     * 
     * @author shihua.jiang
     * @param strIn 需要加密的字符串
     * @return String 加密后的字符串
     * @throws Exception Exception
     */
    public static String encrypt(String strIn) throws Exception {

        // 根据字节数组构造一个 SecretKey
        SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(), ALGORITHM);

        // AES解密、解密核心类
        Cipher cipher = Cipher.getInstance(AES_CBC);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(ivParameter.getBytes()));

        byte[] encrypted = cipher.doFinal(strIn.getBytes());

        return bytes2Hex1(encrypted);
    }

}
