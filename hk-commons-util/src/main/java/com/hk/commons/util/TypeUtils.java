/**
 * 
 */
package com.hk.commons.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

/**
 * @author: kevin
 *
 */
@SuppressWarnings("restriction")
public abstract class TypeUtils {
	
	/**
	 * 获取为泛型属性的泛型类型
	 * @param beanClass
	 * @param propertyName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getParameterizedTypeClass(Class<?> beanClass,String propertyName) {
		Field field = FieldUtils.findField(beanClass, propertyName);
		Type type = field.getGenericType();
		if(type.getClass() == Class.class) {
			return (Class<T>) type;
		}
		ParameterizedTypeImpl typeImple = (ParameterizedTypeImpl) type;
		Type[] types = typeImple.getActualTypeArguments();
		try {
			return types.length == 0 ? null : (Class<T>) Class.forName(types[0].getTypeName());
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

}
