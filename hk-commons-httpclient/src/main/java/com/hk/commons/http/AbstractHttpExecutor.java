package com.hk.commons.http;

import com.hk.commons.util.CollectionUtils;
import com.hk.commons.util.ConverterUtils;
import com.hk.commons.util.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 请求接口抽象实现
 *
 * @author kevin
 */
public abstract class AbstractHttpExecutor<T, P> implements HttpExecutor<T, P> {

    /**
     * 默认响应处理器
     */
    protected static final BasicResponseHandler BASIC_HANDLER = new BasicResponseHandler();

    /**
     * 请求时间配置
     * Socket超时 :10 秒
     * 连接超时:    5秒
     * 连接请求超时: 5秒
     */
    private static final RequestConfig DEFAULT_REQUEST_CONFIG = RequestConfig.custom()
            .setSocketTimeout(10000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();
    /**
     * 定义默认 HttpClient
     */
    private static final CloseableHttpClient DEFAULT_HTTP_CLIENT =
            HttpClients
                    .custom()
                    .setDefaultRequestConfig(DEFAULT_REQUEST_CONFIG)
                    .build();

    /**
     * httpClient
     */
    private CloseableHttpClient httpClient = DEFAULT_HTTP_CLIENT;

    /**
     * 响应处理器
     */
    private ResponseHandler<T> responseHandler;

    public AbstractHttpExecutor(ResponseHandler<T> responseHandler) {
        this.responseHandler = responseHandler;
    }

    public AbstractHttpExecutor(CloseableHttpClient httpClient, ResponseHandler<T> responseHandler) {
        this.httpClient = httpClient;
        this.responseHandler = responseHandler;
    }

    protected T doExecute(HttpUriRequest httpMethod) throws IOException {
        return getHttpClient().execute(httpMethod, responseHandler);
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 生成 Http 请求头
     * Content-Encoding : UTF-8
     *
     * @return
     */
    protected Header[] generateHeaders() {
        return new Header[]{new BasicHeader(HttpHeaders.CONTENT_ENCODING, Consts.UTF_8.name())};
    }

    /**
     * 生成url
     *
     * @param uri    请求URI
     * @param params 请求参数
     * @return url?params1[key]=params1[value]&params2[key]=params2[value]
     */
    protected String generateUri(String uri, Map<String, Object> params) {
        StringBuilder s = new StringBuilder(uri);
        if (CollectionUtils.isNotEmpty(params)) {
            List<NameValuePair> nvps = new ArrayList<>();
            params.forEach((key, value) -> {
                String convertValue = ConverterUtils.defaultConvert(value, String.class);
                if (StringUtils.isNotEmpty(convertValue)) {
                    nvps.add(new BasicNameValuePair(key, convertValue));
                }
            });
            if (!nvps.isEmpty()) {
                if (!StringUtils.endsWith(uri, "?")) {
                    s.append("?");
                }
                s.append(URLEncodedUtils.format(nvps, Consts.UTF_8));
            }
        }
        return s.toString();
    }
}
