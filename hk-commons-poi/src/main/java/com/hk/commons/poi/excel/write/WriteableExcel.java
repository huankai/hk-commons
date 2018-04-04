/**
 * 
 */
package com.hk.commons.poi.excel.write;

import java.io.OutputStream;

import com.hk.commons.poi.excel.model.WriteParam;

/**
 * @author huangkai
 *
 */
public interface WriteableExcel<T> {

	/**
	 * 
	 * @param param
	 *            写出的参数
	 * @param out
	 */
	void write(WriteParam<T> param, OutputStream out);

}
