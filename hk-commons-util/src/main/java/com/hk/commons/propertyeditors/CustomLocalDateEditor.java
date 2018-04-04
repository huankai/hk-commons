package com.hk.commons.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.hk.commons.util.StringUtils;

/**
 * 
 * @author huangkai
 *
 */
public class CustomLocalDateEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text)) {
			setValue(LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE));
		}
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return null == value ? null : ((LocalDate) value).format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
}
