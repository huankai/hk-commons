package com.hk.commons.util;

import java.util.Optional;

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
    public static void notBlankWithI18n(String args, Object... params) {
        notBlankWithI18n(args, "validate.args.notBlank.message", params);
    }

    /**
     * Check the args is not blank
     *
     * @param args        validate String input
     * @param messageCode Custom validation is not through the error message
     * @param params      国际化消息参数
     */
    public static void notBlankWithI18n(String code, String messageCode, Object... params) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException(SpringContextHolder.getMessageWithDefault(messageCode, messageCode, params));
        }
    }

    /**
     * 验证是否为空
     *
     * @param args args
     */
    public static void notEmpty(CharSequence args) {
        notEmpty(args, "the validate args is null");
    }

    /**
     * 验证是否为空
     *
     * @param args    args
     * @param message message
     */
    public static void notEmpty(CharSequence args, String message) {
        if (StringUtils.isEmpty(args)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * object is null
     *
     * @param object  object
     * @param message message
     */
    public static void notNull(Object object, String message) {
        if (object == null || (object instanceof Optional && !((Optional<?>) object).isPresent())) {
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * 检查表达式是否为 true
     *
     * @param expression  表达式
     * @param messageCode 国际化消息Code
     * @param params      国际化消息参数
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检查参数是否为空
     *
     * @param args      args
     * @param paramName 国际化消息参数
     */
    public static void notEmptyWithI18n(CharSequence args, Object paramName) {
        notEmptyWithI18n(args, "validate.args.notEmpty.message", paramName);
    }

    /**
     * 检查参数是否为空
     *
     * @param args        args
     * @param messageCode 国际化消息Code
     * @param params      国际化消息参数
     */
    public static void notEmptyWithI18n(CharSequence args, String messageCode, Object... paramName) {
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
    public static void isTrueWithI18n(boolean expression, String messageCode, Object... params) {
        if (!expression) {
            throw new IllegalArgumentException(SpringContextHolder.getMessageWithDefault(messageCode, messageCode, params));
        }
    }

}
