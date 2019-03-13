package com.hk.commons.propertyeditors;

import com.hk.commons.util.StringUtils;
import com.hk.commons.util.date.DatePattern;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * BeanWrapper LocalDate support
 *
 * @author kevin
 */
public class CustomLocalDateEditor extends PropertyEditorSupport {

    public static final CustomLocalDateEditor INSTANCE = new CustomLocalDateEditor();

    private static final String[] datePatterns = new String[]{DatePattern.YYYY_MM_DD.getPattern(),
            DatePattern.YYYY_MM_DD_EN.getPattern(),
            DatePattern.YYYY_MM_DD_CN.getPattern()};

    private CustomLocalDateEditor() {

    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isNotEmpty(text)) {
            for (String datePattern : datePatterns) {
                try {
                    setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern(datePattern)));
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
        return null == value ? null : ((LocalDate) value).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
