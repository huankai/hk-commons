package com.hk.commons.converters;

/**
 * @author: kevin
 * @date 2018-05-11 10:31
 */
@Deprecated
public abstract class StringToJodaDateTimeConverter extends StringGenericConverter {

    protected StringToJodaDateTimeConverter(Class targetType) {
        super(targetType);
    }

//    public StringToJodaDateTimeConverter() {
//        super(DateTime.class);
//    }
//
//    @Override
//    protected DateTime doConvert(String source) {
//        return DateTime.parse(source,DateTimeFormat.forPattern(DatePattern.YYYY_MM_DD_HH_MM_SS.getPattern()));
//    }

}
