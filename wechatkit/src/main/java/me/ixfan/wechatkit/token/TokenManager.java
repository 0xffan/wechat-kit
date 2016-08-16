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
import me.ixfan.wechatkit.constants.WeChatConstants;
import me.ixfan.wechatkit.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Warren Fan
 */
public class TokenManager {

    private Logger logger = LoggerFactory.getLogger(TokenManager.class);

    private String appid;
    private String appSecret;
    /**
     * <code>access_token</code> 缓存容器
     */
    private WechatAccessTokenContainer tokenContainer;

    public TokenManager(String appid, String appSecret, WechatAccessTokenContainer tokenContainer) {
        this.appid = appid;
        this.appSecret = appSecret;
        this.tokenContainer = tokenContainer;
    }

    /**
     * Get <code>access_token</code>, refresh <code>access_token</code> if necessary. See {@link #refreshAccessToken()}.
     * @return <code>access_token</code>
     */
    public String getAccessToken() {
        if (null == this.tokenContainer.getWechatApiAccessToken()) {
            refreshAccessToken();
        }
        return this.tokenContainer.getWechatApiAccessToken();
    }

    /**
     * Refresh cached <code>access_token</code>.
     */
    private void refreshAccessToken() {
        try {
            JsonObject jsonObject = HttpClientUtil.sendGetRequestAndGetJsonResponse(
                    WeChatConstants.WECHAT_GET_OBTAIN_ACCESS_TOKEN.replace("${APPID}", this.appid)
                                                                  .replace("${APPSECRET}", this.appSecret));

            Map<String, String> keyValues = new HashMap<>();
            for (Map.Entry<String, JsonElement> element: jsonObject.entrySet()) {
                keyValues.put(element.getKey(), element.getValue().getAsString());
            }

            if (null != keyValues.get("access_token")) {
                AccessToken accessToken = new AccessToken();
                accessToken.setAccessToken(keyValues.get("access_token"));
                accessToken.setExpiresIn(Long.parseLong(keyValues.get("expires_in")));

                this.tokenContainer.setWechatApiAccessToken(accessToken);
            } else {
                logger.error("Obtaining access_token failed, errcode:{}, errmsg:{}", keyValues.get(WeChatConstants.WECHAT_API_RESPONSE_KEY_ERRCODE), keyValues.get(WeChatConstants.WECHAT_API_RESPONSE_LEY_ERRMSG));
            }
        } catch (IOException e) {
            logger.error("Error occurred while getting access token!");
            e.printStackTrace();
        }
    }
}