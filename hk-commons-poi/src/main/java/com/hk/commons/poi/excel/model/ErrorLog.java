package com.hk.commons.poi.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 解析Excel的错误日志信息
 *
 * @author kevin
 * @date 2018年1月10日下午3:42:58
 */
@Data
@AllArgsConstructor
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

}
