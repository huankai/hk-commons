package com.hk.commons.http.post;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.hk.commons.fastjson.JsonUtils;

/**
 * JsonPost请求
 * @author huangkai
 * @date 2017年9月28日上午9:31:24
 */
public class JsonPostHttpExecutor extends AbstractPostHttpExecutor<String,Object> {

	public JsonPostHttpExecutor() {
		super(BASIC_HANDLER);
	}
	
	public JsonPostHttpExecutor(CloseableHttpClient httpClient, ResponseHandler<String> responseHandler) {
		super(httpClient, responseHandler);
	}

	@Override
	public HttpEntity generateEntity(Object params) {
		return new StringEntity(JsonUtils.toJSONString(params), Consts.UTF_8);
	}
	
	@Override
	protected Header[] generateHeaders() {
		return new Header[]{
                new BasicHeader(HTTP.CONTENT_ENCODING, Consts.UTF_8.name()),
                new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
        };
	}

}
