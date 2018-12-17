package com.hk.commons.converters;

import java.time.Year;
import java.time.format.DateTimeFormatter;

import com.hk.commons.util.date.DatePattern;

/**
 * String To Year
 * 
 * @author kevin
 * @date 2017年11月16日上午9:26:20
 */
public class StringToYearConverter extends StringGenericConverter<Year> {

	public StringToYearConverter() {
		super(Year.class);
	}

	@Override
	protected Year doConvert(String source) {
		return Year.parse(source, DateTimeFormatter.ofPattern(DatePattern.YYYY.getPattern()));
	}

}
