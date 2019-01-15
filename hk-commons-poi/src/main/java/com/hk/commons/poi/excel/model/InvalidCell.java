package com.hk.commons.poi.excel.model;

import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellAddress;

import java.io.Serializable;

/**
 * 验证出错的单元格信息
 *
 * @author kevin
 * @date 2018年1月10日下午3:44:48
 */
@Data
@SuppressWarnings("serial")
public class InvalidCell implements Serializable {

    /**
     * 单元格所在行
     */
    private int row;

    /**
     * 单元格所在列索引,从 0 开始
     */
    private int column;

    /**
     * 单元格地址，如 A1、B1
     */
    private String cellReference;

    /**
     * 列对应的标题
     */
    private Title title;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 错误值
     */
    private Object value;

    /**
     * 构造方法
     *
     * @param cell    单元格
     * @param title   单元格所对应的标题
     * @param message 错误信息描述
     */
    public InvalidCell(Cell cell, Title title, String message) {
        this.title = title;
        this.message = message;
        CellAddress address = cell.getAddress();
        this.column = address.getColumn();
        this.row = address.getRow();
        this.cellReference = address.formatAsString();
        this.value = cell.getStringCellValue();
    }

    /**
     * 构造方法
     *
     * @param row     单元格所在行
     * @param column  单元格所在列
     * @param value   单元格
     * @param title   单元格所对应的标题
     * @param message 错误信息描述
     */
    public InvalidCell(int row, int column, Object value, Title title, String message) {
        this.row = row;
        this.column = column;
        CellAddress address = new CellAddress(row, column);
        this.cellReference = address.formatAsString();
        this.message = message;
        this.value = value;
        this.title = title;
    }

}
