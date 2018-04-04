/**
 * 
 */
package com.hk.commons.poi.excel.model;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 解析Excel的错误日志信息
 * 
 * @author kally
 * @date 2018年1月10日下午3:42:58
 */
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

	/**
	 * @param sheetName
	 *            工作表名称
	 * @param row
	 *            记录所在行
	 * @param data
	 *            解析的数据
	 * @param invalidCells
	 *            错误信息
	 */
	public ErrorLog(String sheetName, int row, T data, List<InvalidCell> invalidCells) {
		this.sheetName = sheetName;
		this.row = row;
		this.data = data;
		this.invalidCells = invalidCells;
	}

	/**
	 * @return the sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * @param sheetName
	 *            the sheetName to set
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the invalidCells
	 */
	public List<InvalidCell> getInvalidCells() {
		return invalidCells;
	}

	/**
	 * @param invalidCells
	 *            the invalidCells to set
	 */
	public void setInvalidCells(List<InvalidCell> invalidCells) {
		this.invalidCells = invalidCells;
	}

}
