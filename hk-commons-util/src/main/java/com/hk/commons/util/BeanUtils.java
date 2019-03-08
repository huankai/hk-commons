package com.hk.commons.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 与Bean相关的工具类
 *
 * @author kevin
 * @date 2017年9月12日上午10:01:39
 */
public abstract class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * Map To Bean
     *
     * @param map   map
     * @param clazz clazz
     * @param <T>   T
     * @return T
     */
    public static <T> T mapToBean(Map<String, ?> map, Class<T> clazz) {
        BeanWrapper beanWrapper = BeanWrapperUtils.createBeanWrapper(clazz);
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            if (beanWrapper.isWritableProperty(entry.getKey())) {
                beanWrapper.setPropertyValue(entry.getKey(), entry.getValue());
            }
        }
        return clazz.cast(beanWrapper.getWrappedInstance());
    }

    /**
     * Bean to Map
     *
     * @param obj              obj
     * @param ignoreProperties ignoreProperties
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object obj, String... ignoreProperties) {
        return beanToMap(obj, false, ignoreProperties);
    }

    /**
     * Bean to Map
     *
     * @param obj               obj
     * @param containsNullValue containsNullValue
     * @return Map
     */
    public static Map<String, Object> beanToMap(Object obj, boolean containsNullValue, String... ignoreProperties) {
        Map<String, Object> result = new HashMap<>();
        if (null != obj) {
            BeanWrapper beanWrapper = BeanWrapperUtils.createBeanWrapper(obj);
            for (PropertyDescriptor descriptor : beanWrapper.getPropertyDescriptors()) {
                String name = descriptor.getName();
                Object value = beanWrapper.getPropertyValue(name);
                if ((value != null || containsNullValue) && ArrayUtils.noContains(ignoreProperties, name)) {
                    result.put(name, value);
                }
            }
        }
        return result;
    }

    /**
     * 复制不为空的属性
     *
     * @param source source
     * @param target target
     */
    public static void copyNotNullProperties(Object source, Object target) {
        PropertyDescriptor[] targetDescriptors = getPropertyDescriptors(target.getClass());
        for (PropertyDescriptor targetDescriptor : targetDescriptors) {
            Method writeMethod = targetDescriptor.getWriteMethod();
            if (writeMethod != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetDescriptor.getName());
                if (null != sourcePd) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (ObjectUtils.isNotEmpty(value)) {
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetDescriptor.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }

    }

}
