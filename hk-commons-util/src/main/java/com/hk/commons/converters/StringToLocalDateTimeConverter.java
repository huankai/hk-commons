package com.hk.commons.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * String To LocalDateTime
 * 
 * @author kevin
 * @date 2017年11月16日上午9:26:34
 */
public class StringToLocalDateTimeConverter extends StringGenericConverter<LocalDateTime> {

	public StringToLocalDateTimeConverter() {
		super(LocalDateTime.class);
	}

	@Override
	protected LocalDateTime doConvert(String source) {
		return LocalDateTime.parse(source,
				DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM));
	}

}
