/*
 * Copyright 2013-2015 duolabao.com All Right Reserved. This software is the confidential and proprietary information of
 * duolabao.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with duolabao.com.
 */
package com.cds.base.util.misc;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RSA {

    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 初始化生成密钥
     * 
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(512);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 取得私钥
     * 
     * @param keyMap
     * @return base64格式
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key)keyMap.get(PRIVATE_KEY);
        return new String(Base64.encode(key.getEncoded()), ConfigureEncryptAndDecrypt.CHAR_ENCODING);
    }

    /**
     * 取得公钥
     * 
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key)keyMap.get(PUBLIC_KEY);
        return new String(Base64.encode(key.getEncoded()), ConfigureEncryptAndDecrypt.CHAR_ENCODING);
    }

    /**
     * 验证签名
     * 
     * @param data
     *            数据
     * @param sign
     *            签名
     * @param pubicKey
     *            公钥
     * @return
     */
    public static boolean verifySign(byte[] data, byte[] sign, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance(ConfigureEncryptAndDecrypt.SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(data);
            boolean result = signature.verify(sign);
            return result;
        } catch (Exception e) {

            throw new RuntimeException("verifySign fail!", e);
        }
    }

    /**
     * 验证签名
     * 
     * @param data
     *            数据
     * @param sign
     *            签名
     * @param pubicKey
     *            公钥
     * @return
     */
    public static boolean verifySign(String data, String sign, PublicKey pubicKey) {
        try {
            byte[] dataByte = data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            byte[] signByte = Base64.decode(sign.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
            return verifySign(dataByte, signByte, pubicKey);
        } catch (UnsupportedEncodingException e) {

            throw new RuntimeException("verifySign fail!", e);
        }
    }

    /**
     * 验证签名
     * 
     * @param data
     *            数据
     * @param sign
     *            签名
     * @param publicKey
     *            base64格式的公钥
     * @return
     */
    public static boolean verifySign(String data, String sign, String publicKey) {
        try {
            byte[] dataByte = data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            byte[] signByte = Base64.decode(sign.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
            return verifySign(dataByte, signByte, base64KeyTOPublicKey(publicKey));
        } catch (UnsupportedEncodingException e) {

            throw new RuntimeException("verifySign fail!", e);
        }
    }

    /**
     * 签名
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] sign(byte[] data, PrivateKey key) {
        try {
            Signature signature = Signature.getInstance(ConfigureEncryptAndDecrypt.SIGNATURE_ALGORITHM);
            signature.initSign(key);
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException("sign fail!", e);
        }
    }

    /**
     * 签名
     * 
     * @param data
     * @param key
     * @return
     */
    public static String sign(String data, PrivateKey key) {
        try {
            byte[] dataByte = data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            return new String(Base64.encode(sign(dataByte, key)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("sign fail!", e);
        }
    }

    /**
     * 签名
     * 
     * @param data
     * @param privateKey
     *            base64格式的密钥
     * @return
     */
    public static String sign(String data, String privateKey) {
        try {
            byte[] dataByte = data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            return new String(Base64.encode(sign(dataByte, base64KeyTOPrivateKey(privateKey))));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("sign fail!", e);
        }
    }

    /**
     * 加密 <br>
     * RSA加密明文最大长度117字节，在解密的过程中需要分块进行。
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] data, Key key) {
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int inputLen = data.length;

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 加密
     * 
     * @param data
     * @param key
     * @return
     */
    public static String encrypt(String data, Key key) {
        try {
            return new String(Base64.encode(encrypt(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), key)));
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 使用公钥加密
     * 
     * @param data
     * @param publicKey
     * @return
     */
    public static String encryptByPublicKey(String data, String publicKey) {
        try {
            return new String(
                Base64.encode(
                    encrypt(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), base64KeyTOPublicKey(publicKey))),
                ConfigureEncryptAndDecrypt.CHAR_ENCODING);
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 使用私钥加密
     * 
     * @param data
     * @param publicKey
     * @return
     */
    public static String encryptByPrivateKey(String data, String privateKey) {
        try {
            return new String(Base64.encode(
                encrypt(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), base64KeyTOPrivateKey(privateKey))),
                ConfigureEncryptAndDecrypt.CHAR_ENCODING);
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 解密 <br>
     * RSA解密要求密文最大长度为128字节，所以在解密的过程中需要分块进行。
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    /**
     * 解密
     * 
     * @param data
     * @param key
     * @return
     */
    public static String decrypt(String data, Key key) {
        try {
            return new String(decrypt(Base64.decode(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING)), key),
                ConfigureEncryptAndDecrypt.CHAR_ENCODING);
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    /**
     * 使用私钥解密
     * 
     * @param data
     * @param privateKey
     * @return
     */
    public static String decryptByPrivateKey(String data, String privateKey) {
        try {
            return new String(decrypt(Base64.decode(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING)),
                base64KeyTOPrivateKey(privateKey)), ConfigureEncryptAndDecrypt.CHAR_ENCODING);
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    /**
     * 使用公钥解密
     * 
     * @param data
     * @param publicKey
     * @return
     */
    public static String decryptByPublicKey(String data, String publicKey) {
        try {
            return new String(decrypt(Base64.decode(data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING)),
                base64KeyTOPublicKey(publicKey)), ConfigureEncryptAndDecrypt.CHAR_ENCODING);
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    /**
     * @param key
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static PublicKey base64KeyTOPublicKey(String key) {
        try {
            X509EncodedKeySpec keySpec =
                new X509EncodedKeySpec(Base64.decodeBase64(key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING)));
            KeyFactory keyFactory = KeyFactory.getInstance(ConfigureEncryptAndDecrypt.KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            throw new RuntimeException("string to public key fail!", e);
        }
    }

    /**
     * 得到私钥
     * 
     * @param key
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static PrivateKey base64KeyTOPrivateKey(String key) {
        try {
            PKCS8EncodedKeySpec keySpec =
                new PKCS8EncodedKeySpec(Base64.decodeBase64(key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING)));
            KeyFactory keyFactory = KeyFactory.getInstance(ConfigureEncryptAndDecrypt.KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            throw new RuntimeException("string to private key fail!", e);
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> keyMap = initKey();
        String privateKey = getPrivateKey(keyMap);
        String publicKey = getPublicKey(keyMap);
        log.info("--------------------------");
        log.info("private key :" + privateKey);
        log.info("public key :" + publicKey);
        String source = "试试中文";
        log.info("source : " + source);
        log.info("--------------------------\n");

        log.info("----------私钥加密，公钥解密start--------");
        String encryptByPrivateKey = encryptByPrivateKey(source, privateKey);
        log.info("private key encrypt:" + encryptByPrivateKey);
        log.info("public key decrypt:" + decryptByPublicKey(encryptByPrivateKey, publicKey));
        log.info("----------私钥加密，公钥解密end----------\n");

        log.info("----------公钥加密，私钥解密start--------");
        String encryptByPublicKey = encryptByPublicKey(source, publicKey);
        log.info("public key encrypt:" + encryptByPublicKey);
        log.info("private key decrypt:" + decryptByPrivateKey(encryptByPublicKey, privateKey));
        log.info("----------公钥加密，私钥解密end--------\n");

        log.info("----------私钥签名,公钥验签start--------");
        String signResult = sign(source, privateKey);
        log.info("sign result:" + signResult);
        log.info("verify sign result:" + verifySign(source, signResult, publicKey));
        log.info("----------私钥签名,公钥验签end--------");
    }
}
