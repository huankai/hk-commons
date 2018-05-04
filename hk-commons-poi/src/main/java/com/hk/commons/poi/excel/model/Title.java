/**
 * 
 */
package com.hk.commons.poi.excel.model;

import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellAddress;

/**
 * Excel标题行
 * 
 * @author kally
 * @date 2018年1月10日下午3:45:32
 */
@Data
public class Title {

	/**
	 * 单元格所在行
	 */
	private int row;

	/**
	 * 标题所在列
	 */
	private int column;

	/**
	 * 标题列所在的索引名称
	 */
	private String cellReference;

	/**
	 * 对应的属性名称
	 */
	private final String propertyName;

	/**
	 * 标题内容
	 */
	private String value;

	/**
	 * 
	 * @param cell
	 *            单元格
	 * @param propertyName
	 *            Bean 属性名
	 */
	public Title(Cell cell, String propertyName) {
		CellAddress address = cell.getAddress();
		this.column = address.getColumn();
		this.row = address.getRow();
		this.cellReference = address.formatAsString();
		this.propertyName = propertyName;
		this.value = cell.getStringCellValue();
	}

	/**
	 * 
	 * @param row
	 *            单元格所在行
	 * @param column
	 *            单元格所在列
	 * @param value
	 *            单元格值
	 * @param propertyName
	 *            Bean 属性名
	 */
	public Title(int row, int column, String value, String propertyName) {
		this.row = row;
		this.column = column;
		this.cellReference = new CellAddress(row, column).formatAsString();
		this.propertyName = propertyName;
		this.value = value;
	}

}
