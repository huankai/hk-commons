package com.hk.commons.http;

import java.io.IOException;

/**
 * Http 请求
 *
 * @author kevin
 */
public interface HttpExecutor<T, P> {

    /**
     * @param uri    请求的RUI
     * @param params 请求的参数
     * @return 返回Http请求的结果
     * @throws IOException 抛出IO异常
     */
    T execute(String uri, P params) throws IOException;

}
