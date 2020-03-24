package com.springboot.utils;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * AES加密工具类
 * @author mrliz
 */
public class AesUtil {

	public static String randomKey = RandomStringUtils.randomAlphanumeric(16);
	public static final String KEY = "KWR8qVEVyxpNTV0R";
	public static String NewKey = null;
	public static Map<Integer, String> clientKey = new HashMap<Integer, String>();
	/**
	 * @param data 要加密的数据
	 * @param key 加密的密码，必须是16位。否则会报错。
	 * @return 加密后的数据
	 */
	public static byte[] aesEncrypt(byte[] data, String key) throws Exception {
		if (data == null || key == null) { return null; }

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
		return cipher.doFinal(data);

	}

	/**
	 *
	 * @param data 要解密的数据
	 * @param key 输入密码
	 * @return 解密后的数据
	 */
	public static byte[] aesDecrypt(byte[] data, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
		return cipher.doFinal(data);
	}
}