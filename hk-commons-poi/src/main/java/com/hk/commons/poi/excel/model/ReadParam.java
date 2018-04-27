/**
 *
 */
package com.hk.commons.poi.excel.model;

import com.hk.commons.poi.excel.read.interceptor.DefaultValidationInterceptor;
import com.hk.commons.poi.excel.read.interceptor.ValidationInterceptor;
import com.hk.commons.poi.excel.read.validation.Validationable;

import java.util.List;
import java.util.Map;

/**
 * 读取 Excel参数
 *
 * @author kally
 * @date 2018年1月10日下午5:17:49
 */
public class ReadParam<T> {

    /**
     * 標題行
     */
    private int titleRow;

    /**
     * 数据开始行
     */
    private int dataStartRow = 1;

    /**
     * <pre>
     * 是否解析所有工作表
     * 如果设置为 true,自定义的 sheetStartIndex 与 sheetMaxIndex 将被覆盖
     * </pre>
     */
    private boolean parseAll;

    /**
     * 开始读取的sheet位置
     */
    private int sheetStartIndex;

    /**
     * 上传表格需要读取的sheet最大的索引
     */
    private int sheetMaxIndex;

    /**
     * 是否输出公式结果值
     */
    private boolean outputFormulaValues;

    /**
     * 是否过滤单元格首尾空字符串
     */
    private boolean trim = true;

    /**
     * <pre>
     * 是否忽略换行符号
     * </pre>
     */
    private boolean ingoreLineBreak = true;

    /**
     * 列与属性映射
     */
    private Map<Integer, String> columnProperties;

    /**
     * BeanClazz
     */
    private Class<T> beanClazz;

    /**
     * 验证拦截器
     */
    private ValidationInterceptor<T> interceptor = new DefaultValidationInterceptor<>();

    /**
     * 验证器
     */
    private List<Validationable<T>> validationList;

    /**
     * @return the beanClazz
     */
    public Class<T> getBeanClazz() {
        return beanClazz;
    }

    /**
     * @param beanClazz the beanClazz to set
     */
    public ReadParam<T> setBeanClazz(Class<T> beanClazz) {
        this.beanClazz = beanClazz;
        return this;
    }

    /**
     * @return the parseAll
     */
    public boolean isParseAll() {
        return parseAll;
    }

    /**
     * @param parseAll the parseAll to set
     */
    public ReadParam<T> setParseAll(boolean parseAll) {
        this.parseAll = parseAll;
        return this;
    }

    /**
     * @return the sheetStartIndex
     */
    public int getSheetStartIndex() {
        return sheetStartIndex;
    }

    /**
     * @param sheetStartIndex the sheetStartIndex to set
     */
    public ReadParam<T> setSheetStartIndex(int sheetStartIndex) {
        this.sheetStartIndex = sheetStartIndex;
        return this;
    }

    /**
     * @return the sheetMaxIndex
     */
    public int getSheetMaxIndex() {
        return sheetMaxIndex;
    }

    /**
     * @param sheetMaxIndex the sheetMaxIndex to set
     */
    public ReadParam<T> setSheetMaxIndex(int sheetMaxIndex) {
        this.sheetMaxIndex = sheetMaxIndex;
        return this;
    }

    /**
     * @return the outputFormulaValues
     */
    public boolean isOutputFormulaValues() {
        return outputFormulaValues;
    }

    /**
     * @param outputFormulaValues the outputFormulaValues to set
     */
    public ReadParam<T> setOutputFormulaValues(boolean outputFormulaValues) {
        this.outputFormulaValues = outputFormulaValues;
        return this;
    }

    /**
     * @return the trim
     */
    public boolean isTrim() {
        return trim;
    }

    /**
     * @param trim the trim to set
     */
    public ReadParam<T> setTrim(boolean trim) {
        this.trim = trim;
        return this;
    }

    /**
     * @return the ingoreLineBreak
     */
    public boolean isIngoreLineBreak() {
        return ingoreLineBreak;
    }

    /**
     * @param ingoreLineBreak the ingoreLineBreak to set
     */
    public ReadParam<T> setIngoreLineBreak(boolean ingoreLineBreak) {
        this.ingoreLineBreak = ingoreLineBreak;
        return this;
    }

    /**
     * @return the titleRow
     */
    public int getTitleRow() {
        return titleRow;
    }

    /**
     * @param titleRow the titleRow to set
     */
    public ReadParam<T> setTitleRow(int titleRow) {
        this.titleRow = titleRow;
        return this;
    }

    /**
     * @return the dataStartRow
     */
    public int getDataStartRow() {
        return dataStartRow;
    }

    /**
     * @param dataStartRow the dataStartRow to set
     */
    public ReadParam<T> setDataStartRow(int dataStartRow) {
        this.dataStartRow = dataStartRow;
        return this;
    }

    /**
     * @return the columnProperties
     */
    public Map<Integer, String> getColumnProperties() {
        return columnProperties;
    }

    /**
     * @param columnProperties the columnProperties to set
     */
    public ReadParam<T> setColumnProperties(Map<Integer, String> columnProperties) {
        this.columnProperties = columnProperties;
        return this;
    }

    /**
     * @return the interceptor
     */
    public ValidationInterceptor<T> getInterceptor() {
        return interceptor;
    }

    /**
     * @param interceptor the interceptor to set
     */
    public ReadParam<T> setInterceptor(ValidationInterceptor<T> interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    /**
     * @return the validationList
     */
    public List<Validationable<T>> getValidationList() {
        return validationList;
    }

    /**
     * @param validationList the validationList to set
     */
    public ReadParam<T> setValidationList(List<Validationable<T>> validationList) {
        this.validationList = validationList;
        return this;
    }

}
