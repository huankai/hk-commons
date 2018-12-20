package com.hk.commons.poi.excel.model;

import com.hk.commons.util.date.DatePattern;

/**
 * Excel格式
 *
 * @author kevin
 */
public enum DataFormat {

    /**
     * 文本格式
     */
    TEXT_FORMAT("GENERAL"),

    /**
     * 日期格式: yyyy-MM-dd
     */
    DATE_FORMAT(DatePattern.YYYY_MM_DD.getPattern()),

    /**
     * 日期格式: yyyy年MM月dd日
     */
    DATE_FORMAT_CN(DatePattern.YYYY_MM_DD_CN.getPattern()),

    /**
     * 日期格式：yyyy/MM/dd
     */
    DATE_FORMAT_EN(DatePattern.YYYY_MM_DD_EN.getPattern()),
    /**
     * 日期格式:yyyy/MM/dd HH:mm:ss
     */
    DATETIME_FORMAT_EN(DatePattern.YYYY_MM_DD_HH_MM_SS_EN.getPattern()),

    /**
     * 日期时间格式:yyyy-MM-dd HH:mm:ss
     */
    DATETIME_FORMAT(DatePattern.YYYY_MM_DD_HH_MM_SS.getPattern()),

    /**
     * 时间格式：HH:mm:ss
     */
    TIME_FORMAT(DatePattern.HH_MM_SS.getPattern()),

    /**
     * 整数格式
     */
    INTEGER_FORMAT("#"),

    /**
     * 小数格式, 精确到小数点后一位
     */
    DECIMAL_FORMAT_1("#.#"),

    /**
     * 小数格式, 精确到小数点后两位
     */
    DECIMAL_FORMAT_2("#.##"),

    /**
     * 小数格式, 精确到小数点三位
     */
    DECIMAL_FORMAT_3("#.###"),

    /**
     * 百分比格式
     */
    PERCENT_FORMAT("0%"),

    /**
     * 百分比格式, 精确到小数点后一位
     */
    PERCENT_FORMAT_1("0.0%"),

    /**
     * 百分比格式, 精确到小数点后两位
     */
    PERCENT_FORMAT_2("0.00%"),

    /**
     * 百分比格式, 精确到小数点后三位
     */
    PERCENT_FORMAT_3("0.000%"),

    /**
     * 货币格式
     */
    CURRENCY_FORMAT("￥#,##0;￥-#,##0"),

    /**
     * 货币格式, 精确到小数点后一位
     */
    CURRENCY_FORMAT_1("￥#,##0.0;￥-#,##0.0"),

    /**
     * 货币格式, 精确到小数点后两位
     */
    CURRENCY_FORMAT_2("￥#,##0.00;￥-#,##0.00"),

    /**
     * 货币格式, 精确到小数点后三位
     */
    CURRENCY_FORMAT_3("￥#,##0.000;￥-#,##0.000");

    DataFormat(String pattern) {
        this.pattern = pattern;
    }

    private String pattern;

    public String getPattern() {
        return pattern;
    }
}
