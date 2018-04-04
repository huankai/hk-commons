/**
 * 
 */
package com.hk.commons.poi.excel.model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellAddress;

/**
 * Excel标题行
 * 
 * @author kally
 * @date 2018年1月10日下午3:45:32
 */
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
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param column
	 *            the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @return the cellReference
	 */
	public String getCellReference() {
		return cellReference;
	}

	/**
	 * @param cellReference
	 *            the cellReference to set
	 */
	public void setCellReference(String cellReference) {
		this.cellReference = cellReference;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
