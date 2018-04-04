package com.hk.commons.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author huangkai
 * @date 2017-11-24 17:00
 */
public abstract class NumberUtils extends org.springframework.util.NumberUtils {

    /**
     * <pre>
     *  比较两个Number是否相等
     *
     *  如果都为null ，返回true,
     *  如果只有任意一个为null ，返回false,
     *  如果参数类型不一样，返回 false,
     *  如果值不一样，返回false
     *  NumberUtils.equals(null,null); return true
     *  NumberUtils.equals(null,1); return false
     *  NumberUtils.equals(1,1L); return false
     *  NumberUtils.equals(1.0,1.0); return true
     *  NumberUtils.equals(1.0,2); return false
     * </pre>
     *
     * @param n1
     * @param n2
     * @return
     */
    public static boolean equals(Number n1, Number n2) {
        if (null == n1 && null == n2) {
            return true;
        }
        if (!n1.getClass().equals(n2.getClass())) {
            return false;
        }
        if (n1 instanceof Byte) {
            return n1.byteValue() == n2.byteValue();
        }
        if (n1 instanceof Short) {
            return n1.shortValue() == n2.shortValue();
        }
        if (n1 instanceof Integer || n1 instanceof BigInteger) {
            return n1.intValue() == n2.intValue();
        }
        if (n1 instanceof Long) {
            return n1.longValue() == n2.longValue();
        }
        if (n1 instanceof Float) {
            return n1.floatValue() == n2.floatValue();
        }
        return (n1 instanceof Double || n1 instanceof BigDecimal) && n1.doubleValue() == n2.doubleValue();
    }

    /**
     * 比较两个Number是否不相等
     *
     * @param n1
     * @param n2
     * @return
     */
    public static boolean nequals(Number n1, Number n2) {
        return !equals(n1, n2);
    }

}
