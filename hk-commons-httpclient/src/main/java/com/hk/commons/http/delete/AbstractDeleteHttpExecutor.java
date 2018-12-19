package com.hk.commons.http.delete;

import com.hk.commons.http.AbstractHttpExecutor;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URI;
import java.util.Map;


/**
 * Delete请求
 *
 * @param <T>
 * @author kevin
 */
public abstract class AbstractDeleteHttpExecutor<T> extends AbstractHttpExecutor<T, Map<String, Object>> {

    protected AbstractDeleteHttpExecutor(ResponseHandler<T> responseHandler) {
        super(responseHandler);
    }

    protected AbstractDeleteHttpExecutor(CloseableHttpClient httpClient, ResponseHandler<T> responseHandler) {
        super(httpClient, responseHandler);
    }

    @Override
    public T execute(String uri, Map<String, Object> params) throws IOException {
        return doExecute(buildHttpDelete(uri, params));
    }

    protected HttpDelete buildHttpDelete(String uri, Map<String, Object> params) {
        HttpDelete httpDelete = new HttpDelete();
        String uri_ = generateUri(uri, params);
        httpDelete.setURI(URI.create(uri_));
        return httpDelete;
    }

}
