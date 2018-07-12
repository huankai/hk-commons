/**
 *
 */
package com.hk.commons.poi.excel.model;

import com.hk.commons.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 解析Excel 结果
 *
 * @author: kevin
 * @date 2018年1月10日下午3:41:43
 */
@SuppressWarnings("serial")
public class ReadResult<T> implements Serializable {

    /**
     * 总错误信息
     */
    @Getter
    private List<ErrorLog<T>> errorLogList = new ArrayList<>();

    /**
     * 解析的每个工作表数据
     */
    @Getter
    private List<SheetData<T>> sheetDataList = new ArrayList<>();

    /**
     * 解析的标题行信息
     */
    @Getter
    @Setter
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
    public List<String> getSheetNameSet() {
        return sheetDataList.stream().map(SheetData::getSheetName).collect(Collectors.toList());
    }

    /**
     * 返回所有工作表数据
     *
     * @return
     */
    public List<T> getAllSheetData() {
        return sheetDataList.stream()
                .map(SheetData::getData)
                .reduce(new ArrayList<>(), (result, dataSheetItem) -> {
                    result.addAll(dataSheetItem);
                    return result;
                });
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


}
