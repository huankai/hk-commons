package com.hk.commons.poi.excel.util;

import org.apache.poi.ss.usermodel.BorderStyle;

import com.hk.commons.poi.excel.annotations.CellStyle;
import com.hk.commons.poi.excel.style.CustomCellStyle;
import com.hk.commons.poi.excel.style.StyleSheet;

/**
 * 样式工具类
 *
 * @author kevin
 */
public abstract class StyleCellUtils {

    /**
     * 解析注解样式
     *
     * @param cellStyle cellStyle
     * @return {@link CustomCellStyle}
     */
    public static CustomCellStyle toCustomCellStyle(CellStyle cellStyle) {
        CustomCellStyle columnStyle = new CustomCellStyle();
        columnStyle.setBackgroundColor(cellStyle.backgroundColor());
        columnStyle.setFillForegroundColor(cellStyle.fillForegroundColor());
        columnStyle.setFillPatternType(cellStyle.fillPatternType());
        columnStyle.setVerticalAlignment(cellStyle.verticalAlignment());
        columnStyle.setHorizontalAlignment(cellStyle.horizontalAlignment());
        columnStyle.setBold(cellStyle.bold());

        columnStyle.setBorder(cellStyle.border());
        columnStyle.setBorderColor(cellStyle.borderColor());

        if (cellStyle.borderTop() != BorderStyle.NONE) {
            columnStyle.setBorderTop(cellStyle.borderTop());
        }
        if (cellStyle.borderTopColor() != StyleSheet.NONE_STYLE) {
            columnStyle.setBorderTopColor(cellStyle.borderTopColor());
        }
        if (cellStyle.borderBottom() != BorderStyle.NONE) {
            columnStyle.setBorderBottom(cellStyle.borderBottom());
        }
        if (cellStyle.borderBottomColor() != StyleSheet.NONE_STYLE) {
            columnStyle.setBorderBottomColor(cellStyle.borderBottomColor());
        }

        if (cellStyle.borderLeft() != BorderStyle.NONE) {
            columnStyle.setBorderLeft(cellStyle.borderLeft());
        }
        if (cellStyle.borderLeftColor() != StyleSheet.NONE_STYLE) {
            columnStyle.setBorderLeftColor(cellStyle.borderLeftColor());
        }
        if (cellStyle.borderRight() != BorderStyle.NONE) {
            columnStyle.setBorderRight(cellStyle.borderRight());
        }
        if (cellStyle.borderRightColor() != StyleSheet.NONE_STYLE) {
            columnStyle.setBorderRightColor(cellStyle.borderRightColor());
        }

        columnStyle.setAutoWrap(cellStyle.autoWrap());
        columnStyle.setFontName(cellStyle.fontName());
        columnStyle.setFontSize(cellStyle.fontSize());
        columnStyle.setFontColor(cellStyle.fontColor());
        columnStyle.setItalic(cellStyle.italic());
        columnStyle.setBold(cellStyle.bold());
        columnStyle.setStrikeout(cellStyle.strikeout());
        columnStyle.setUnderline(cellStyle.underline());
        columnStyle.setLocked(cellStyle.locked());
        return columnStyle;
    }

}
