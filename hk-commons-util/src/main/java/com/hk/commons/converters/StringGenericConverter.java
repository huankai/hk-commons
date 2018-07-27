package com.hk.commons.converters;

import com.hk.commons.util.StringUtils;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Collections;
import java.util.Set;

/**
 * String转换器
 *
 * @param <T>
 * @author: kevin
 * @date 2017年9月1日上午11:35:56
 */
public abstract class StringGenericConverter<T> implements GenericConverter {

    /**
     * 为空时的默认值
     */
    private T defaultValue;

    /**
     * 目标类型
     */
    public final Class<T> targetType;

    protected StringGenericConverter(Class<T> targetType) {
        this.targetType = targetType;
    }

    public StringGenericConverter(T defaultValue, Class<T> targetType) {
        this.defaultValue = defaultValue;
        this.targetType = targetType;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Class<T> getTargetType() {
        return targetType;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, targetType));
    }

    @Override
    public T convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return defaultValue;
        }
        try {
            return doConvert(StringUtils.trimWhitespace(source.toString()));
        } catch (Exception e) {
            throw new ConversionFailedException(TypeDescriptor.forObject(source),
                    TypeDescriptor.valueOf(this.targetType), source, e);
        }
    }

    protected abstract T doConvert(String source);

}
