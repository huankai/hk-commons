package com.hk.commons.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 相关工具类
 *
 * @author kevin
 * @date 2017年8月30日下午1:15:35
 */
public abstract class StringUtils extends org.springframework.util.StringUtils {

    /**
     * - 分隔符
     */
    public static final String RUNG = "-";

    /**
     * : 分隔符
     */
    public static final String COLON_SEPARATE = ":";

    /**
     * , 分隔符
     */
    public static final String COMMA_SEPARATE = ",";

    /**
     * 空字符串
     */
    public static final String EMPTY = org.apache.commons.lang3.StringUtils.EMPTY;

    /**
     * 下划线 pattern
     */
    private static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");

    /**
     * 数字开头匹配
     */
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");

    /**
     * 字母开头匹配
     */
    private static final Pattern LETTER_PATTERN = Pattern.compile("[A-Z]*");

    /**
     * 大写字符匹配
     */
    private static final String HUMP_TO_LINE = "[A-Z]";

    /**
     * 换行符
     */
    public static final String LF = org.apache.commons.lang3.StringUtils.LF;

    /**
     * 空白字符
     */
    public static final String SPACE = org.apache.commons.lang3.StringUtils.SPACE;

    /**
     * 下划线转小驼峰
     *
     * @param str str
     * @return String
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
     * 判断是否以数字开头
     *
     * @param str str
     * @return true or false
     */
    public static boolean startWithNumber(String str) {
        return isNotEmpty(str) && NUMBER_PATTERN.matcher(String.valueOf(str.charAt(0))).matches();
    }

    /**
     * 判断是否以字母开头
     *
     * @param str str
     * @return true or false
     */
    public static boolean startWithLetter(String str) {
        return isNotEmpty(str) && LETTER_PATTERN.matcher(String.valueOf(str.charAt(0))).matches();
    }

    /**
     * 下划线转大驼峰
     *
     * @param str str
     */
    public static String lineToBigHump(String str) {
        return lineToHump(capitalize(str));
    }

    /**
     * 驼峰转下划线小写
     *
     * @param str str
     * @return {@link String}
     */
    public static String humpToLineLower(String str) {
        return StringUtils.isEmpty(str) ? null : str.replaceAll(HUMP_TO_LINE, "_$0").toLowerCase();
    }

    /**
     * 驼峰转下划线大写
     *
     * @param str str
     * @return {@link String}
     */
    public static String humpToLineUpper(String str) {
        return StringUtils.isEmpty(str) ? null : str.replaceAll(HUMP_TO_LINE, "_$0").toUpperCase();
    }

    /**
     * 与任意字符串比较，如果只要有一个相同的，返回 true,否则返回false
     *
     * @param cs1           cs1
     * @param charSequences charSequences
     * @return true or false
     */
    public static boolean equalsAny(CharSequence cs1, CharSequence... charSequences) {
        return org.apache.commons.lang3.StringUtils.equalsAny(cs1, charSequences);
    }

    /**
     * string to byte[] ，如果参数 s 为 null or "" ，返回 null
     *
     * @param s s
     * @return byte[]
     */
    public static byte[] getByteUtf8(String s) {
        return isEmpty(s) ? null : s.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 是否为数字类型
     *
     * @param args args
     * @return true or false
     */
    public static boolean isNumber(String args) {
        return NumberUtils.isCreatable(args);
    }

    /**
     * @param bytes bytes
     * @return String
     */
    public static String newStringIso8859_1(byte[] bytes) {
        return new String(bytes, StandardCharsets.ISO_8859_1);
    }

    /**
     * 与任意字符串忽略大小写比较，如果只要有一个相同的，返回 true,否则返回false
     *
     * @param cs1           cs1
     * @param charSequences charSequences
     * @return true or false
     */
    public static boolean equalsAnyIgnoreCase(CharSequence cs1, CharSequence... charSequences) {
        return org.apache.commons.lang3.StringUtils.equalsAnyIgnoreCase(cs1, charSequences);
    }

    /**
     * <pre>
     *
     * 如果参数  str 为 null ，返回 defaultStr
     * 如果参数  str 为 "" ，返回 defaultStr
     * 如果参数  str 为 "   " ，返回 defaultStr
     * </pre>
     *
     * @param str        str
     * @param defaultStr defaultStr
     * @param <T>        T
     * @return T
     */
    public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    /**
     * <pre>
     *
     * 如果参数  str 为 null ，返回 defaultStr
     * 如果参数  str 为 "" ，返回 defaultStr
     * </pre>
     *
     * @param str        str
     * @param defaultStr defaultStr
     * @param <T>        T
     * @return T
     */
    public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    /**
     * <p>
     * 判断是否不为 null or ""
     * </p>
     * <p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty("ad")      = true
     * StringUtils.isNotEmpty(" ad ")    = true
     * </pre>
     *
     * @param args args
     * @return true or false
     */
    public static boolean isNotEmpty(CharSequence args) {
        return !isEmpty(args);
    }

    /**
     * <pre>
     * StringUtils.isBlank(null) = true
     * StringUtils.isBlank("") = true
     * StringUtils.isBlank(" ") = true
     * StringUtils.isBlank("bob") = false
     * StringUtils.isBlank(" bob ") = false
     *
     * </pre>
     *
     * @param args args
     * @return true or false
     */
    public static boolean isBlank(CharSequence args) {
        return org.apache.commons.lang3.StringUtils.isBlank(args);
    }

    /**
     * <pre>
     * StringUtils.isNotBlank(null) = false
     * StringUtils.isNotBlank("") = false
     * StringUtils.isNotBlank(" ") = false
     * StringUtils.isNotBlank("bob") = true
     * StringUtils.isNotBlank(" bob ") = true
     *
     * </pre>
     *
     * @param args args
     * @return true or false
     */
    public static boolean isNotBlank(CharSequence args) {
        return !isBlank(args);
    }

    /**
     * 根据英文逗号切割字符串
     *
     * @param args 要切割的字符串
     * @return 切割后的数组
     */
    public static String[] splitByComma(String args) {
        return tokenizeToStringArray(args, "\\,");
    }

    /**
     * 忽略大小写比较是否相等
     *
     * @param cs1 cs1
     * @param cs2 cs2
     * @return true or false
     */
    public static boolean equalsIgnoreCase(CharSequence cs1, CharSequence cs2) {
        return org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cs1, cs2);
    }

