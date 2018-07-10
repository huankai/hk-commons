package com.hk.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 相关工具类
 *
 * @author: kevin
 * @date 2017年8月30日下午1:15:35
 */
public abstract class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String RUNG = "-";

    public static final String COLON_SEPARATE = ":";

    public static final String COMMA_SEPARATE = ",";

    private static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");


    /**
     * 下划线转小驼峰
     *
     * @param str
     * @return
     */
    public static String lineToSmallHump(String str) {
        return lineToHump(uncapitalize(str));
    }

    private static String lineToHump(String str) {
        Matcher matcher = LINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线转大驼峰
     */
    public static String lineToBigHump(String str) {
        return lineToHump(capitalize(str));
    }

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
