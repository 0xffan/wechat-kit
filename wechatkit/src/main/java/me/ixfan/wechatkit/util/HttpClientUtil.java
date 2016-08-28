/*
 * MIT License
 *
 * Copyright (c) 2016 Warren Fan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
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
     * @throws IOException If I/O error occurs.
     */
    public static JsonObject sendGetRequestAndGetJsonResponse(String url) throws IOException {
        return sendRequestAndGetJsonResponse(url, HttpGet.METHOD_NAME);
    }

    /**
     * Send HTTP POST request and get JSON response.
     * @param url URL of request.
     * @param params Param data to post.
     * @return JSON object of response.
     * @throws IOException If I/O error occurs.
     */
    public static JsonObject sendPostRequestAndGetJsonResponse(String url, Map<String, String> params)
            throws IOException {
        List<NameValuePair> nameValuePairs = params.keySet()
                                                   .stream()
                                                   .map(key -> new BasicNameValuePair(key, params.get(key)))
                                                   .collect(Collectors.toList());
        return sendRequestAndGetJsonResponse(
                url,
                HttpPost.METHOD_NAME,
                nameValuePairs.toArray(new NameValuePair[nameValuePairs.size()]));
    }

    /**
     * Send HTTP POST request with body of JSON data.
     * @param url URL of request.
     * @param jsonBody Body data in JSON.
     * @return JSON object of response.
     * @throws IOException If I/O error occurs.
     */
    public static JsonObject sendPostRequestWithJsonBody(String url, String jsonBody)
            throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpUriRequest request = RequestBuilder.post(url)
                                               .setCharset(Charsets.UTF_8)
                                               .addHeader("Content-type", "application/json")
                                               .setEntity(new StringEntity(jsonBody, Charsets.UTF_8))
                                               .build();
        return httpClient.execute(request, new JsonResponseHandler());
    }

    /**
     * Send HTTP request with specified HTTP method.
     * If method is not specified, GET method will be used.
     * @param url URL of request.
     * @param method HTTP method used.
     * @param nameValuePairs name/stringValue pairs parameter used as an element of HTTP messages.
     * @return JSON object of response.
     * @throws IOException If I/O error occurs.
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
     * @param nameValuePairs name/stringValue pairs parameter used as an element of HTTP messages.
     * @return String response.
     * @throws IOException If I/O error occurs.
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

    /**
     * POST data using the Content-Type <code>multipart/form-data</code>.
     * This enables uploading of binary files etc.
     *
     * @param url URL of request.
     * @param textInputs Name-Value pairs of text inputs.
     * @param binaryInputs Name-Value pairs of binary files inputs.
     * @return JSON object of response.
     * @throws IOException If I/O error occurs.
     */
    public static JsonObject sendMultipartRequestAndGetJsonResponse(String url, Map<String, String> textInputs, Map<String, MultipartInput> binaryInputs)
            throws IOException {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        if (null != textInputs) {
            textInputs.forEach((k, v) -> multipartEntityBuilder.addTextBody(k, v));
        }
        if (null != binaryInputs) {
            binaryInputs.forEach(
                    (k, v) -> multipartEntityBuilder.addBinaryBody(k, v.getBinaryData(), v.getContentType(), v.getFilename()));
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setEntity(multipartEntityBuilder.build());
        return httpClient.execute(post, new JsonResponseHandler());
    }

    /**
     * Emulate binary file input in multipart form.
     */
    public static class MultipartInput {
        private String filename;
        private ContentType contentType;
        private InputStream data;

        public MultipartInput(String filename, String mimeType, InputStream data) {
            this.filename = filename;
            this.contentType = ContentType.create(mimeType);
            this.data = data;
        }

        public String getFilename() {
            return filename;
        }

        public ContentType getContentType() {
            return contentType;
        }

        public InputStream getBinaryData() {
            return data;
        }
    }

}
