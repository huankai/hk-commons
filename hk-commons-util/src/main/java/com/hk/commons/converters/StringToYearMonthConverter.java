package com.hk.commons.converters;

import com.hk.commons.util.date.DatePattern;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * String to YearMouth
 * 
 * @author kevin
 * @date 2017年11月16日上午9:22:26
 */
public class StringToYearMonthConverter extends StringGenericConverter<YearMonth> {

	public StringToYearMonthConverter() {
		super(YearMonth.class);
	}

	@Override
	protected YearMonth doConvert(String source) {
		return YearMonth.parse(source,DateTimeFormatter.ofPattern(DatePattern.YYYY_MM.getPattern()));
	}

}
