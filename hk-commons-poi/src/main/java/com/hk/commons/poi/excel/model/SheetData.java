package com.hk.commons.poi.excel.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 工作表数据
 *
 * @author kevin
 * @date 2018年1月10日下午4:05:03
 */
@Data
public final class SheetData<T> {

    /**
     * 工作表索引，从 0 开始
     */
    private int sheetIndex;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 工作表解析的数据
     */
    private List<T> data = new ArrayList<>();

    /**
     * 解析时出现错误的日志
     */
    private List<ErrorLog<T>> errorLogs = new ArrayList<>();

    public SheetData() {

    }

    /**
     * @param sheetIndex sheetIndex
     * @param sheetName  sheetName
     */
    public SheetData(int sheetIndex, String sheetName) {
        this.sheetIndex = sheetIndex;
        this.sheetName = sheetName;
    }

    /**
     * 添加数据
     *
     * @param t t
     */
    public void add(T t) {
        if (Objects.nonNull(t)) {
            data.add(t);
        }
    }

    /**
     * 添加错误日志
     *
     * @param errorLog errorLog
     */
    public void addErrorLog(ErrorLog<T> errorLog) {
        if (Objects.nonNull(errorLog)) {
            errorLogs.add(errorLog);
        }
    }

}
