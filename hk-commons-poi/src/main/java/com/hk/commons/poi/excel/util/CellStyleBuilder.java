package com.hk.commons.poi.excel.util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import com.hk.commons.poi.excel.style.CustomCellStyle;
import com.hk.commons.poi.excel.style.StyleSheet;

/**
 * 样式创建工具类
 * 
 * @author kevin
 * @date 2017年9月14日下午3:34:37
 */
public abstract class CellStyleBuilder {

	/**
	 * 创建单元格样式
	 * 
	 * @param workbook workbook
	 * @param style style
	 * @param dataFormat dataFormat
	 * @return CellStyle
	 */
	public static CellStyle buildCellStyle(Workbook workbook, CustomCellStyle style,
			com.hk.commons.poi.excel.model.DataFormat dataFormat) {
		CellStyle cellStyle = workbook.createCellStyle();
		if (null != style) {
			if (null != style.getHorizontalAlignment()) {
				cellStyle.setAlignment(style.getHorizontalAlignment());
			}
			if (null != style.getVerticalAlignment()) {
				cellStyle.setVerticalAlignment(style.getVerticalAlignment());
			}
			if (null != style.getBorderBottom()) {
				cellStyle.setBorderBottom(style.getBorderBottom());
			}
			if (style.getBorderBottomColor() != StyleSheet.NONE_STYLE) {
				cellStyle.setBottomBorderColor(style.getBorderBottomColor());
			}
			if (null != style.getBorderLeft()) {
				cellStyle.setBorderLeft(style.getBorderLeft());
			}
			if (style.getBorderLeftColor() != StyleSheet.NONE_STYLE) {
				cellStyle.setLeftBorderColor(style.getBorderLeftColor());
			}
			if (null != style.getBorderRight()) {
				cellStyle.setBorderRight(style.getBorderRight());
			}
			if (style.getBorderRightColor() != StyleSheet.NONE_STYLE) {
				cellStyle.setRightBorderColor(style.getBorderRightColor());
			}
			if (null != style.getBorderTop()) {
				cellStyle.setBorderTop(style.getBorderTop());
			}
			if (style.getBorderTopColor() != StyleSheet.NONE_STYLE) {
				cellStyle.setTopBorderColor(style.getBorderTopColor());
			}
			if (style.getBackgroundColor() != StyleSheet.NONE_STYLE) {
				cellStyle.setFillBackgroundColor(style.getBackgroundColor());
				cellStyle.setFillPattern(style.getFillPatternType());
			}
			if (style.getFillForegroundColor() != StyleSheet.NONE_STYLE) {
				cellStyle.setFillForegroundColor(style.getFillForegroundColor());
				cellStyle.setFillPattern(style.getFillPatternType());
			}
			cellStyle.setWrapText(style.isAutoWrap());
			cellStyle.setLocked(style.isLocked());
			cellStyle.setFont(createFont(workbook, style));
		}
		DataFormat format = workbook.getCreationHelper().createDataFormat();
		cellStyle.setDataFormat(format.getFormat(dataFormat.getPattern()));
		return cellStyle;
	}

	/**
	 * 创建单元格字体，并设置样式
	 * 
	 * @param workbook workbook
	 * @param style style
	 * @return Font
	 */
	private static Font createFont(Workbook workbook, CustomCellStyle style) {
		Font font = workbook.createFont();
		font.setBold(style.isBold());
		font.setItalic(style.isItalic());
		font.setStrikeout(style.isStrikeout());
		font.setUnderline(style.getUnderline().getValue());
		if (style.getFontColor() != StyleSheet.NONE_STYLE) {
			font.setColor(style.getFontColor());
		}
		if (style.getFontSize() != StyleSheet.NONE_STYLE) {
			font.setFontHeightInPoints(style.getFontSize());
		}
		font.setFontName(style.getFontName().getFontName());
		return font;
	}
}
