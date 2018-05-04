/**
 * 
 */
package com.hk.commons.poi.excel.model;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 解析Excel的错误日志信息
 * 
 * @author kally
 * @date 2018年1月10日下午3:42:58
 */
@Data
@AllArgsConstructor
public class ErrorLog<T> {

	/**
	 * 工作表名称
	 */
	private String sheetName;

	/**
	 * 记录所在行
	 */
	private int row;

	/**
	 * 解析的数据
	 */
	private T data;

	/**
	 * 错误信息
	 */
	private List<InvalidCell> invalidCells;

	/**
	 * @param sheetName
	 *            工作表名称
	 * @param row
	 *            记录所在行
	 * @param data
	 *            解析的数据
	 * @param invalidCell
	 *            错误信息
	 */
	public ErrorLog(String sheetName, int row, T data, InvalidCell invalidCell) {
		this(sheetName, row, data, Lists.newArrayList(invalidCell));
	}

}
