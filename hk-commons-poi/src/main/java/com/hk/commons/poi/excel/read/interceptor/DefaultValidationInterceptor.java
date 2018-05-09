/**
 *
 */
package com.hk.commons.poi.excel.read.interceptor;

import com.hk.commons.poi.excel.model.ReadResult;

/**
 * 默认验证拦截器
 *
 * @author huangkai
 */
public class DefaultValidationInterceptor<T> implements ValidationInterceptor<T> {

    @Override
    public void preValidate(ReadResult<T> result) {

    }

    @Override
    public void afterValidate(T t) {

    }

    @Override
    public void afterComplate(ReadResult<T> result) {

    }

}
