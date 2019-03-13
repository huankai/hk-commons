package com.hk.commons.propertyeditors;

import com.hk.commons.util.StringUtils;
import com.hk.commons.util.date.DatePattern;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

/**
 * BeanWrapper LocalDateTime support
 *
 * @author kevin
 * @date 2017年10月23日下午2:54:55
 */
public class CustomLocalDateTimeEditor extends PropertyEditorSupport {

    public static final CustomLocalDateTimeEditor INSTANCE = new CustomLocalDateTimeEditor();

    private static final String[] datePatterns = new String[]{DatePattern.YYYY_MM_DD_HH_MM_SS.getPattern(),
            DatePattern.YYYY_MM_DD_HH_MM.getPattern(),
            DatePattern.YYYY_MM_DD_HH_MM_CN.getPattern(), DatePattern.YYYY_MM_DD_HH_MM_EN.getPattern(),
            DatePattern.YYYY_MM_DD_HH_MM_SS_EN.getPattern(),DatePattern.YYYY_MM_DD_HH_MM_SS_CN.getPattern()};

    private CustomLocalDateTimeEditor() {
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isNotEmpty(text)) {
            for (String datePattern : datePatterns) {
                try {
                    setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern(datePattern)));
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
                : ((LocalDateTime) value)
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM));
    }

}
