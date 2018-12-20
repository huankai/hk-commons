package com.hk.commons.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author kevin
 * @date 2017年09月18日上午9:16:49
 */
public class ThreadLocalUtils {

    private static final ThreadLocal<Map<String, Object>> localMap = ThreadLocal.withInitial(HashMap::new);

    /**
     * 添加一个值
     *
     * @param key
     * @param value
     * @return value
     */
    public static <T> T put(String key, T value) {
        localMap.get().put(key, value);
        return value;
    }

    /**
     * 删除参数对应的值
     *
     * @param key key
     * @see Map#remove(Object)
     */
    public static void remove(String key) {
        localMap.get().remove(key);
    }

    /**
     * 清空ThreadLocal
     *
     * @see Map#clear()
     */
    public static void clear() {
        localMap.remove();
    }

    /**
     * 从ThreadLocal中获取值
     *
     * @param key 键
     * @param <T> 值泛型
     * @return 值, 不存在则返回null, 如果类型与泛型不一致, 可能抛出{@link ClassCastException}
     * @see Map#get(Object)
     * @see ClassCastException
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return ((T) localMap.get().get(key));
    }

    /**
     * 从ThreadLocal中获取值,并指定一个当值不存在的提供者
     *
     * @see Supplier
     * @since 3.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Supplier<T> supplierOnNull) {
        return (T) localMap.get().computeIfAbsent(key, k -> supplierOnNull.get());
    }

    /**
     * 获取一个值后然后删除掉
     *
     * @param key 键
     * @param <T> 值类型
     * @return 值, 不存在则返回null
     * @see this#get(String)
     * @see this#remove(String)
     */
    public static <T> T getAndRemove(String key) {
        try {
            return get(key);
        } finally {
            remove(key);
        }
    }

}
