package com.hk.commons.util;

import com.hk.commons.converters.StringToBooleanConverter;
import com.hk.commons.converters.StringToDateConverter;
import com.hk.commons.converters.StringToNumberConverter.StringToBigIntegerConverter;
import com.hk.commons.converters.StringToNumberConverter.StringToIntegerConverter;
import com.hk.commons.converters.StringToNumberConverter.StringToLongConverter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * 转换工具类
 *
 * @author: kevin
 * @date: 2017年8月30日上午10:39:23
 */
public class ConverterUtils {

    /**
     * 默认转换
     */
    public static DefaultConversionService DEFAULT_CONVERSION_SERVICE = new DefaultConversionService();

    /**
     * 转换
     */
    private DefaultConversionService conversionService;

    static {
        DEFAULT_CONVERSION_SERVICE.addConverter(new StringToBooleanConverter());
        DEFAULT_CONVERSION_SERVICE.addConverter(new StringToDateConverter());
        DEFAULT_CONVERSION_SERVICE.addConverter(new StringToLongConverter());
        DEFAULT_CONVERSION_SERVICE.addConverter(new StringToIntegerConverter());
        DEFAULT_CONVERSION_SERVICE.addConverter(new StringToBigIntegerConverter());
    }

    private ConverterUtils() {
        conversionService = DEFAULT_CONVERSION_SERVICE;
    }

    /**
     * 获取实例
     *
     * @return ConverterUtils
     */
    public static ConverterUtils getInstance() {
        return new ConverterUtils();
    }

    /**
     * 添加一个转换器
     *
     * @param converter ConverterUtils
     * @return
     */
    public ConverterUtils addConverter(GenericConverter converter) {
        return addConverters(converter);
    }

    /**
     * 获取转换器
     *
     * @return DefaultConversionService
     */
    public DefaultConversionService getConversionService() {
        return conversionService;
    }

    /**
     * 添加多个转换器
     *
     * @param converters converters
     * @return ConverterUtils
     */
    public ConverterUtils addConverters(GenericConverter... converters) {
        if (ArrayUtils.isNotEmpty(converters)) {
            for (GenericConverter converter : converters) {
                if (null != converter) {
                    conversionService.addConverter(converter);
                }
            }
        }
        return this;
    }

    /**
     * 将值转换为指定的类型
     *
     * <pre>
     * String -> Boolean | boolean
     * String -> java.util.Date 或其子类
     * String -> Long | long
     * String -> Integer | int
     * String -> java.math.BigInteger
     * </pre>
     *
     * @param value      value
     * @param targetType targetType
     * @return
     */
    public <T> T convert(Object value, Class<T> targetType) {
        return conversionService.convert(value, targetType);
    }

}
