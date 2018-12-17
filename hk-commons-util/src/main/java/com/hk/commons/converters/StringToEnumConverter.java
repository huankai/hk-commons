package com.hk.commons.converters;

/**
 * String To Enum 转换
 * 
 * @author kevin
 * @date 2017年09月20日上午9:05:45
 * @param <T>
 */
public class StringToEnumConverter<T extends Enum<T>> extends StringGenericConverter<Enum<T>> {

	private final Class<Enum<T>> enumType;

	public StringToEnumConverter(Class<Enum<T>> targetType) {
		super(targetType);
		this.enumType = targetType;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Enum<T> doConvert(String source) {
		return Enum.valueOf((Class<T>) enumType, source);
	}

}
