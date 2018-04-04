package com.hk.commons.converters;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import com.hk.commons.util.date.DatePattern;

/**
 * String to YearMouth
 * 
 * @author huangkai
 * @date 2017年11月16日上午9:22:26
 */
public class StringToYearMonthConverter extends StringGenericConverter<YearMonth> {

	protected StringToYearMonthConverter() {
		super(YearMonth.class);
	}

	@Override
	protected YearMonth doConvert(String source) {
		return YearMonth.parse(source,DateTimeFormatter.ofPattern(DatePattern.YYYY_MM.getPattern()));
	}

}
