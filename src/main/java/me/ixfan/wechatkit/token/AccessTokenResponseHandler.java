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
public class AccessTokenResponseHandler implements ResponseHandler<AccessToken> {

    private final Logger logger = LoggerFactory.getLogger(AccessTokenResponseHandler.class);

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
