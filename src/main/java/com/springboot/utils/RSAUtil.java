package com.springboot.utils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * RSA加解密工具<br>
 */
public class RSAUtil {
    public static String RSA_ALGORITHM = "RSA";
    public static String UTF8 = "UTF-8";

    /**
     * 创建公钥私钥
     * @return
     * @throws Exception
     */
    public static KeyStore createKeys() throws Exception {
        KeyPairGenerator keyPairGeno = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGeno.initialize(1024);
        KeyPair keyPair = keyPairGeno.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        KeyStore keyStore = new KeyStore();
        keyStore.setPublicKey(Base64.encodeBase64String(publicKey.getEncoded()));
        keyStore.setPrivateKey(Base64.encodeBase64String(privateKey.getEncoded()));
        return keyStore;
    }

    /**
     * 获取公钥对象
     *
     * @param pubKeyData
     * @return
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(byte[] pubKeyData) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubKeyData);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * 获取公钥对象
     *
     * @param pubKey
     *            公钥
     * @return
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String pubKey) throws Exception {
        return getPublicKey(Base64.decodeBase64(pubKey));

    }

    /**
     * 获取私钥对象
     *
     * @param priKey
     *            私钥
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String priKey) throws Exception {
        return getPrivateKey(Base64.decodeBase64(priKey));
    }

    /**
     * 通过私钥byte[]将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(byte[] keyBytes) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

    }

    public static String encryptByPublicKey(String data, String publicKey) throws Exception {
        return encryptByPublicKey(data, getPublicKey(publicKey));
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(data.getBytes(UTF8));
        return Base64.encodeBase64String(bytes);
    }

    public static String decryptByPublicKey(String data, String rsaPublicKey) throws Exception {
        return decryptByPublicKey(data, getPublicKey(rsaPublicKey));
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param rsaPublicKey
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data, RSAPublicKey rsaPublicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
        byte[] inputData = Base64.decodeBase64(data);
        byte[] bytes = cipher.doFinal(inputData);
        return new String(bytes, UTF8);
    }

    public static String encryptByPrivateKey(String data, String privateKey) throws Exception {
        return encryptByPrivateKey(data, getPrivateKey(privateKey));
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(data.getBytes(UTF8));
        return Base64.encodeBase64String(bytes);
    }

    public static String decryptByPrivateKey(String data, String privateKey) throws Exception {
        return decryptByPrivateKey(data, getPrivateKey(privateKey));
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] inputData = Base64.decodeBase64(data);
        byte[] bytes = cipher.doFinal(inputData);
        return new String(bytes, UTF8);
    }


    public static class KeyStore {
        private String publicKey;
        private String privateKey;

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
    }

    private static String getRSAPrivateKeyAsNetFormat(byte[] encodedPrivkey) {
        try {
            StringBuffer buff = new StringBuffer(1024);

            PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(
                    encodedPrivkey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateCrtKey pvkKey = (RSAPrivateCrtKey) keyFactory
                    .generatePrivate(pvkKeySpec);

            buff.append("<RSAKeyValue>");
            buff.append("<Modulus>"
                    + b64encode(removeMSZero(pvkKey.getModulus()
                    .toByteArray())) + "</Modulus>");

            buff.append("<Exponent>"
                    + b64encode(removeMSZero(pvkKey.getPublicExponent()
                    .toByteArray())) + "</Exponent>");

            buff.append("<P>"
                    + b64encode(removeMSZero(pvkKey.getPrimeP().toByteArray()))
                    + "</P>");

            buff.append("<Q>"
                    + b64encode(removeMSZero(pvkKey.getPrimeQ().toByteArray()))
                    + "</Q>");

            buff.append("<DP>"
                    + b64encode(removeMSZero(pvkKey.getPrimeExponentP()
                    .toByteArray())) + "</DP>");

            buff.append("<DQ>"
                    + b64encode(removeMSZero(pvkKey.getPrimeExponentQ()
                    .toByteArray())) + "</DQ>");

            buff.append("<InverseQ>"
                    + b64encode(removeMSZero(pvkKey.getCrtCoefficient()
                    .toByteArray())) + "</InverseQ>");

            buff.append("<D>"
                    + b64encode(removeMSZero(pvkKey.getPrivateExponent()
                    .toByteArray())) + "</D>");
            buff.append("</RSAKeyValue>");

            return buff.toString().replaceAll("[ \t\n\r]", "");
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    private static String getRSAPublicKeyAsNetFormat(byte[] encodedPrivkey) {
        try {
            StringBuffer buff = new StringBuffer(1024);

            PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(
                    encodedPrivkey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey pukKey = (RSAPublicKey) keyFactory
                    .generatePublic(new X509EncodedKeySpec(encodedPrivkey));

            buff.append("<RSAKeyValue>");
            buff.append("<Modulus>"
                    + b64encode(removeMSZero(pukKey.getModulus()
                    .toByteArray())) + "</Modulus>");
            buff.append("<Exponent>"
                    + b64encode(removeMSZero(pukKey.getPublicExponent()
                    .toByteArray())) + "</Exponent>");
            buff.append("</RSAKeyValue>");
            return buff.toString().replaceAll("[ \t\n\r]", "");
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    public static String encodePublicKeyToXml(PublicKey key) {
        if (!RSAPublicKey.class.isInstance(key)) {
            return null;
        }
        RSAPublicKey pubKey = (RSAPublicKey) key;
        StringBuilder sb = new StringBuilder();

        sb.append("<RSAKeyValue>");
        sb.append("<Modulus>").append(
                Base64.encodeBase64(pubKey.getModulus().toByteArray())).append(
                "</Modulus>");
        sb.append("<Exponent>").append(
                Base64.encodeBase64(pubKey.getPublicExponent().toByteArray()))
                .append("</Exponent>");
        sb.append("</RSAKeyValue>");
        return sb.toString();
    }

    private static byte[] removeMSZero(byte[] data) {
        byte[] data1;
        int len = data.length;
        if (data[0] == 0) {
            data1 = new byte[data.length - 1];
            System.arraycopy(data, 1, data1, 0, len - 1);
        } else{
            data1 = data;
        }

        return data1;
    }

    private static String b64encode(byte[] data) {
        String b64str = new String(Base64.decodeBase64(data));
        return b64str;
    }

    private static byte[] b64decode(String data) {
        byte[] decodeData = Base64.decodeBase64(data);
        return decodeData;
    }
}