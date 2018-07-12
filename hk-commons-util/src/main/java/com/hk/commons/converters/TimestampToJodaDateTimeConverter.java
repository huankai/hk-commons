package com.hk.commons.converters;

import org.springframework.core.convert.converter.GenericConverter;

/**
 * java.sql.Date -> org.joda.time.DateTime.DateTime
 * 
 * @author: kevin
 * @date 2017年12月14日下午4:34:27
 */
@Deprecated
public abstract class TimestampToJodaDateTimeConverter implements GenericConverter {

//	@Override
//	public Set<ConvertiblePair> getConvertibleTypes() {
//		return Collections.singleton(new ConvertiblePair(Timestamp.class, DateTime.class));
//	}
//
//	@Override
//	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
//		return new DateTime(source);
//	}

}
