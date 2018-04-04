/**
 * 
 */
package com.hk.commons.poi.excel.read.interceptor;

import com.hk.commons.poi.excel.model.ReadResult;

/**
 * 默认验证拦截器
 * 
 * @author huangkai
 *
 */
public class DefaultValidationInterceptor<T> implements ValidationInterceptor<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.read.interceptor.ValidationInterceptor#preValidate(
	 * com.hk.commons.poi.excel.model.ReadResult)
	 */
	@Override
	public void preValidate(ReadResult<T> result) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.read.interceptor.ValidationInterceptor#afterValidate
	 * (java.lang.Object)
	 */
	@Override
	public void afterValidate(T t) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hk.commons.poi.excel.read.interceptor.ValidationInterceptor#afterComplate
	 * (com.hk.commons.poi.excel.model.ReadResult)
	 */
	@Override
	public void afterComplate(ReadResult<T> result) {

	}

}
