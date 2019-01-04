package com.hk.commons.util;

/**
 * Assert utils
 *
 * @author kevin
 * @date 2017年8月31日上午10:09:14
 */
public abstract class AssertUtils {

    /**
     * Check the args is not blank
     *
     * @param code   validate String input
     * @param params 国际化消息参数
     */
    public static void notBlank(String args, Object... params) {
        notBlankWithDefault(args, "validate.args.notBlank.message", params);
    }

    /**
     * Check the args is not blank
     *
     * @param args        validate String input
     * @param messageCode Custom validation is not through the error message
     * @param params      国际化消息参数
     */
    public static void notBlankWithDefault(String code, String messageCode, Object... params) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException(SpringContextHolder.getMessageWithDefault(messageCode, messageCode, params));
        }
    }

    public static void notEmpty(CharSequence args) {
        if (StringUtils.isEmpty(args)) {
            throw new IllegalArgumentException("null");
        }
    }

    /**
     * 检查参数是否为空
     *
     * @param args      args
     * @param paramName 国际化消息参数
     */
    public static void notEmpty(CharSequence args, Object paramName) {
        notEmptyWithDefault(args, "validate.args.notEmpty.message", paramName);
    }

    /**
     * 检查参数是否为空
     *
     * @param args        args
     * @param messageCode 国际化消息Code
     * @param params      国际化消息参数
     */
    public static void notEmptyWithDefault(CharSequence args, String messageCode, Object... paramName) {
        if (StringUtils.isEmpty(args)) {
            throw new IllegalArgumentException(SpringContextHolder.getMessageWithDefault(messageCode, messageCode, paramName));
        }
    }

    /**
     * 检查表达式是否为 true
     *
     * @param expression  表达式
     * @param messageCode 国际化消息Code
     * @param params      国际化消息参数
     */
    public static void isTrue(boolean expression, String messageCode, Object... params) {
        if (!expression) {
            throw new IllegalArgumentException(SpringContextHolder.getMessageWithDefault(messageCode, messageCode, params));
        }
    }

}
