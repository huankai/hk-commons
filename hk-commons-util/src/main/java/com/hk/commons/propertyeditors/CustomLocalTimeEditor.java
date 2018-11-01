package com.hk.commons.propertyeditors;

import com.hk.commons.util.StringUtils;
import com.hk.commons.util.date.DatePattern;
import org.springframework.beans.BeanWrapper;

import java.beans.PropertyEditorSupport;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * BeanWrapper LocalTime support.
 *
 * @see com.hk.commons.util.BeanWrapperUtils#registryDefaultEditor(BeanWrapper)
 */
public class CustomLocalTimeEditor extends PropertyEditorSupport {

    public static final CustomLocalTimeEditor INSTANCE = new CustomLocalTimeEditor();

    private CustomLocalTimeEditor() {

    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isNotEmpty(text)) {
            setValue(LocalTime.parse(text, DateTimeFormatter.ofPattern(DatePattern.HH_MM.getPattern())));
        }
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return null == value ? null
                : LocalTime.class.cast(value).format(DateTimeFormatter.ofPattern(DatePattern.HH_MM.getPattern()));
    }

}
