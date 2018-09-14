package com.hk.commons.util;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * 数组工具类
 *
 * @author: kevin
 * @date: 2017年9月1日下午1:36:28
 */
public abstract class ArrayUtils {

    /**
     * 不包含
     *
     * @param array
     * @param objectToFind
     * @return
     */
    public static boolean noContains(final Object[] array, final Object objectToFind) {
        return !contains(array, objectToFind);
    }

    @SafeVarargs
    public static <T> Collection<T> asList(T... args) {
        return null == args ? Collections.emptyList() : Arrays.asList(args);
    }

    /**
     * 包含
     *
     * @param array
     * @param objectToFind
     * @return
     */
    public static boolean contains(final Object[] array, final Object objectToFind) {
        return org.apache.commons.lang3.ArrayUtils.contains(array, objectToFind);
    }

    /**
     * 包含
     *
     * @param array
     * @param objectToFind
     * @return
     */
    public static boolean contains(final int[] array, final int objectToFind) {
        return org.apache.commons.lang3.ArrayUtils.contains(array, objectToFind);
    }

    /**
     * 包含
     *
     * @param array
     * @param objectToFind
     * @return
     */
    public static boolean contains(final byte[] array, final byte objectToFind) {
        return org.apache.commons.lang3.ArrayUtils.contains(array, objectToFind);
    }

    /**
     * 包含
     *
     * @param array
     * @param objectToFind
     * @return
     */
    public static boolean contains(final long[] array, final long objectToFind) {
        return org.apache.commons.lang3.ArrayUtils.contains(array, objectToFind);
    }


    /**
     * 数组是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        return ObjectUtils.isEmpty(array);
    }

    /**
     * 数组是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(byte[] array) {
        return org.apache.commons.lang3.ArrayUtils.isEmpty(array);
    }

    /**
     * 数组是否不为空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }


}
