/**
 * 
 */
package com.hk.commons.poi.excel.read.handler;

import org.apache.poi.ss.usermodel.Sheet;

import com.hk.commons.poi.excel.model.SheetData;

/**
 * Dom 解析
 * 
 * @author kally
 * @date 2018年1月10日下午5:55:31
 */
public interface DomReadHandler<T> extends ReadableHandler<T> {

	/**
	 * 解析工作表
	 * 
	 * @param sheet
	 *            工作表
	 * @param sheetIndex
	 *            工作表所在工作薄的索引
	 * @return
	 */

	SheetData<T> processSheet(Sheet sheet, int sheetIndex);

}
