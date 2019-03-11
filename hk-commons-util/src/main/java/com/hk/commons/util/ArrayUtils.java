package com.hk.commons.util;

import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 数组工具类
 *
 * @author kevin
 * @date 2017年9月1日下午1:36:28
 */
public abstract class ArrayUtils {

    /**
     * 不包含元素
     *
     * @param array        array
     * @param objectToFind objectToFind
     * @return true or false
     */
    public static boolean noContains(final Object[] array, final Object objectToFind) {
        return !contains(array, objectToFind);
    }

    /**
     * 数组转换为 Array List
     *
     * @param args args
     * @param <T>  T
     * @return ArrayList
     */
    @SafeVarargs
    public static <T> List<T> asArrayList(T... args) {
        return asList(ArrayList::new, args);
    }

    /**
     * 数组转换为 List
     *
     * @param supplier supplier
     * @param args     args
     * @param <T>      T
     * @param <C>      C
     * @return List
     */
    @SafeVarargs
    public static <T, C extends List<T>> List<T> asList(Supplier<C> supplier, T... args) {
        return isEmpty(args) ? supplier.get() : Arrays.stream(args).collect(Collectors.toCollection(supplier));
    }

    /**
     * 数组转换为 HasHSet
     *
     * @param args args
     * @param <T>  T
     * @return HashSet
     */
    @SafeVarargs
    public static <T> Set<T> asHashSet(T... args) {
        return asSet(HashSet::new, args);
    }

    /**
     * 数组转换为 LinkedHashSet
     *
     * @param args args
     * @param <T>  T
     * @return HashSet
     */
    @SafeVarargs
    public static <T> Set<T> asLinkedHashSet(T... args) {
        return asSet(LinkedHashSet::new, args);
    }

    /**
     * 数组转换为 Set
     *
     * @param supplier supplier
     * @param args     args
     * @param <T>      T
     * @param <C>      C
     * @return Set
     */
    @SafeVarargs
    public static <T, C extends Set<T>> Set<T> asSet(Supplier<C> supplier, T... args) {
        return isEmpty(args) ? supplier.get()
                : Arrays.stream(args).collect(Collectors.toCollection(supplier));
    }

    /**
     * 包含元素
     *
     * @param array        array
     * @param objectToFind objectToFind
     * @return true or false
     */
    public static boolean contains(final Object[] array, final Object objectToFind) {
        return org.apache.commons.lang3.ArrayUtils.contains(array, objectToFind);
    }

    /**
     * 获取数组的长度
     *
     * @param args args
     * @return length
     */
    public static int length(Object[] args) {
        return isEmpty(args) ? 0 : args.length;
    }

    /**
     * 获取数组中第一个元素，不存在返回 Null
     *
     * @param arr arr
     * @param <T> T
     * @return 第一个元素 or null
     */
    public static <T> T getFirstOrDefault(T[] arr) {
        return isEmpty(arr) ? null : arr[0];
    }

    /**
     * 包含元素
     *
     * @param array        array
     * @param objectToFind objectToFind
     * @return true or false
     */
    public static boolean contains(final int[] array, final int objectToFind) {
        return org.apache.commons.lang3.ArrayUtils.contains(array, objectToFind);
    }

    /**
     * 包含元素
     *
     * @param array        array
     * @param objectToFind objectToFind
     * @return true or false
     */
    public static boolean contains(final byte[] array, final byte objectToFind) {
        return org.apache.commons.lang3.ArrayUtils.contains(array, objectToFind);
    }

    /**
     * 包含元素
     *
     * @param array        array
     * @param objectToFind objectToFind
     * @return true or false
     */
    public static boolean contains(final long[] array, final long objectToFind) {
        return org.apache.commons.lang3.ArrayUtils.contains(array, objectToFind);
    }

    /**
     * 数组是否为空
     *
     * @param array array
     * @return true or false
     */
    public static boolean isEmpty(Object[] array) {
        return ObjectUtils.isEmpty(array);
    }

    /**
     * 数组是否为空
     *
     * @param array array
     * @return true or false
     */
    public static boolean isEmpty(byte[] array) {
        return org.apache.commons.lang3.ArrayUtils.isEmpty(array);
    }

    /**
     * 数组是否不为空
     *
     * @param array array
     * @return true or false
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组是否不为空
     *
     * @param bytes bytes
     * @return true or false
     */
    public static boolean isNotEmpty(byte[] bytes) {
        return !isEmpty(bytes);
    }
}
