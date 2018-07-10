package com.hk.commons.converters;

import com.hk.commons.util.date.DatePattern;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * @author: kevin
 * @date 2018-05-11 10:31
 */
public class StringToJodaDateTimeConverter extends StringGenericConverter<DateTime> {

    public StringToJodaDateTimeConverter() {
        super(DateTime.class);
    }

    @Override
    protected DateTime doConvert(String source) {
        return DateTime.parse(source,DateTimeFormat.forPattern(DatePattern.YYYY_MM_DD_HH_MM_SS.getPattern()));
    }

}
