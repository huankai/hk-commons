package com.hk.commons.poi.excel.read.interceptor;

import com.hk.commons.poi.excel.model.ReadResult;

/**
 * 验证拦截器
 * 
 * @author huangk
 *
 */
public interface ValidationInterceptor<T> {

	/**
	 * 所有对象验证之前
	 * 
	 * @param result
	 */
	void preValidate(ReadResult<T> result);

	/**
	 * 每行数据验证之前,返回 true才会执行验证
	 * 
	 * @param t
	 * @return
	 */
	default boolean beforeValidate(T t) {
		return true;
	}

	/**
	 * 每行数据验证之后，不管验证是否通过，只要 beforeValidate()方法返回true
	 * 
	 * @param t
	 */
	void afterValidate(T t);

	/**
	 * 所有对象验证之后执行
	 * 
	 * @param t
	 */
	void afterComplate(ReadResult<T> result);

}
