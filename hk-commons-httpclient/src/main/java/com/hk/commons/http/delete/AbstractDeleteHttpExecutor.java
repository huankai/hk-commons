package com.hk.commons.http.delete;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;

import com.hk.commons.http.AbstractHttpExecutor;


/**
 * Delete请求
 * @author kevin
 *
 * @param <T>
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
		HttpDelete httpDelete = buildHttpDelete(uri, params);
		return doExecute(httpDelete);
	}

	protected HttpDelete buildHttpDelete(String uri, Map<String, Object> params) {
		HttpDelete httpDelete = new HttpDelete();
		String uri_ = generateUri(uri, params);
		httpDelete.setURI(URI.create(uri_));
		return httpDelete;
	}

}
