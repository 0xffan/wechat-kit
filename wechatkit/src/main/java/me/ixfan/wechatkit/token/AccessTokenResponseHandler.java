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

package me.ixfan.wechatkit.token;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * ResponseHandler for request of access token.
 *
 * Created by xfan on 16/3/27.
 */
class AccessTokenResponseHandler implements ResponseHandler<AccessToken> {

    private final Logger logger = LoggerFactory.getLogger(AccessTokenResponseHandler.class);

    /**
     * Response handler of WeChat access_token request.
     *
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public AccessToken handleResponse(HttpResponse response) throws IOException {
        StatusLine status = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        if (status.getStatusCode() >= 300) {
            throw new HttpResponseException(status.getStatusCode(), status.getReasonPhrase());
        }
        if (null == entity) {
            logger.error("Exception occurred while obtaining access_token, there's no data in the response.");
            throw new ClientProtocolException("Empty HTTP response");
        }

        ContentType contentType = ContentType.getOrDefault(entity);
        Charset charset = contentType.getCharset();
        if (null == charset) {
            charset = StandardCharsets.UTF_8;
        }
        Reader reader = new InputStreamReader(entity.getContent(), charset);
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();

        logger.debug("obtained access_token: " + jsonObject.toString());

        Map<String, String> keyValues = new HashMap<>();
        for (Map.Entry<String, JsonElement> element: jsonObject.entrySet()) {
            keyValues.put(element.getKey(), element.getValue().getAsString());
        }

        AccessToken accessToken = new AccessToken();
        if (null != keyValues.get("access_token")) {
            accessToken.setAccessToken(keyValues.get("access_token"));
            accessToken.setExpiresIn(Long.parseLong(keyValues.get("expires_in")));
        } else {
            logger.error("Obtaining access_token failed, errcode:{}, errmsg:{}", keyValues.get("errcode"), keyValues.get("errmsg"));
        }

        return accessToken;
    }
}
