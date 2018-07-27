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
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        BeanWrapper beanWrapper = BeanWrapperUtils.createBeanWrapper(clazz);
        map.forEach(beanWrapper::setPropertyValue);
        return (T) beanWrapper.getWrappedInstance();
    }

    /**
     * Bean to Map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) {
        return beanToMap(obj, false);
    }

    /**
     * @param obj
     * @param containsNullValue
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj, boolean containsNullValue) {
        BeanWrapper beanWrapper = BeanWrapperUtils.createBeanWrapper(obj);
        Map<String, Object> result = new HashMap<>();
        for (PropertyDescriptor descriptor : beanWrapper.getPropertyDescriptors()) {
            Object value = beanWrapper.getPropertyValue(descriptor.getName());
            if (value != null || containsNullValue) {
                result.put(descriptor.getName(), value);
            }
        }
        return result;
    }

    /**
     * 复制不为空的属性
     *
     * @param source
     * @param target
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
