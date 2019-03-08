package com.hk.commons.util;

import org.springframework.beans.BeanWrapper;

/**
 * 属性工具类
 *
 * @author kevin
 * @date 2017年9月1日下午1:31:52
 */
public abstract class PropertyUtils {

    /**
     * 获取属性类型，包括父类属性
     *
     * @param beanClazz    beanClazz
     * @param propertyName propertyName
     *                     属性名称
     * @return
     * @throws IllegalArgumentException
     */
    public static Class<?> getPropertyType(final Class<?> beanClazz, final String propertyName) {
        return FieldUtils.findField(beanClazz, propertyName).getType();
    }

    /**
     * 获取属性名称值
     *
     * @param bean         bean
     * @param propertyName propertyName
     * @param clazz        属性值类型
     * @return bean 属性值
     */
    public static <T> T getPropertyValue(Object bean, String propertyName, Class<T> clazz) {
        BeanWrapper beanWrapper = BeanWrapperUtils.createBeanWrapper(bean);
        return clazz.cast(beanWrapper.getPropertyValue(propertyName));
    }

    /**
     * 设置属性值
     *
     * @param bean         bean
     * @param propertyName propertyName
     * @param value        value
     */
    public static void setPropertyValue(final Object bean, String propertyName, Object value) {
        BeanWrapper beanWrapper = BeanWrapperUtils.createBeanWrapper(bean);
        beanWrapper.setPropertyValue(propertyName, value);
    }

}
