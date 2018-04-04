package com.hk.commons.converters;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.hk.commons.util.date.DatePattern;

public class StringToLocalTimeConverter extends StringGenericConverter<LocalTime> {

	public StringToLocalTimeConverter() {
		super(LocalTime.class);
	}

	@Override
	protected LocalTime doConvert(String source) {
		return LocalTime.parse(source, DateTimeFormatter.ofPattern(DatePattern.HH_MM.getPattern()));
	}

}
