package com.hk.commons.http.get;

import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Get请求
 * @author kevin
 * @date 2017年9月28日上午9:31:04
 */
public class SimpleGetHttpExecutor extends AbstractGetHttpExecutor<String> {
	
	/**
	 * 使用默认
	 * 
	 */
	public SimpleGetHttpExecutor() {
		super(BASIC_HANDLER);
	}
	
	/**
	 * 自定义一个 CloseableHttpClient
	 * @param httpClient
	 */
	public SimpleGetHttpExecutor(CloseableHttpClient httpClient) {
		super(httpClient, BASIC_HANDLER);
	}
	
	/**
	 * 自定义一个 CloseableHttpClient 和  ResponseHandler
	 * @param httpClient
	 * @param handler
	 */
	public SimpleGetHttpExecutor(CloseableHttpClient httpClient,ResponseHandler<String> handler) {
		super(httpClient, handler);
	}

}
