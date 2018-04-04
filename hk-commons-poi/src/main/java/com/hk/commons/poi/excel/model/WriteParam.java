/**
 * 
 */
package com.hk.commons.poi.excel.model;

import java.util.List;

/**
 * 
 * @author huangkai
 *
 */
public class WriteParam<T> {

	/**
	 * 
	 */
	protected static final String DEFAULT_SHEET_NAME = "sheet1";

	/**
	 * 标题行
	 */
	protected static final int DEFAULT_TITLE_ROW = 0;

	/**
	 * 数据开始行
	 */
	protected static final int DEFAULT_DATA_START_ROW = 1;

	/**
	 * 工作表名
	 */
	private String sheetName = DEFAULT_SHEET_NAME;

	/**
	 * 导出的数据
	 */
	private List<T> data;

	/**
	 * 转换的 Beanclass
	 * 
	 */
	private Class<T> beanClazz;

	/**
	 * 标题行，默认为第0行
	 */
	private int titleRow = DEFAULT_TITLE_ROW;

	/**
	 * 数据开始行，默认为第一行开始
	 */
	private int dataStartRow = DEFAULT_DATA_START_ROW;

	/**
	 * 标题行高
	 */
	private float titleRowHeight = 30;

	/**
	 * 数据行高
	 */
	private float dataRowHeight = 25;

	/**
	 * 单元格值格式化
	 */
	private ValueFormat valueFormat = new ValueFormat();

	/**
	 * 显示网格
	 */
	private boolean displayGridLines = true;

	/**
	 * 文件密码
	 */
	private String password;

	/**
	 * <pre>
	 * 当有NestedProperty注解(一对多)时，一的一方是否合并单元格
	 * </pre>
	 * 
	 */
	private boolean mergeCell = true;

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
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * @return the beanClazz
	 */
	public Class<T> getBeanClazz() {
		return beanClazz;
	}

	/**
	 * @param beanClazz
	 *            the beanClazz to set
	 */
	public void setBeanClazz(Class<T> beanClazz) {
		this.beanClazz = beanClazz;
	}

	/**
	 * @return the titleRow
	 */
	public int getTitleRow() {
		return titleRow;
	}

	/**
	 * @param titleRow
	 *            the titleRow to set
	 */
	public void setTitleRow(int titleRow) {
		this.titleRow = titleRow;
	}

	/**
	 * @return the dataStartRow
	 */
	public int getDataStartRow() {
		return dataStartRow;
	}

	/**
	 * @param dataStartRow
	 *            the dataStartRow to set
	 */
	public void setDataStartRow(int dataStartRow) {
		this.dataStartRow = dataStartRow;
	}

	/**
	 * @return the displayGridLines
	 */
	public boolean isDisplayGridLines() {
		return displayGridLines;
	}

	/**
	 * @param displayGridLines
	 *            the displayGridLines to set
	 */
	public void setDisplayGridLines(boolean displayGridLines) {
		this.displayGridLines = displayGridLines;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the titleRowHeight
	 */
	public float getTitleRowHeight() {
		return titleRowHeight;
	}

	/**
	 * @param titleRowHeight
	 *            the titleRowHeight to set
	 */
	public void setTitleRowHeight(float titleRowHeight) {
		this.titleRowHeight = titleRowHeight;
	}

	/**
	 * @return the dataRowHeight
	 */
	public float getDataRowHeight() {
		return dataRowHeight;
	}

	/**
	 * @param dataRowHeight
	 *            the dataRowHeight to set
	 */
	public void setDataRowHeight(float dataRowHeight) {
		this.dataRowHeight = dataRowHeight;
	}

	/**
	 * @return the valueFormat
	 */
	public ValueFormat getValueFormat() {
		return valueFormat;
	}

	/**
	 * @return the mergeCell
	 */
	public boolean isMergeCell() {
		return mergeCell;
	}

	/**
	 * @param mergeCell
	 *            the mergeCell to set
	 */
	public void setMergeCell(boolean mergeCell) {
		this.mergeCell = mergeCell;
	}

	public void putFormat(String propertyName, DataFormat format) {
		getValueFormat().put(propertyName, format);
	}

	public void putFormat(Class<?> clazz, DataFormat format) {
		getValueFormat().put(clazz, format);
	}

}
