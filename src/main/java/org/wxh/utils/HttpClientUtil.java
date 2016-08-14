package org.wxh.utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.wxh.httphandle.HeaderBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by Maroon on 2016/8/7.
 * project: MySpider
 */
public class HttpClientUtil {

    private static Logger logger = Logger.getLogger(HttpClientUtil.class);
    private static Map<String, Object> resultMap = PropUtil.getPropMap("http");
    private HttpClientBuilder clientBuilder;
    private HttpClientUtil() {}

    public static HttpClientUtil getInstance() {
        HttpClientUtil util = null;
        util = new HttpClientUtil();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        int maxTotal = Integer.parseInt((String) resultMap.get("httpClient.maxTotal"));
        int defaultMaxPerRoute = Integer.parseInt((String) resultMap.get("httpClient.defaultMaxPerRoute"));
        connManager.setMaxTotal(maxTotal);
        connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        util.clientBuilder = HttpClientBuilder.create();
        util.clientBuilder.setConnectionManager(connManager);
        return util;
    }

    public static HttpClient getDefaultHttpClient() {
        return HttpClientUtil.getInstance().clientBuilder.build();
    }

    public HttpResponse httpGet(String url, List<Header> headers) throws IOException {
        logger.info("[" + Thread.currentThread().getName()+ "]" + " 发起httpGet: " + url + "...");
        HttpGet httpGet = new HttpGet(url);
        if (headers != null) {
        headers.forEach(httpGet::setHeader);
        }
        return getDefaultHttpClient().execute(httpGet);
    }

    public HttpResponse httpGet(String url, Header[] headers) throws IOException {
        logger.info("发起httpGet: " + url + "...");
        HttpGet httpGet = new HttpGet(url);
        if (headers != null) {
        httpGet.setHeaders(headers);
        }
        return getDefaultHttpClient().execute(httpGet);
    }

    public HttpResponse httpPost(String url, List<Header> headers, Map<String, Object> params) throws IOException {
        logger.info("发起httpPost: " + url + "...");
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            headers.forEach(httpPost::setHeader);
        }
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (params != null) {
            nameValuePairs.addAll(params.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(),
                    (String) entry.getValue())).collect(Collectors.toList()));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        return getDefaultHttpClient().execute(httpPost);
    }

}
