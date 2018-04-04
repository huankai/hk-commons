package com.hk.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hk.commons.util.EnumDisplayUtils;

/**
 * 
 * @author huangkai
 * @see EnumDisplayUtils
 * @date 2017年9月27日上午11:36:07
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumDisplay {

	/**
	 * value
	 * 
	 * @return
	 */
	String value();

	/**
	 * Order
	 * 
	 * @return
	 */
	int order() default 0;
}
