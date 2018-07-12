package com.hk.commons.converters;


/**
 * Json字符串转换为指定的类型
 *
 * @param <T>
 */
public class JsonStringToObjectConverter<T> extends StringGenericConverter<T> {

    public JsonStringToObjectConverter(Class<T> targetType) {
        super(targetType);
    }

    @Override
    protected T doConvert(String source) {
        return null;
//		return JsonUtils.parseObject(source, getTargetType());
    }

}
