package com.hk.commons.poi.excel.model;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 值格式化
 *
 * @author kevin
 */
public class ValueFormat {

    /**
     * 默认格式化方式
     */
    private static Map<Object, DataFormat> DEFAULT_FORMAT = new HashMap<>();

    private Map<Object, DataFormat> mapper = new HashMap<>();

    static {
        DEFAULT_FORMAT.put(String.class, DataFormat.TEXT_FORMAT);
        DEFAULT_FORMAT.put(Date.class, DataFormat.DATE_FORMAT);
        DEFAULT_FORMAT.put(Short.TYPE, DataFormat.INTEGER_FORMAT);
        DEFAULT_FORMAT.put(Short.class, DataFormat.INTEGER_FORMAT);
        DEFAULT_FORMAT.put(Integer.TYPE, DataFormat.INTEGER_FORMAT);
        DEFAULT_FORMAT.put(Integer.class, DataFormat.INTEGER_FORMAT);
        DEFAULT_FORMAT.put(Long.TYPE, DataFormat.INTEGER_FORMAT);
        DEFAULT_FORMAT.put(Long.class, DataFormat.INTEGER_FORMAT);
        DEFAULT_FORMAT.put(Float.TYPE, DataFormat.DECIMAL_FORMAT_2);
        DEFAULT_FORMAT.put(Float.class, DataFormat.DECIMAL_FORMAT_2);
        DEFAULT_FORMAT.put(Double.TYPE, DataFormat.DECIMAL_FORMAT_2);
        DEFAULT_FORMAT.put(Double.class, DataFormat.DECIMAL_FORMAT_2);
        DEFAULT_FORMAT.put(BigDecimal.class, DataFormat.DECIMAL_FORMAT_2);
        DEFAULT_FORMAT.put(LocalDateTime.class, DataFormat.DATETIME_FORMAT);
        DEFAULT_FORMAT.put(LocalDate.class, DataFormat.DATE_FORMAT);
        DEFAULT_FORMAT.put(LocalTime.class, DataFormat.TIME_FORMAT);
    }

    /**
     *
     */
    public ValueFormat() {
        mapper.putAll(DEFAULT_FORMAT);
    }

    /**
     * 属性名称绑定值格式
     *
     * @param propertyName 属性名称
     * @param format       格式
     */
    public void put(String propertyName, DataFormat format) {
        mapper.put(propertyName, format);
    }

    /**
     * 属性类型绑定值格式
     *
     * @param propertyType 属性类型
     * @param format       格式
     */
    public void put(Class<?> propertyType, DataFormat format) {
        mapper.put(propertyType, format);
    }

    /**
     * 获取映射对象
     */
    public Map<Object, DataFormat> getMapper() {
        return mapper;
    }

    /**
     * 构建值格式
     * <pre>
     *     先根据 propertyName 查询，
     *     如果没有，再根据  propertyType 查询
     *     如果还是没有，返回 {@link DataFormat#TEXT_FORMAT}
     * </pre>
     *
     * @param propertyName 属性名称
     * @param propertyType 属性类型
     * @return {@link DataFormat}
     */
    public DataFormat getFormat(String propertyName, Class<?> propertyType) {
        return mapper.getOrDefault(propertyName, mapper.getOrDefault(propertyType, DataFormat.TEXT_FORMAT));
    }
}
