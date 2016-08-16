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

import me.ixfan.wechatkit.util.AppProperties;
import org.apache.commons.codec.Charsets;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * 获取 access_token 的 Callable Job. 可以提交到 <code>ScheduledExecutorService</code>
 * 中周期性地去执行, 这也是微信官方推荐的获取 access_token 的方式. 一般一个 access_token 的
 * 有效期为2个小时.
 *
 * Created by xfan on 16/3/26.
 */
public class ObtainAccessTokenCallableJob implements Callable<AccessToken> {

    private static final String WECHAT_GET_OBTAIN_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${APPID}&secret=${APPSECRET}";

    @Override
    public AccessToken call() {

        String requestUrl = WECHAT_GET_OBTAIN_ACCESS_TOKEN
                            .replace("${APPID}", AppProperties.get("APPID"))
                            .replace("${APPSECRET}", AppProperties.get("APPSECRET"));

        CloseableHttpClient httpClient = HttpClients.custom()
                .setRetryHandler(new ObtainTokenRetryHandler())
                .build();

        HttpUriRequest request = RequestBuilder.get(requestUrl).setCharset(Charsets.UTF_8).build();
        AccessToken accessToken = null;
        try {
            accessToken = httpClient.execute(request, new AccessTokenResponseHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }
}
