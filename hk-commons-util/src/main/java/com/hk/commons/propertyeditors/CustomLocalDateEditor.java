package com.hk.commons.propertyeditors;

import com.hk.commons.util.StringUtils;
import org.springframework.beans.BeanWrapper;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * BeanWrapper LocalDate support
 *
 * @author: kevin
 * @see com.hk.commons.util.BeanWrapperUtils#registryDefaultEditor(BeanWrapper)
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
        return null == value ? null : LocalDate.class.cast(value).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
