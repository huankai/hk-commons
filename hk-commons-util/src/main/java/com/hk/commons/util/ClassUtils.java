package com.hk.commons.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author: huangkai
 * @date 2018-04-19 09:00
 */
public abstract class ClassUtils extends org.springframework.util.ClassUtils {

    public static Class<?> getGenericTypeByType(ParameterizedType genType, int index) {
        Type[] params = genType.getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return null;
        }
        Object res = params[index];
        if (res instanceof Class) {
            return (Class) res;
        }
        if (res instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) res).getRawType();
        }
        return null;
    }

    /**
     * 获取一个类的泛型类型,如果未获取到返回Object.class
     *
     * @param clazz 要获取的类
     * @param index 泛型索引
     * @return 泛型
     */
    public static Class<?> getGenericType(Class<?> clazz, int index) {
        List<Type> arrys = new ArrayList<>();
        arrys.add(clazz.getGenericSuperclass());
        arrys.addAll(Arrays.asList(clazz.getGenericInterfaces()));
        return arrys.stream()
                .filter(Objects::nonNull)
                .map(type -> {
                    if (clazz != Object.class && !(type instanceof ParameterizedType)) {
                        return getGenericType(clazz.getSuperclass(), index);
                    }
                    return getGenericTypeByType(((ParameterizedType) type), index);
                })
                .filter(Objects::nonNull)
                .filter(res -> res != Object.class)
                .findFirst()
                .orElse(Object.class);
    }

}
