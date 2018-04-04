package com.hk.commons.converters;

import static com.hk.commons.util.date.DateTimeUtils.stringToDate;

import java.util.Date;
import java.util.Locale;

import com.hk.commons.util.date.DatePattern;

/**
 * String 转换为日期
 * 
 * @author huangkai
 * @date 2017年9月1日上午11:34:17
 */
public class StringToDateConverter extends StringGenericConverter<Date> {

	private static final DatePattern[] PATTERNS = DatePattern.values();

	/**
	 * locale 默认为 Locale.getDefault()
	 */
	private Locale locale;

	public StringToDateConverter() {
		super(Date.class);
	}

	@Override
	protected Date doConvert(String source) {
		return stringToDate(source, locale, PATTERNS);
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
