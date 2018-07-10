package com.hk.commons.http.get;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import com.hk.commons.http.AbstractHttpExecutor;


/**
 * Get请求
 * @author: kevin
 *
 * @param <T>
 */
public abstract class AbstractGetHttpExecutor<T> extends AbstractHttpExecutor<T, Map<String, Object>> {

	protected AbstractGetHttpExecutor(ResponseHandler<T> responseHandler) {
		super(responseHandler);
	}
	
	protected AbstractGetHttpExecutor(CloseableHttpClient httpClient, ResponseHandler<T> responseHandler) {
		super(httpClient, responseHandler);
	}
	
	@Override
	public T execute(String uri, Map<String, Object> params) throws IOException {
		HttpGet get = buildHttpGet(uri, params);
		return doExecute(get);
	}

	/**
	 * 创建一个HttpGet 
	 * @param uri
	 * @param params
	 * @return
	 */
	protected HttpGet buildHttpGet(String uri, Map<String, Object> params) {
		HttpGet get = new HttpGet();
		String uri_ = generateUri(uri, params);
		get.setHeaders(generateHeaders());
		get.setURI(URI.create(uri_));
		return get;
	}
	
}
