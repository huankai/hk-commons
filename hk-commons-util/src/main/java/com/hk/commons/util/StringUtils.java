package com.hk.commons.util;

/**
 * String 相关工具类
 *
 * @author huangkai
 * @date 2017年8月30日下午1:15:35
 */
public abstract class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String RUNG = "-";

    public static final String COLON_SEPARATE = ":";

    public static final String COMMA_SEPARATE = ",";

    /**
     * 与任意字符串比较，如果只要有一个相同的，返回 true,否则返回false
     *
     * @param cs1
     * @param charSequences
     * @return
     */
    public static boolean equalsAny(CharSequence cs1, CharSequence... charSequences) {
        if (ArrayUtils.isEmpty(charSequences)) {
            return false;
        }
        for (CharSequence charSequence : charSequences) {
            if (equals(cs1, charSequence)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 与任意字符串忽略大小写比较，如果只要有一个相同的，返回 true,否则返回false
     *
     * @param cs1
     * @param charSequences
     * @return
     */
    public static boolean equalsAnyIgnoreCase(CharSequence cs1, CharSequence... charSequences) {
        if (ArrayUtils.isEmpty(charSequences)) {
            return false;
        }
        for (CharSequence charSequence : charSequences) {
            if (equalsIgnoreCase(cs1, charSequence)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据英文逗号切割字符串
     *
     * @param args 要切割的字符串
     * @return
     */
    public static String[] splitByComma(String args) {
        return split(args, "\\,");
    }

    /**
     * 不相等
     *
     * @param cs1
     * @param cs2
     * @return
     */
    public static boolean notEquals(CharSequence cs1, CharSequence cs2) {
        return !equals(cs1, cs2);
    }

}
