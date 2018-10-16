package com.hk.commons.util;

import java.util.Objects;

/**
 * 对象工具类
 *
 * @author: kevin
 * @date: 2017年10月20日上午9:40:39
 */
public abstract class ObjectUtils extends org.springframework.util.ObjectUtils {

    /**
     * 对象默认值
     *
     * @param object       object
     * @param defaultValue defaultValue
     * @return object
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return Objects.nonNull(object) ? object : defaultValue;
    }

    /**
     * <pre>
     * ObjectUtils.isBlank(null) ; true
     * ObjectUtils.isBlank("") ; true
     * ObjectUtils.isBlank("  ") ; true
     * </pre>
     *
     * @param object object
     * @return true if blank
     */
    public static boolean isBlank(Object object) {
        return StringUtils.isBlank(toString(object));
    }

    /**
     * @param obj obj
     * @return true if notEmpty
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * <pre>
     * ObjectUtils.isBlank(null) ; false
     * ObjectUtils.isBlank("") ; false
     * ObjectUtils.isBlank("  ") ; false
     * </pre>
     *
     * @param object object
     * @return true if not blank
     */
    public static boolean isNotBlank(Object object) {
        return !isBlank(object);
    }

    /**
     * 对象的 toString 表现形式
     *
     * @param obj obj
     * @return toString
     */
    public static String toString(Object obj) {
        return Objects.isNull(obj) ? null : obj.toString();
    }

}
