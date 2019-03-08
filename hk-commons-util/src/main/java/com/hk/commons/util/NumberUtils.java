package com.hk.commons.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author kevin
 * @date 2017-11-24 17:00
 */
public abstract class NumberUtils extends org.springframework.util.NumberUtils {

    /**
     * <pre>
     *  比较两个Number是否相等
     *
     *  如果都为null ，返回true,
     *  如果只有任意一个为null ，返回false,
     *  如果参数类型不一样，返回 false,
     *  如果值不一样，返回false
     *  NumberUtils.equals(null,null); return true
     *  NumberUtils.equals(null,1); return false
     *  NumberUtils.equals(1,1L); return false
     *  NumberUtils.equals(1.0,1.0); return true
     *  NumberUtils.equals(1.0,2); return false
     * </pre>
     *
     * @param n1
     * @param n2
     * @return
     */
    public static boolean equals(Number n1, Number n2) {
        if (ObjectUtils.nullSafeEquals(n1, n2)) {
            return true;
        }
        if (!n1.getClass().equals(n2.getClass())) {
            return false;
        }
        return n1.doubleValue() == n2.doubleValue();
    }

    public static boolean equalsAny(Number value, Number... arr) {
        if (ArrayUtils.isEmpty(arr)) {
            return false;
        }
        for (Number item : arr) {
            if (equals(value, item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 比较两个Number是否不相等
     *
     * @param n1 n1
     * @param n2 n2
     * @return true or false
     */
    public static boolean nequals(Number n1, Number n2) {
        return !equals(n1, n2);
    }

    /**
     * 格式化数值为百分比，默认小数位保留2位
     *
     * @param value 值
     * @return format value
     */
    public static String formatPercent(Object value) {
        return formatPercent(value, 2);
    }

    /**
     * 格式化数值为百分比
     *
     * @param value                 值
     * @param maximumFractionDigits 小数位最大位数
     * @return
     */
    public static String formatPercent(Object value, int maximumFractionDigits) {
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(maximumFractionDigits);
        return format.format(value);
    }

    /**
     * 格式化数值，保留两位小数
     *
     * @param value value
     * @return String
     */
    public static String formatDecimal(Object value) {
        return formatDecimal(value, "#.##");
    }

    /**
     * 格式化数值
     *
     * @param value   value
     * @param pattern pattern 格式化
     * @return format value
     */
    public static String formatDecimal(Object value, String pattern) {
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(value);
    }

}