    /**
     * 查询 searchSeq 在 seq 中的索引位置
     *
     * @param seq       seq
     * @param searchSeq searchSeq
     * @return 索引位置，不存在返回 -1
     */
    public static int indexOf(final CharSequence seq, final CharSequence searchSeq) {
        return org.apache.commons.lang3.StringUtils.indexOf(seq, searchSeq);
    }

    /**
     * 判断两个字符串是否不相等
     *
     * @param cs1 cs1
     * @param cs2 cs2
     * @return true or false
     */
    public static boolean notEquals(CharSequence cs1, CharSequence cs2) {
        return !equals(cs1, cs2);
    }

    /**
     * 相等
     *
     * @param cs1 cs1
     * @param cs2 cs2
     * @return boolean
     */
    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        return org.apache.commons.lang3.StringUtils.equals(cs1, cs2);
    }

    /**
     * 是否以 suffix 结尾
     *
     * @param str    str
     * @param suffix suffix
     * @return true or false
     */
    public static boolean endsWith(CharSequence str, CharSequence suffix) {
        return org.apache.commons.lang3.StringUtils.endsWith(str, suffix);
    }

    /**
     * 是否包含指定字符串 contains
     *
     * @param seq       seq
     * @param searchSeq searchSeq
     * @return true or false
     */
    public static boolean contains(CharSequence seq, CharSequence searchSeq) {
        return org.apache.commons.lang3.StringUtils.contains(seq, searchSeq);
    }

    /**
     * 是否包含指定字符串 contains
     *
     * @param seq       seq
     * @param searchSeq searchSeq
     * @return true or false
     */
    public static boolean containsIgnoreCase(CharSequence seq, CharSequence searchSeq) {
        return org.apache.commons.lang3.StringUtils.containsIgnoreCase(seq, searchSeq);
    }

    /**
     * 是否以 searchSequence 中任意一个开始
     *
     * @param sequence       sequence
     * @param searchSequence searchSequence
     * @return true or false
     */
    public static boolean startsWithAny(CharSequence sequence, CharSequence... searchSequence) {
        return org.apache.commons.lang3.StringUtils.startsWithAny(sequence, searchSequence);
    }

    /**
     * 判断 str 是否以  prefix 开头
     *
     * @param str    str
     * @param prefix prefix
     * @return true or false
     */
    public static boolean startsWith(CharSequence str, CharSequence prefix) {
        return org.apache.commons.lang3.StringUtils.startsWith(str, prefix);
    }

    /**
     * 截取字符串
     *
     * @param str   str
     * @param start start
     * @return {@link String}
     */
    public static String substring(String str, int start) {
        return org.apache.commons.lang3.StringUtils.substring(str, start);
    }

    /**
     * 截取字符串
     *
     * @param str   str
     * @param start start
     * @param end   end
     * @return {@link String}
     */
    public static String substring(String str, int start, int end) {
        return org.apache.commons.lang3.StringUtils.substring(str, start, end);
    }

    /**
     * 截取之前部分
     *
     * @param str       str
     * @param separator separator
     * @return {@link String}
     */
    public static String substringBefore(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringBefore(str, separator);
    }

    /**
     * 根据查询的最后字符串截取之前部分
     *
     * @param str       str
     * @param separator separator
     * @return {@link String}
     */
    public static String substringBeforeLast(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringBeforeLast(str, separator);
    }

    /**
     * 截取之后部分
     *
     * @param str       str
     * @param separator separator
     * @return {@link String}
     */
    public static String substringAfter(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringAfter(str, separator);
    }

    /**
     * 根据查询的最后字符串截取之后部分
     *
     * @param str       str
     * @param separator separator
     * @return {@link String}
     */
    public static String substringAfterLast(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringAfterLast(str, separator);
    }

    /**
     * trim To Null
     *
     * @param str str
     * @return {@link String}
     */
    public static String trimToNull(String str) {
        return org.apache.commons.lang3.StringUtils.trimToNull(str);
    }

    /**
     * trim To Empty
     *
     * @param str str
     * @return {@link String}
     */
    public static String trimToEmpty(String str) {
        return org.apache.commons.lang3.StringUtils.trimToEmpty(str);
    }

    /**
     * string url 地址转成 {@link URL}
     *
     * @param url url
     * @return {@link URL}
     * @throws IllegalArgumentException 如果转换失败，抛出此异常
     */
    public static URL toURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Failed to create url for : " + url);
        }
    }

    /**
     * String url 转换成 {@link UrlResource}
     *
     * @param url url
     * @return {@link UrlResource}
     * @throws IllegalArgumentException 如果转换失败，抛出此异常
     */
    public static UrlResource createResource(String url) {
        try {
            return new UrlResource(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Failed to create url for : " + url);
        }
    }

}
