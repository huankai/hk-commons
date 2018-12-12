package com.hk.commons.util;

import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.*;

/**
 * Key 小驼峰Key转换
 *
 * @author: kevin
 * @date: 2018-10-08 12:22
 * @see org.springframework.util.LinkedCaseInsensitiveMap
 */
@SuppressWarnings("serial")
public class LinkedHumpMap<V> implements Map<String, V>, Serializable, Cloneable {

    private final LinkedHashMap<String, V> targetMap;

    private final HashMap<String, String> caseInsensitiveKeys;

    private final Locale locale;


    /**
     * Create a new LinkedCaseInsensitiveMap that stores case-insensitive keys
     * according to the default Locale (by default in lower case).
     *
     * @see #convertKey(String)
     */
    public LinkedHumpMap() {
        this((Locale) null);
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that stores case-insensitive keys
     * according to the given Locale (by default in lower case).
     *
     * @param locale the Locale to use for case-insensitive key conversion
     * @see #convertKey(String)
     */
    public LinkedHumpMap(@Nullable Locale locale) {
        this(16, locale);
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that wraps a {@link LinkedHashMap}
     * with the given initial capacity and stores case-insensitive keys
     * according to the default Locale (by default in lower case).
     *
     * @param initialCapacity the initial capacity
     * @see #convertKey(String)
     */
    public LinkedHumpMap(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * Create a new LinkedCaseInsensitiveMap that wraps a {@link LinkedHashMap}
     * with the given initial capacity and stores case-insensitive keys
     * according to the given Locale (by default in lower case).
     *
     * @param initialCapacity the initial capacity
     * @param locale          the Locale to use for case-insensitive key conversion
     * @see #convertKey(String)
     */
    public LinkedHumpMap(int initialCapacity, @Nullable Locale locale) {
        this.targetMap = new LinkedHashMap<String, V>(initialCapacity) {
            @Override
            public boolean containsKey(Object key) {
                return LinkedHumpMap.this.containsKey(key);
            }

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, V> eldest) {
                boolean doRemove = LinkedHumpMap.this.removeEldestEntry(eldest);
                if (doRemove) {
                    caseInsensitiveKeys.remove(convertKey(eldest.getKey()));
                }
                return doRemove;
            }
        };
        this.caseInsensitiveKeys = new HashMap<>(initialCapacity);
        this.locale = (locale != null ? locale : Locale.getDefault());
    }

    /**
     * Copy constructor.
     */
    @SuppressWarnings("unchecked")
    private LinkedHumpMap(LinkedHumpMap<V> other) {
        this.targetMap = (LinkedHashMap<String, V>) other.targetMap.clone();
        this.caseInsensitiveKeys = (HashMap<String, String>) other.caseInsensitiveKeys.clone();
        this.locale = other.locale;
    }


    // Implementation of java.util.Map

    @Override
    public int size() {
        return this.targetMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.targetMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return (key instanceof String && this.caseInsensitiveKeys.containsKey(convertKey((String) key)));
    }

    @Override
    public boolean containsValue(Object value) {
        return this.targetMap.containsValue(value);
    }

    @Override
    @Nullable
    public V get(Object key) {
        if (key instanceof String) {
//            String caseInsensitiveKey = this.caseInsensitiveKeys.get(key);
//            if (caseInsensitiveKey != null) {
//                return this.targetMap.get(caseInsensitiveKey);
//            }
            return this.targetMap.get(key);
        }
        return null;
    }

    @Override
    @Nullable
    public V getOrDefault(Object key, V defaultValue) {
        V value = get(key);
        return value == null ? defaultValue : value;
    }

    @Override
    @Nullable
    public V put(String key, @Nullable V value) {
        String convertKey = convertKey(key);
        String oldKey = this.caseInsensitiveKeys.put(convertKey, key);
        if (oldKey != null && !oldKey.equals(key)) {
            this.targetMap.remove(oldKey);
        }
        return this.targetMap.put(convertKey, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends V> map) {
        if (CollectionUtils.isNotEmpty(map)) {
            map.forEach(this::put);
        }
    }

    @Override
    @Nullable
    public V remove(Object key) {
        if (key instanceof String) {
            String caseInsensitiveKey = this.caseInsensitiveKeys.remove(convertKey((String) key));
            if (caseInsensitiveKey != null) {
                return this.targetMap.remove(caseInsensitiveKey);
            }
        }
        return null;
    }

    @Override
    public void clear() {
        this.caseInsensitiveKeys.clear();
        this.targetMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.targetMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.targetMap.values();
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        return this.targetMap.entrySet();
    }

    @Override
    public LinkedHumpMap<V> clone() {
        return new LinkedHumpMap<>(this);
    }

    @Override
    public boolean equals(Object obj) {
        return this.targetMap.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.targetMap.hashCode();
    }

    @Override
    public String toString() {
        return this.targetMap.toString();
    }


    // Specific to LinkedCaseInsensitiveMap

    /**
     * Return the locale used by this {@code LinkedCaseInsensitiveMap}.
     * Used for case-insensitive key conversion.
     *
     * @see #LinkedHumpMap(Locale)
     * @see #convertKey(String)
     * @since 4.3.10
     */
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * Convert the given key to a case-insensitive key.
     * <p>The default implementation converts the key
     * to lower-case according to this Map's Locale.
     *
     * @param key the user-specified key
     * @return the key to use for storing
     * @see org.springframework.util.LinkedCaseInsensitiveMap#convertKey(String)
     */
    protected String convertKey(String key) {
        return StringUtils.lineToSmallHump(key);
    }

    /**
     * Determine whether this map should remove the given eldest entry.
     *
     * @param eldest the candidate entry
     * @return {@code true} for removing it, {@code false} for keeping it
     * @see LinkedHashMap#removeEldestEntry
     */
    protected boolean removeEldestEntry(Map.Entry<String, V> eldest) {
        return false;
    }

}
