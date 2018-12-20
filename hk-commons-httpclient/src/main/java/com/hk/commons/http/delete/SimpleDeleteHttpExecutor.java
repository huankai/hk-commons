package com.hk.commons.http.delete;

import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;


/**
 * Delete
 * @author kevin
 * @date 2017年9月28日上午9:39:20
 */
public class SimpleDeleteHttpExecutor extends AbstractDeleteHttpExecutor<String> {

	/**
	 * 使用默认
	 * 
	 */
	public SimpleDeleteHttpExecutor() {
		super(BASIC_HANDLER);
	}
	
	/**
	 * 自定义一个 CloseableHttpClient
	 * @param httpClient
	 */
	public SimpleDeleteHttpExecutor(CloseableHttpClient httpClient) {
		super(httpClient, BASIC_HANDLER);
	}
	
	/**
	 * 自定义一个 CloseableHttpClient 和  ResponseHandler
	 * @param httpClient
	 * @param handler
	 */
	public SimpleDeleteHttpExecutor(CloseableHttpClient httpClient,ResponseHandler<String> handler) {
		super(httpClient, handler);
	}

}
