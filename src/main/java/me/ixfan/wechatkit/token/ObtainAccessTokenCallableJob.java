package me.ixfan.wechatkit.token;

import me.ixfan.wechatkit.util.AppProperties;
import org.apache.commons.codec.Charsets;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.concurrent.Callable;

/**
 * 获取 access_token 的 Callable Job. 可以提交到 <code>ScheduledExecutorService</code>
 * 中周期性地去执行, 这也是微信官方推荐的获取 access_token 的方式. 一般一个 access_token 的
 * 有效期为2个小时.
 *
 * Created by xfan on 16/3/26.
 */
public class ObtainAccessTokenCallableJob implements Callable<AccessToken> {

    private final String OBTAIN_ACCESS_TOKEN_API_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    @Override
    public AccessToken call() throws Exception {

        String requestUrl = OBTAIN_ACCESS_TOKEN_API_URL
                            + "&appid=" + AppProperties.get("APPID")
                            + "&secret=" + AppProperties.get("APPSECRET");

        CloseableHttpClient httpClient = HttpClients.custom()
                .setRetryHandler(new ObtainTokenRetryHandler())
                .build();

        HttpUriRequest request = RequestBuilder.post(requestUrl).setCharset(Charsets.UTF_8).build();
        AccessToken accessToken = httpClient.execute(request, new AccessTokenResponseHandler());

        return accessToken;
    }
}
