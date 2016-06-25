package me.ixfan.wechatkit.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.commons.codec.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Http utility functions.
 *
 * Created by xfan on 16/6/25.
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private HttpClientUtil() {
        throw new AssertionError();
    }

    /**
     * Send HTTP GET request and get JSON response.
     * @param url URL of request.
     * @return JSON object of response.
     * @throws IOException
     */
    public static JsonObject sendGetRequestAndGetJsonResponse(String url) throws IOException {
        return sendRequestAndGetJsonResponse(url, HttpGet.METHOD_NAME);
    }

    /**
     * Send HTTP POST request and get JSON response.
     * @param url URL of request.
     * @param params Param data to post.
     * @return JSON object of response.
     * @throws IOException
     */
    public static JsonObject sendPostRequestAndGetJsonResponse(String url, Map<String, String> params)
            throws IOException {
        List<NameValuePair> nameValuePairs = params.keySet().stream().map(key -> new BasicNameValuePair(key, params.get(key))).collect(Collectors.toList());
        return sendRequestAndGetJsonResponse(
                url,
                HttpPost.METHOD_NAME,
                nameValuePairs.toArray(new NameValuePair[nameValuePairs.size()]));
    }

    /**
     * Send HTTP request with specified HTTP method.
     * If method is not specified, GET method will be used.
     * @param url URL of request.
     * @param method HTTP method used.
     * @param nameValuePairs name/value pairs parameter used as an element of HTTP messages.
     * @return JSON object of response.
     * @throws IOException
     */
    private static JsonObject sendRequestAndGetJsonResponse(String url, String method, NameValuePair... nameValuePairs)
            throws IOException {
        String jsonStr = sendRequestAndGetStringResponse(url, method, nameValuePairs);
        try {
            return new JsonParser().parse(jsonStr).getAsJsonObject();
        } catch (JsonParseException e) {
            logger.error("Exception occurs during parsing of a Json string, maybe the specified text is not valid JSON.");
            throw e;
        }
    }

    /**
     * Send HTTP request with specified HTTP method.
     * If method is not specified, GET method will be used.
     * @param url URL of request.
     * @param method HTTP method used.
     * @param nameValuePairs name/value pairs parameter used as an element of HTTP messages.
     * @return String response.
     * @throws IOException
     */
    private static String sendRequestAndGetStringResponse(String url, String method, NameValuePair... nameValuePairs)
            throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpUriRequest request;
        switch (method) {
            case HttpPost.METHOD_NAME:
                request = RequestBuilder.post(url)
                        .setCharset(Charsets.UTF_8)
                        .setEntity(new UrlEncodedFormEntity(Arrays.asList(nameValuePairs)))
                        .build();
                break;
            default:
                request = RequestBuilder.get(url).setCharset(Charsets.UTF_8).build();
                break;
        }

        return httpClient.execute(request, new BasicResponseHandler());
    }

}
