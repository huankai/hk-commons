package com.hk.commons.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.hk.commons.util.StringUtils;

/**
 * 
 * @author huangkai
 * @date 2017年10月23日下午2:54:55
 */
public class CustomLocalDateTimeEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text)) {
			setValue(LocalDateTime.parse(text,
					DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)));
		}
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return null == value ? null
				: ((LocalDateTime) value)
						.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM));
	}

}
