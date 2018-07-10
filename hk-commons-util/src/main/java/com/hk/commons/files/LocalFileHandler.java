package com.hk.commons.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.KeyValue;
import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.hk.commons.util.ArrayUtils;
import com.hk.commons.util.AssertUtils;
import com.hk.commons.util.CollectionUtils;
import com.hk.commons.util.IDGenerator;
import com.hk.commons.util.StringUtils;
import com.hk.commons.util.date.DatePattern;
import com.hk.commons.util.date.DateTimeUtils;

/**
 * 本地文件上传保存
 * 
 * @author: kevin
 * @date 2017年8月18日下午2:47:14
 */
public class LocalFileHandler implements FileHandler {
	
	/**
	 * 本地文件
	 */
	private final String basePath;

	public LocalFileHandler(String basePath) {
		AssertUtils.notNull(basePath, "保存的文件路径必须不能为空");
		if (!StringUtils.endsWith(basePath, "/")) {
			basePath = basePath + "/";
		}
		this.basePath = basePath;
	}

	@Override
	public String[] upload(List<KeyValue<String, InputStream>> inputStreams) throws IOException {
		List<String> paths = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(inputStreams)) {
			for (KeyValue<String, InputStream> keyValue : inputStreams) {
				File file = new File(basePath + getDatePath(keyValue.getKey()));
				FileUtils.copyInputStreamToFile(keyValue.getValue(), file);
				paths.add(file.getName());
			}
		}
		return paths.toArray(new String[] {});
	}

	@Override
	public String upload(String fileName, InputStream inputStream) throws IOException {
		String datePath = getDatePath(fileName);
		File file = new File(basePath + datePath);
		FileUtils.copyInputStreamToFile(inputStream, file);
		return datePath;
	}

	protected String getDatePath(String fileName) {
		String generate = IDGenerator.STRING_UUID.generate();
		String dateDir = DateTimeUtils.dateToString(new Date(), DatePattern.YYYY_MM_DD_EN);
		return String.format("%s/%s.%s", dateDir, generate, Files.getFileExtension(fileName));
	}

	@Override
	public String[] upload(File... files) throws IOException {
		List<String> paths = Lists.newArrayList();
		if (ArrayUtils.isNotEmpty(files)) {
			for (File file : files) {
				File newFile = new File(basePath + getDatePath(file.getName()));
				Files.copy(file, newFile);
				paths.add(newFile.getName());
			}
		}
		return paths.toArray(new String[] {});
	}

	@Override
	public void delete(String filePath) {
		String path = String.format("%s/%s", getBasePath(), filePath);
		File file = new File(path);
		try {
			FileUtils.forceDelete(file);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public byte[] getFileData(String filePath) {
		String path = String.format("%s/%s", getBasePath(), filePath);
		File file = new File(path);
		if (!file.exists()) {
			throw new RuntimeException("文件不存在 ：" + filePath);
		}
		try {
			return Files.toByteArray(file);
		} catch (IOException e) {
			throw new RuntimeException("不能读取文件：" + filePath);
		}
	}

	public String getBasePath() {
		return basePath;
	}

}
