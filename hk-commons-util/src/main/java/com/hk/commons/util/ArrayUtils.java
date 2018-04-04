package com.hk.commons.util;

/**
 * 数组工具类
 *
 * @author huangkai
 * @date 2017年9月1日下午1:36:28
 */
public abstract class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

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


}
