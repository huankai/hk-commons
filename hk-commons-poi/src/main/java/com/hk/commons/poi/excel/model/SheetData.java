/**
 *
 */
package com.hk.commons.poi.excel.model;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

/**
 * 工作表数据
 *
 * @author kally
 * @date 2018年1月10日下午4:05:03
 */
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
    private List<T> data = Lists.newArrayList();

    /**
     * 解析时出现错误的日志
     */
    private List<ErrorLog<T>> errorLogs = Lists.newArrayList();

    public SheetData() {

    }

    /**
     * @param sheetIndex
     * @param sheetName
     */
    public SheetData(int sheetIndex, String sheetName) {
        this.sheetIndex = sheetIndex;
        this.sheetName = sheetName;
    }

    /**
     * @return the sheetIndex
     */
    public int getSheetIndex() {
        return sheetIndex;
    }

    /**
     * @param sheetIndex the sheetIndex to set
     */
    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * @param sheetName the sheetName to set
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
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * @return the errorLogs
     */
    public List<ErrorLog<T>> getErrorLogs() {
        return errorLogs;
    }

    /**
     * @param errorLogs the errorLogs to set
     */
    public void setErrorLogs(List<ErrorLog<T>> errorLogs) {
        this.errorLogs = errorLogs;
    }

    /**
     * 添加数据
     *
     * @param t
     */
    public void add(T t) {
        if (Objects.nonNull(t)) {
            data.add(t);
        }
    }

    /**
     * 添加错误日志
     *
     * @param errorLog
     */
    public void addErrorLog(ErrorLog<T> errorLog) {
        if (Objects.nonNull(errorLog)) {
            errorLogs.add(errorLog);
        }
    }

}
