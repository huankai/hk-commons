package com.hk.commons.http.post;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import com.hk.commons.http.AbstractHttpExecutor;

/**
 * Post请求
 * @author kevin
 *
 * @param <T>
 * @param <P>
 */
public abstract class AbstractPostHttpExecutor<T, P> extends AbstractHttpExecutor<T, P> {
	
	public AbstractPostHttpExecutor(ResponseHandler<T> responseHandler) {
		super(responseHandler);
	}
	
	public AbstractPostHttpExecutor(CloseableHttpClient httpClient, ResponseHandler<T> responseHandler) {
		super(httpClient, responseHandler);
	}

	public T execute(String uri, P params) throws IOException {
		HttpPost httpPost = new HttpPost();
		httpPost.setEntity(generateEntity(params));
		httpPost.setHeaders(generateHeaders());
		httpPost.setURI(URI.create(uri));
		return doExecute(httpPost);
	}

	public abstract HttpEntity generateEntity(P params);

}
