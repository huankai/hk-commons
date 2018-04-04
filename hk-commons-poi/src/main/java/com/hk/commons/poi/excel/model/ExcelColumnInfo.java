package com.hk.commons.poi.excel.model;

import com.hk.commons.poi.excel.style.CustomCellStyle;

/**
 * 导出Excel每一列的属性参数
 * 
 * @author huangkai
 * 
 * @date 2017年9月15日下午3:21:51
 */
public class ExcelColumnInfo {

	/**
	 * 标题
	 */
	private StyleTitle title;

	/**
	 * 是否统计
	 */
	private boolean isStatistics;

	/**
	 * 如果有注解，注解是否可见，默认隐藏，需要鼠标移动到指定的区域才可见
	 */
	private boolean commentVisible;

	/**
	 * 如果有注解，注解作者名称
	 */
	private String commentAuthor;

	private CustomCellStyle dataStyle;

	/**
	 * @return the title
	 */
	public StyleTitle getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(StyleTitle title) {
		this.title = title;
	}

	/**
	 * @return the isStatistics
	 */
	public boolean isStatistics() {
		return isStatistics;
	}

	/**
	 * @param isStatistics
	 *            the isStatistics to set
	 */
	public void setStatistics(boolean isStatistics) {
		this.isStatistics = isStatistics;
	}

	/**
	 * @return the commentVisible
	 */
	public boolean isCommentVisible() {
		return commentVisible;
	}

	/**
	 * @param commentVisible
	 *            the commentVisible to set
	 */
	public void setCommentVisible(boolean commentVisible) {
		this.commentVisible = commentVisible;
	}

	/**
	 * @return the commentAuthor
	 */
	public String getCommentAuthor() {
		return commentAuthor;
	}

	/**
	 * @param commentAuthor
	 *            the commentAuthor to set
	 */
	public void setCommentAuthor(String commentAuthor) {
		this.commentAuthor = commentAuthor;
	}

	/**
	 * @return the dataStyle
	 */
	public CustomCellStyle getDataStyle() {
		return dataStyle;
	}

	/**
	 * @param dataStyle
	 *            the dataStyle to set
	 */
	public void setDataStyle(CustomCellStyle dataStyle) {
		this.dataStyle = dataStyle;
	}
	
	public int getSortColumn() {
		return title.getColumn();
	}

}
