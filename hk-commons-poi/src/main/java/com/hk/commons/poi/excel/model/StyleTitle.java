/**
 * 
 */
package com.hk.commons.poi.excel.model;

import org.apache.poi.ss.usermodel.Cell;

import com.hk.commons.poi.excel.style.CustomCellStyle;

/**
 * @author huangkai
 *
 */
public class StyleTitle extends Title {


	/**
	 * 列宽
	 */
	private int columnWidth;

	/**
	 * 样式
	 */
	private CustomCellStyle style;

	public StyleTitle(Cell cell, String propertyName) {
		super(cell, propertyName);
	}
	
	public StyleTitle(int row, int column, String value, String propertyName) {
		super(row, column, value, propertyName);
	}
	
	/**
	 * @return the columnWidth
	 */
	public int getColumnWidth() {
		return columnWidth;
	}

	/**
	 * @param columnWidth
	 *            the columnWidth to set
	 */
	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth * 256;
	}

	/**
	 * @return the style
	 */
	public CustomCellStyle getStyle() {
		return style;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(CustomCellStyle style) {
		this.style = style;
	}

}
