package com.hk.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 相关工具类
 *
 * @author: kevin
 * @date 2017年8月30日下午1:15:35
 */
public abstract class StringUtils extends org.springframework.util.StringUtils {

    public static final String RUNG = "-";

    public static final String COLON_SEPARATE = ":";

    public static final String COMMA_SEPARATE = ",";

    public static final String EMPTY = org.apache.commons.lang3.StringUtils.EMPTY;

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
        return org.apache.commons.lang3.StringUtils.equalsAny(cs1, charSequences);
    }

    /**
     * 与任意字符串忽略大小写比较，如果只要有一个相同的，返回 true,否则返回false
     *
     * @param cs1
     * @param charSequences
     * @return
     */
    public static boolean equalsAnyIgnoreCase(CharSequence cs1, CharSequence... charSequences) {
        return org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase(cs1, charSequences);
    }

    /**
     * @param str
     * @param defaultStr
     * @param <T>
     * @return
     */
    public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
        return org.apache.commons.lang3.StringUtils.defaultIfBlank(str, defaultStr);
    }

    /**
     * @param str
     * @param defaultStr
     * @param <T>
     * @return
     */
    public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr) {
        return org.apache.commons.lang3.StringUtils.defaultIfEmpty(str, defaultStr);
    }

    /**
     * <p>
     * 判断是否不为 null or  ""
     * </p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty("ad")      = true
     * StringUtils.isNotEmpty(" ad ")    = true
     * </pre>
     *
     * @param args
     * @return
     */
    public static boolean isNotEmpty(CharSequence args) {
        return !isEmpty(args);
    }

    /**
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     *
     * @param args
     * @return
     */
    public static boolean isBlank(CharSequence args) {
        return org.apache.commons.lang3.StringUtils.isBlank(args);
    }


    /**
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = ftrue
     *
     * @param args
     * @return
     */
    public static boolean isNotBlank(CharSequence args) {
        return !isBlank(args);
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
     * 忽略大小写比较是否相等
     *
     * @param cs1
     * @param cs2
     * @return
     */
    public static boolean equalsIgnoreCase(CharSequence cs1, CharSequence cs2) {
        return org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cs1, cs2);

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

    /**
     * 相等
     *
     * @param cs1
     * @param cs2
     * @return
     */
    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        return org.apache.commons.lang3.StringUtils.equals(cs1, cs2);
    }

    /**
     * 是否以 suffix 结尾
     *
     * @param str
     * @param suffix
     * @return
     */
    public static boolean endsWith(CharSequence str, CharSequence suffix) {
        return org.apache.commons.lang3.StringUtils.endsWith(str, suffix);
    }

    /**
     * 是否包含指定字符串
     * contains
     *
     * @param seq
     * @param searchSeq
     * @return
     */
    public static boolean contains(CharSequence seq, CharSequence searchSeq) {
        return org.apache.commons.lang3.StringUtils.contains(seq, searchSeq);
    }

    /**
     * 是否以 searchSequence 中任意一个开始
     *
     * @param sequence
     * @param searchSequence
     * @return
     */
    public static boolean startsWithAny(CharSequence sequence, CharSequence... searchSequence) {
        return org.apache.commons.lang3.StringUtils.startsWithAny(sequence, searchSequence);
    }

    public static boolean startsWith(CharSequence str, CharSequence prefix) {
        return org.apache.commons.lang3.StringUtils.startsWith(str, prefix);
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param start
     * @return
     */
    public static String substring(String str, int start) {
        return org.apache.commons.lang3.StringUtils.substring(str, start);
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String substring(String str, int start, int end) {
        return org.apache.commons.lang3.StringUtils.substring(str, start, end);
    }

    /**
     * 截取之前部分
     *
     * @param str
     * @param separator
     * @return
     */
    public static String substringBefore(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringBefore(str, separator);
    }

    /**
     * 截取之后部分
     *
     * @param str
     * @param separator
     * @return
     */
    public static String substringAfter(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringAfter(str, separator);
    }

    public static String substringAfterLast(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringAfterLast(str, separator);
    }

    /**
     * trim To Null
     *
     * @param str
     * @return
     */
    public static String trimToNull(String str) {
        return org.apache.commons.lang3.StringUtils.trimToNull(str);
    }

    /**
     * trim To Empty
     *
     * @param str
     * @return
     */
    public static String trimToEmpty(String str) {
        return org.apache.commons.lang3.StringUtils.trimToEmpty(str);
    }

}
