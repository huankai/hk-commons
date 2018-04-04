/**
 * 
 */
package com.hk.commons.poi.excel.read;

import java.io.File;
import java.io.InputStream;

import com.hk.commons.poi.excel.model.ReadResult;

/**
 * @author kally
 * @date 2018年1月10日下午3:41:13
 */
public interface ReadableExcel<T> {

	/**
	 * 从文件中读取
	 * 
	 * @param param
	 *            解析文件的参数
	 * @param file
	 *            Excel文件
	 * @return
	 */
	ReadResult<T> read(File file);

	/**
	 * 从文件流中读取
	 * 
	 * @param param
	 *            解析文件的参数
	 * @param in
	 *            Excel文件流
	 * @return
	 */
	ReadResult<T> read(InputStream in);

}
