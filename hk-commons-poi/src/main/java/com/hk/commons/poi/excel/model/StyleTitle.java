package com.hk.commons.poi.excel.model;

import com.hk.commons.poi.excel.style.CustomCellStyle;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author kevin
 */
@SuppressWarnings("serial")
public class StyleTitle extends Title {

    private static final int WIDTH_UNITS = 256;

    /**
     * 列宽
     */
    @Setter
    private int columnWidth;

    /**
     * 样式
     */
    @Getter
    @Setter
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
        return columnWidth * WIDTH_UNITS;
    }

}
