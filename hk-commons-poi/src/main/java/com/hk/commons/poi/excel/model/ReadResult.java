/**
 *
 */
package com.hk.commons.poi.excel.model;

import com.google.common.collect.Lists;
import com.hk.commons.util.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 解析Excel 结果
 *
 * @author kally
 * @date 2018年1月10日下午3:41:43
 */
@SuppressWarnings("serial")
public class ReadResult<T> implements Serializable {

    /**
     * 总错误信息
     */
    private List<ErrorLog<T>> errorLogList = Lists.newArrayList();

    /**
     * 解析的每个工作表数据
     */
    private List<SheetData<T>> sheetDataList = Lists.newArrayList();

    /**
     * 解析的标题行信息
     */
    private List<Title> titleList;

    /**
     * 添加工作表数据
     *
     * @param data
     */
    public void addSheetData(SheetData<T> data) {
        sheetDataList.add(data);
    }

    public void setSheetDataList(List<SheetData<T>> sheetDataList) {
        this.sheetDataList = sheetDataList;
    }

    /**
     * 添加错误日志信息
     *
     * @param errorLogList
     */
    public void addErrorLogList(List<ErrorLog<T>> errorLogList) {
        this.errorLogList.addAll(errorLogList);
    }

    /**
     * 添加错误日志信息
     *
     * @param errorLog
     */
    public void addErrorLog(ErrorLog<T> errorLog) {
        this.errorLogList.add(errorLog);
    }

    /**
     * 返回所有工作表名
     *
     * @return
     */
    public Set<String> getSheetNameSet() {
        return sheetDataList.stream().map(item -> item.getSheetName()).collect(Collectors.toSet());
    }

    /**
     * 返回所有工作表数据
     *
     * @return
     */
    public List<T> getAllSheetData() {
        List<T> result = Lists.newArrayList();
        sheetDataList.forEach(item -> result.addAll(item.getData()));
        return result;
    }

    /**
     * 返回指定工作表的数据
     *
     * @param sheetIndex 工作表索引
     * @return
     */
    public List<T> getSheetDataBySheetIndex(int sheetIndex) {
        Optional<SheetData<T>> optional = sheetDataList.stream().filter(item -> item.getSheetIndex() == sheetIndex)
                .findFirst();
        return optional.isPresent() ? optional.get().getData() : Collections.emptyList();
    }

    /**
     * 返回指定工作表的数据
     *
     * @param sheetName 工作表名称
     * @return
     */
    public List<T> getSheetDataBySheetName(String sheetName) {
        Optional<SheetData<T>> optional = sheetDataList.stream()
                .filter(item -> StringUtils.equals(item.getSheetName(), sheetName)).findFirst();
        return optional.isPresent() ? optional.get().getData() : Collections.emptyList();
    }

    /**
     * 是否有错误
     *
     * @return
     */
    public boolean hasErrors() {
        return !errorLogList.isEmpty();
    }

    /**
     * @return the errorLogList
     */
    public List<ErrorLog<T>> getErrorLogList() {
        return errorLogList;
    }

    /**
     * @param errorLogList the errorLogList to set
     */
    public void setErrorLogList(List<ErrorLog<T>> errorLogList) {
        this.errorLogList = errorLogList;
    }

    /**
     * @return the sheetDataList
     */
    public List<SheetData<T>> getSheetDataList() {
        return sheetDataList;
    }

    /**
     * @return the titleList
     */
    public List<Title> getTitleList() {
        return titleList;
    }

    /**
     * @param titleList the titleList to set
     */
    public void setTitleList(List<Title> titleList) {
        this.titleList = titleList;
    }

}
