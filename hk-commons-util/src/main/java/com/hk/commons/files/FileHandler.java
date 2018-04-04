package com.hk.commons.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.collections4.KeyValue;

/**
 * 文件上传处理
 * 
 * @author huangkai
 * @date 2017年8月18日下午1:04:27
 */
public interface FileHandler {

	/**
	 * 上传文件
	 * 
	 * @param inputStreams
	 * @return 文件路径
	 */
	String[] upload(List<KeyValue<String, InputStream>> inputStreams) throws IOException;

	/**
	 * 文件上传
	 * 
	 * @param fileName
	 *            文件名
	 * @param inputStream
	 *            文件流
	 * @return 文件路径
	 * @throws IOException
	 */
	String upload(String fileName, InputStream inputStream) throws IOException;

	/**
	 * 上传文件
	 * 
	 * @param files
	 * @throws IOException
	 * @return 文件路径
	 */
	String[] upload(File... files) throws IOException;

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 */
	void delete(String filePath);

	/**
	 * 文件下载
	 * 
	 * @param filePath
	 */
	byte[] getFileData(String filePath);

}
