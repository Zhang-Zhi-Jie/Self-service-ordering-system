package com.example.cook;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubin on 2016/4/29.
 */
class HttpClientCollector {
    public static List<HttpClient> httpClients = new ArrayList<>();

    public static HttpClient getHttpClient() {
        if (!httpClients.isEmpty()) {
            return httpClients.get(0);
        } else
            return null;
    }
    public static boolean isSetHttpClient() {
        if (!httpClients.isEmpty()) {
            return true;
        }
        else
            return false;
    }
    public static boolean setHttpClient(HttpClient httpClient) {
        if (httpClients.isEmpty()) {
            httpClients.add(httpClient);
            return true;
        } else
            return false;
    }
    }

public class HttpUtils {
    //function:发送Post请求到服务器
    public static HttpClient httpClient;

    public static String submitPostData(String strUrlPath, List<NameValuePair> params, String encode) throws IOException {
        HttpPost post = new HttpPost(strUrlPath);
        post.setEntity(new UrlEncodedFormEntity( params, HTTP.UTF_8));
        if (HttpClientCollector.isSetHttpClient()) {
            httpClient = HttpClientCollector.getHttpClient();
        } else {
            httpClient = new DefaultHttpClient();
            HttpClientCollector.setHttpClient(httpClient);
        }
        HttpResponse httpResponse = httpClient.execute(post);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            String msg = EntityUtils.toString(httpResponse.getEntity());
            return msg;
        } else
            return "-1";
    }

    public static String submitGetData(String strUrlPath, String encode) throws IOException {
        HttpGet get = new HttpGet(strUrlPath);
        if (HttpClientCollector.isSetHttpClient()) {
            httpClient = HttpClientCollector.getHttpClient();
        } else {
            httpClient = new DefaultHttpClient();
            HttpClientCollector.setHttpClient(httpClient);
        }
        HttpResponse httpResponse = httpClient.execute(get);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            String msg = EntityUtils.toString(httpResponse.getEntity());
            return msg;
        } else
            return "-1";
    }
}
