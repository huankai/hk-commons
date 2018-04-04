package com.hk.commons.util;

import java.util.Objects;

/**
 * 对象工具类
 * 
 * @author huangkai
 * @date 2017年10月20日上午9:40:39
 */
public abstract class ObjectUtils extends org.springframework.util.ObjectUtils {

	/**
	 * 对象默认值
	 * 
	 * @param object
	 * @param defaultValue
	 * @return
	 */
	public static <T> T defaultIfNull(final T object, final T defaultValue) {
		return Objects.nonNull(object) ? object : defaultValue;
	}

	/**
	 * <pre>
	 * ObjectUtils.isBlank(null) ; true
	 * ObjectUtils.isBlank("") ; true
	 * ObjectUtils.isBlank("  ") ; true
	 * </pre>
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isBlank(Object object) {
		return StringUtils.isBlank(toString(object));
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * <pre>
	 * ObjectUtils.isBlank(null) ; false
	 * ObjectUtils.isBlank("") ; false
	 * ObjectUtils.isBlank("  ") ; false
	 * </pre>
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNotBlank(Object object) {
		return !isBlank(object);
	}

	/**
	 * 对象的 toString 表现形式
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return Objects.isNull(obj) ? null : obj.toString();
	}

}
