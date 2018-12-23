package com.hk.commons.http.post;

import com.hk.commons.util.CollectionUtils;
import com.hk.commons.util.ConverterUtils;
import com.hk.commons.util.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * POST请求
 *
 * @author kevin
 * @date 2017年9月28日上午9:31:16
 */
public class SimplePostHttpExecutor extends AbstractPostHttpExecutor<String, Map<String, Object>> {

    public SimplePostHttpExecutor() {
        super(BASIC_HANDLER);
    }

    public SimplePostHttpExecutor(CloseableHttpClient httpClient, ResponseHandler<String> responseHandler) {
        super(httpClient, responseHandler);
    }

    @Override
    public HttpEntity generateEntity(Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(params)) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String value = ConverterUtils.defaultConvert(entry.getValue(), String.class);
                if (StringUtils.isNotEmpty(value)) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }
        return new UrlEncodedFormEntity(nvps, Consts.UTF_8);
    }

}
