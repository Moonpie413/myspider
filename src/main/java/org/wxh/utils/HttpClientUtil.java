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

    public static List<Header> defaultHeaders;
    private static Logger logger = Logger.getLogger(HttpClientUtil.class);

    static {
        defaultHeaders = new ArrayList<>();
        Properties prop = null;
        try {
            prop = PropUtil.getProperties("header");
            Header userAgent = new BasicHeader("User-Agent", prop.getProperty("header.User-Agent"));
            Header accept = new BasicHeader("Accept", prop.getProperty("header.Accept"));
            Header acceptEncoding = new BasicHeader("Accept-Encoding", prop.getProperty(prop.getProperty("header.Accept-Encoding")));
            Header acceptLanguage = new BasicHeader("Accept-Language", prop.getProperty("header.Accept-Language"));
            defaultHeaders.add(userAgent);
            defaultHeaders.add(accept);
            defaultHeaders.add(acceptEncoding);
            defaultHeaders.add(acceptLanguage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (prop != null) {
                prop.clear();
            }
        }
    }
    private HttpClientBuilder clientBuilder;

    private HttpClientUtil() {}

    public static HttpClientUtil getInstance() {
        HttpClientUtil util = new HttpClientUtil();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(20);
        util.clientBuilder = HttpClientBuilder.create();
        util.clientBuilder.setConnectionManager(connManager);
        return util;
    }

    public static HttpClient getDefaultHttpClient() {
        return HttpClientUtil.getInstance().clientBuilder.build();
    }

    public HttpResponse httpGet(String url, List<Header> headers) throws IOException {

        logger.info("发起httpGet: " + url + "...");
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
