/**
 *
 */
package com.hk.commons.poi.excel.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 写入到Excel 参数
 *
 * @author kevin
 */
@Data
@Builder
public class WriteParam<T> {

    /**
     *
     */
    public static final String DEFAULT_SHEET_NAME = "sheet1";

    /**
     * 标题行
     */
    protected static final int DEFAULT_TITLE_ROW = 0;

    /**
     * 数据开始行
     */
    protected static final int DEFAULT_DATA_START_ROW = 1;

    /**
     * 工作表名
     */
    @Builder.Default
    private String sheetName = DEFAULT_SHEET_NAME;

    /**
     * 导出的数据
     */
    private List<T> data;

    /**
     * 转换的 Beanclass
     */
    private Class<T> beanClazz;

    /**
     * 标题行，默认为第0行
     */
    @Builder.Default
    private int titleRow = DEFAULT_TITLE_ROW;

    /**
     * 数据开始行，默认为第一行开始
     */
    @Builder.Default
    private int dataStartRow = DEFAULT_DATA_START_ROW;

    /**
     * 标题行高
     */
    @Builder.Default
    private float titleRowHeight = 30;

    /**
     * 数据行高
     */
    @Builder.Default
    private float dataRowHeight = 25;

    /**
     * 单元格值格式化
     */
    @Builder.Default
    private ValueFormat valueFormat = new ValueFormat();

    /**
     * 显示网格
     */
    @Builder.Default
    private boolean displayGridLines = true;

    /**
     * 文件密码
     */
    private String password;

    /**
     * <pre>
     * 当有NestedProperty注解(一对多)时，一的一方是否合并单元格
     * </pre>
     */
    @Builder.Default
    private boolean mergeCell = true;

    /**
     * 添加属性单元格导出格式
     *
     * @param propertyName propertyName
     * @param format       {@link DataFormat}
     * @return {@link WriteParam}
     */
    public WriteParam<T> addPropertyformat(String propertyName, DataFormat format) {
        valueFormat.put(propertyName, format);
        return this;
    }

    /**
     * 添加指定类型的单元格导出样式
     *
     * @param clazz  clazz
     * @param format {@link DataFormat}
     * @return {@link WriteParam}
     */
    public WriteParam<T> addClassFormat(Class<?> clazz, DataFormat format) {
        valueFormat.put(clazz, format);
        return this;
    }

}
