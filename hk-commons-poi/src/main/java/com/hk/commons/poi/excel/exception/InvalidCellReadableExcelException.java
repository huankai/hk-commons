package com.hk.commons.poi.excel.exception;

import com.hk.commons.poi.excel.model.InvalidCell;

import java.util.List;

/**
 * 解析数据时出现的异常，记录每个单元格的错误信息
 *
 * @author huangkai
 * @date 2017年9月11日下午5:42:52
 */
@SuppressWarnings("serial")
public class InvalidCellReadableExcelException extends ExcelReadException {

    /**
     * 失效的单元格数据
     */
    private final List<InvalidCell> invalidCells;

    /**
     * 转换的对象对象
     */
    private final Object target;

    public InvalidCellReadableExcelException(String message, Object target, List<InvalidCell> invalidCells) {
        super(message);
        this.target = target;
        this.invalidCells = invalidCells;
    }

    public List<InvalidCell> getInvalidCells() {
        return invalidCells;
    }


    public Object getTarget() {
        return target;
    }

}
