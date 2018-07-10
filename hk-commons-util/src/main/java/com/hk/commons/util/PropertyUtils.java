package com.hk.commons.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * 属性工具类
 *
 * @author: kevin
 * @date 2017年9月1日下午1:31:52
 */
public abstract class PropertyUtils {

	/**
	 * 获取属性类型，包括父类属性
	 *
	 * @param beanClazz
	 * @param propertyName
	 *            属性名称
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Class<?> getPropertyType(final Class<?> beanClazz, final String propertyName) {
		return FieldUtils.findField(beanClazz, propertyName).getType();
	}

	/**
	 * 获取属性名称值
	 *
	 * @param bean
	 * @param propertyName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getPropertyValue(Object bean, String propertyName) {
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
		return (T) wrapper.getPropertyValue(propertyName);
	}

	/**
	 * 设置属性值
	 *
	 * @param bean
	 * @param propertyName
	 * @param value
	 */
	public static void setPropertyValue(final Object bean, String propertyName, Object value) {
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
		wrapper.setPropertyValue(propertyName, value);
	}

}
