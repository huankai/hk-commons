package com.hk.commons.converters;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

/**
 * java.sql.Date -> org.joda.time.DateTime.DateTime
 * 
 * @author huangkai
 * @date 2017年12月14日下午4:34:27
 */
public class TimestampToJodaDateTimeConverter implements GenericConverter {

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return Collections.singleton(new ConvertiblePair(Timestamp.class, DateTime.class));
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		return new DateTime(source);
	}

}
