package com.hk.commons.converters;

import org.springframework.util.NumberUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * String 转换为Number类型
 *
 * @param <T>
 * @author kevin
 * @date 2017年9月1日上午11:34:42
 */
public abstract class StringToNumberConverter<T extends Number> extends StringGenericConverter<T> {

    StringToNumberConverter(Class<T> targetType) {
        super(targetType);
    }

    StringToNumberConverter(T defaultValue, Class<T> targetType) {
        super(defaultValue, targetType);
    }

    @Override
    protected T doConvert(String source) {
        return NumberUtils.parseNumber(source, getTargetType());
    }

    /**
     * 转换为 Long类型
     *
     * @author kevin
     * @date 2017年9月1日上午11:34:55
     */
    public static class StringToLongConverter extends StringToNumberConverter<Long> {

        public StringToLongConverter() {
            super(Long.class);
        }
    }

    /**
     * 转换为 Integer类型
     *
     * @author kevin
     * @date 2017年9月1日上午11:35:04
     */
    public static class StringToIntegerConverter extends StringToNumberConverter<Integer> {

        public StringToIntegerConverter() {
            super(Integer.class);
        }

    }

    /**
     * 转换为BigInteger类型
     *
     * @author kevin
     * @date 2017年9月1日上午11:35:20
     */
    public static class StringToBigIntegerConverter extends StringToNumberConverter<BigInteger> {

        public StringToBigIntegerConverter() {
            super(BigInteger.class);
        }

    }

    /**
     * 转换为BigInteger类型
     *
     * @author kevin
     * @date 2017年9月1日上午11:35:20
     */
    public static class StringToBigDecimalConverter extends StringToNumberConverter<BigDecimal> {

        public StringToBigDecimalConverter() {
            super(BigDecimal.class);
        }

    }
}
