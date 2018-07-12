package com.hk.commons.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 集合工具类
 *
 * @author kevin
 * @date 2017年9月1日下午1:31:18
 */
public abstract class CollectionUtils extends org.springframework.util.CollectionUtils {

    /**
     * 是否为一个空集合
     *
     * @param coll
     * @return
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 是否为一个空Map
     *
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 如果 list2中的元素不能空，添加list2的元素添加到 list1中
     *
     * @param list1
     * @param list2
     * @return
     */
    public static <T> boolean addAll(Collection<T> list1, Collection<T> list2) {
        return isNotEmpty(list2) && list1.addAll(list2);
    }

    public static boolean contains(Iterable<?> iterable, Object element) {
        if (null != iterable) {
            for (Object e : iterable) {
                if (ObjectUtils.nullSafeEquals(e, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Map根据Value 从小到大排序
     *
     * @param result
     * @return
     */
    public static <V extends Comparable<? super V>> Map<String, V> sortMapByValue(Map<String, V> result) {
        return sortMapByValue(result, false);
    }

    /**
     * <pre>
     * Map根据Value 排序
     * </pre>
     *
     * @param result
     * @param reversed 是否反转
     * @return
     */
    public static <V extends Comparable<? super V>> Map<String, V> sortMapByValue(Map<String, V> result,
                                                                                  boolean reversed) {
        if (isEmpty(result)) {
            return Collections.emptyMap();
        }
        Map<String, V> finalMap = new LinkedHashMap<>();
        result.entrySet().stream()
                .sorted(reversed ? Map.Entry.<String, V>comparingByValue().reversed()
                        : Map.Entry.comparingByValue())
                .forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        return finalMap;
    }

    /**
     * Map根据Key 排序
     *
     * @param result
     * @return
     */
    public static <K extends Comparable<? super K>> Map<K, Object> sortMapByKey(Map<K, Object> result) {
        return sortMapByKey(result, false);
    }

    /**
     * <pre>
     * Map根据Key 排序
     * </pre>
     *
     * @param result
     * @param reversed 是否反转
     * @return
     */
    public static <K extends Comparable<? super K>> Map<K, Object> sortMapByKey(Map<K, Object> result,
                                                                                boolean reversed) {
        if (isEmpty(result)) {
            return Collections.emptyMap();
        }
        Map<K, Object> finalMap = new LinkedHashMap<>();
        result.entrySet().stream()
                .sorted(reversed ? Map.Entry.<K, Object>comparingByKey().reversed()
                        : Map.Entry.comparingByKey())
                .forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        return finalMap;
    }

    /**
     * 过滤为空的可变数组元素，添加到指定集合
     *
     * @param list1
     * @param args
     * @return
     */
    @SafeVarargs
    public static <T> void addAllNotNull(Collection<T> list1, final T... args) {
        if (ArrayUtils.isNotEmpty(args)) {
            for (T t : args) {
                if (Objects.nonNull(t)) {
                    list1.add(t);
                }
            }
        }
    }

    /**
     * 获取集合第一个元素
     *
     * @param coll
     * @return
     */
    public static <T> T getFristOrDefault(Collection<T> coll) {
        return isEmpty(coll) ? null : coll.iterator().next();
    }

    /**
     * 多个Map的值合并成一个map
     *
     * @param values
     * @return
     */
    @SafeVarargs
    public static Map<String, Integer> addOrMergeIntegerValues(Map<String, Integer>... values) {
        if (ArrayUtils.isEmpty(values)) {
            return Collections.emptyMap();
        }
        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Integer> map : values) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                Integer value = result.get(entry.getKey());
                if (null != value) {
                    value = value + entry.getValue();
                } else {
                    value = entry.getValue();
                }
                result.put(entry.getKey(), value);
            }
        }
        return result;
    }

    /**
     * 多个Map的值合并成一个map
     *
     * @param values
     * @return
     */
    @SafeVarargs
    public static Map<String, Double> addOrMergeDoubleValues(Map<String, Double>... values) {
        if (ArrayUtils.isEmpty(values)) {
            return Collections.emptyMap();
        }
        Map<String, Double> result = new HashMap<>();
        for (Map<String, Double> map : values) {
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                Double value = result.get(entry.getKey());
                if (null != value) {
                    value = value + entry.getValue();
                } else {
                    value = entry.getValue();
                }
                result.put(entry.getKey(), value);
            }
        }
        return result;
    }

    /**
     * 多个Map的值合并成一个map
     *
     * @param values
     * @return
     */
    @SafeVarargs
    public static Map<String, Long> addOrMergeLongValues(Map<String, Long>... values) {
        if (ArrayUtils.isEmpty(values)) {
            return Collections.emptyMap();
        }
        Map<String, Long> result = new HashMap<>();
        for (Map<String, Long> map : values) {
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                Long value = result.get(entry.getKey());
                if (null != value) {
                    value = value + entry.getValue();
                } else {
                    value = entry.getValue();
                }
                result.put(entry.getKey(), value);
            }
        }
        return result;
    }

    /**
     * 多个Map的值合并成一个map
     *
     * @param values
     * @return
     */
    @SafeVarargs
    public static Map<String, BigDecimal> addOrMergeBigDecimalValues(Map<String, BigDecimal>... values) {
        if (ArrayUtils.isEmpty(values)) {
            return Collections.emptyMap();
        }
        Map<String, BigDecimal> result = new HashMap<>();
        for (Map<String, BigDecimal> map : values) {
            for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
                BigDecimal value = result.get(entry.getKey());
                if (null != value) {
                    value = value.add(entry.getValue());
                } else {
                    value = entry.getValue();
                }
                result.put(entry.getKey(), value);
            }
        }
        return result;
    }

    /**
     * 多个Map的值合并成一个map
     *
     * @param values
     * @return
     */
    @SafeVarargs
    public static Map<String, BigInteger> addOrMergeBigIntegerValues(Map<String, BigInteger>... values) {
        if (ArrayUtils.isEmpty(values)) {
            return Collections.emptyMap();
        }
        Map<String, BigInteger> result = new HashMap<>();
        for (Map<String, BigInteger> map : values) {
            for (Map.Entry<String, BigInteger> entry : map.entrySet()) {
                BigInteger value = result.get(entry.getKey());
                if (null != value) {
                    value = value.add(entry.getValue());
                } else {
                    value = entry.getValue();
                }
                result.put(entry.getKey(), value);
            }
        }
        return result;
    }


}
