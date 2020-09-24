package com.nonce.restsecurity.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Andon
 * 2020/9/24
 */
public class HttpClientUtil {

    // 编码格式,发送编码格式统一用UTF-8
    private static final String ENCODING = "UTF-8";

    // 设置连接超时时间,单位毫秒
    private static final int CONNECT_TIMEOUT = 3 * 60 * 1000;

    // 请求响应超时时间,单位毫秒
    private static final int SOCKET_TIMEOUT = 3 * 60 * 1000;

    /**
     * 发送get请求;带请求头和请求参数
     */
    public static String doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建访问地址
        URIBuilder uriBuilder = new URIBuilder(url);
        if (!ObjectUtils.isEmpty(params)) {
            params.forEach(uriBuilder::setParameter);
        }

        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());

        // 设置请求超时时间及响应超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpGet.setConfig(requestConfig);

        // 设置请求头
        packageHeader(headers, httpGet);

        // 执行请求获取响应体并释放资源
        return getHttpClientResult(httpClient, httpGet);
    }

    /**
     * 发送get请求;带请求参数
     */
    public static String doGet(String url, Map<String, String> params) throws Exception {
        return doGet(url, null, params);
    }

    /**
     * 发送get请求;不带请求头和请求参数
     */
    public static String doGet(String url) throws Exception {
        return doGet(url, null, null);
    }

    /**
     * 发送post请求;带请求头和请求参数
     */
    public static String doPost(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);

        // 设置请求超时时间及响应超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);

        // 设置请求头
        /*httpPost.setHeader("Cookie", "");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");*/
        // 设置请求头
        packageHeader(headers, httpPost);

        // 封装请求参数
        packageParam(params, httpPost);

        // 执行请求获取响应体并释放资源
        return getHttpClientResult(httpClient, httpPost);
    }

    /**
     * 发送post请求;带请求参数
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        return doPost(url, null, params);
    }

    /**
     * 发送post请求;不带请求头和请求参数
     */
    public static String doPost(String url) throws IOException {
        return doPost(url, null, null);
    }

    /**
     * 发送post请求;json格式参数
     */
    public static String doPostJson(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);

        // 设置请求超时时间及响应超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);

        // 设置请求头
        /*httpPost.setHeader("Cookie", "");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");*/
        // 设置请求头
        packageHeader(headers, httpPost);

        // 封装请求参数为json格式
        packageParamJson(params, httpPost);

        // 执行请求获取响应体并释放资源
        return getHttpClientResult(httpClient, httpPost);
    }

    /**
     * 发送post请求;json格式参数
     */
    public static String doPostJson(String url, Map<String, String> params) throws IOException {
        return doPostJson(url, null, params);
    }

    /**
     * 发送put请求;带请求头和请求参数
     */
    public static String doPut(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPut.setConfig(requestConfig);
        packageHeader(headers, httpPut);
        packageParam(params, httpPut);
        return getHttpClientResult(httpClient, httpPut);
    }

    /**
     * 发送put请求;带请求参数
     */
    public static String doPut(String url, Map<String, String> params) throws IOException {
        return doPut(url, null, params);
    }

    /**
     * 发送put请求;不带请求头和请求参数
     */
    public static String doPut(String url) throws IOException {
        return doPut(url, null, null);
    }

    /**
     * 发送delete请求;带请求头
     */
    public static String doDelete(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(url);
        if (!ObjectUtils.isEmpty(params)) {
            params.forEach(uriBuilder::setParameter);
        }
        HttpDelete httpDelete = new HttpDelete(uriBuilder.build());
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpDelete.setConfig(requestConfig);
        packageHeader(headers, httpDelete);
        return getHttpClientResult(httpClient, httpDelete);
    }

    /**
     * 发送delete请求;带请求参数
     */
    public static String doDelete(String url, Map<String, String> params) throws Exception {
        return doDelete(url, null, params);
    }

    /**
     * 发送delete请求;不带请求头和请求参数
     */
    public static String doDelete(String url) throws Exception {
        return doDelete(url, null, null);
    }

    /**
     * 设置请求头
     */
    private static void packageHeader(Map<String, String> headers, HttpRequestBase httpMethod) {
        if (!ObjectUtils.isEmpty(headers)) {
            headers.forEach(httpMethod::setHeader);
        }
    }

    /**
     * 封装请求参数
     */
    private static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod) throws UnsupportedEncodingException {
        if (!ObjectUtils.isEmpty(params)) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            params.forEach((key, value) -> nameValuePairs.add(new BasicNameValuePair(key, value)));
            httpMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs, ENCODING));
        }
    }

    /**
     * 封装请求参数为json格式
     */
    private static void packageParamJson(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod) {
        if (!ObjectUtils.isEmpty(params)) {
            String json = JSONObject.toJSONString(params);
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpMethod.setEntity(stringEntity);
        }
    }

    /**
     * 执行请求获取响应体并释放资源
     */
    private static String getHttpClientResult(CloseableHttpClient httpClient, HttpRequestBase httpMethod) throws IOException {
        // 执行请求
        CloseableHttpResponse httpResponse = null;
        try {
            // 获取响应体
            httpResponse = httpClient.execute(httpMethod);
            String content = "";
            if (!ObjectUtils.isEmpty(httpResponse) && !ObjectUtils.isEmpty(httpResponse.getEntity())) {
                content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            }
            return content;
        } finally {
            // 释放资源
            if (!ObjectUtils.isEmpty(httpResponse)) {
                httpResponse.close();
            }
            if (!ObjectUtils.isEmpty(httpClient)) {
                httpClient.close();
            }
        }
    }
}
