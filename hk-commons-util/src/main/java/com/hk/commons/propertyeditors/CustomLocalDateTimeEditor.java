package com.hk.commons.propertyeditors;

import com.hk.commons.util.StringUtils;
import org.springframework.beans.BeanWrapper;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * BeanWrapper LocalDateTime support
 *
 * @author: kevin
 * @date 2017年10月23日下午2:54:55
 * @see com.hk.commons.util.BeanWrapperUtils#registryDefaultEditor(BeanWrapper)
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
                : LocalDateTime.class.cast(value)
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM));
    }

}
