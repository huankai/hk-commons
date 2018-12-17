package com.hk.commons.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * @author kevin
 * @date 2017年11月18日下午5:38:23
 */
public abstract class Base64Utils extends org.springframework.util.Base64Utils {

	/**
	 * 将文件 Base64 转码为字符串
	 *
	 * @param file
	 *            需要转码的文件
	 * @return 转码后的字符串
	 */
	public static String encodeToString(File file) {
		try {
			return encodeToString(FileUtils.readFileToByteArray(file));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param is
	 *            将流 Base64 转码为字符串
	 * @return 转码后的字符串
	 */
	public static String encodeToString(InputStream is) {
		try {
			return encodeToString(IOUtils.toByteArray(is));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
