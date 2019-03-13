package com.hk.commons.propertyeditors;

import com.hk.commons.util.StringUtils;
import com.hk.commons.util.date.DatePattern;

import java.beans.PropertyEditorSupport;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * BeanWrapper LocalTime support.
 */
public class CustomLocalTimeEditor extends PropertyEditorSupport {

    public static final CustomLocalTimeEditor INSTANCE = new CustomLocalTimeEditor();

    private static final String[] datePatterns = new String[]{DatePattern.HH_MM_SS.getPattern(),
            DatePattern.HH_MM.getPattern()};

    private CustomLocalTimeEditor() {

    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isNotEmpty(text)) {
            for (String datePattern : datePatterns) {
                try {
                    setValue(LocalTime.parse(text, DateTimeFormatter.ofPattern(datePattern)));
                    break;
                } catch (DateTimeParseException e) {
                    // ignore
                }
            }
        }
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return null == value ? null
                : ((LocalTime) value).format(DateTimeFormatter.ofPattern(DatePattern.HH_MM.getPattern()));
    }

}
