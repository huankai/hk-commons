package com.hk.commons.converters;

import java.util.Calendar;
import java.util.Date;

import com.hk.commons.util.date.DateTimeUtils;

public class StringToCalendarConverter extends StringGenericConverter<Calendar> {

	public StringToCalendarConverter() {
		super(Calendar.class);
	}

	@Override
	protected Calendar doConvert(String source) {
		Date date = DateTimeUtils.stringToDate(source);
		return DateTimeUtils.dateToCalendar(date);
	}

}
