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

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * 获取 access token 的重试策略.
 *
 * Created by xfan on 16/3/27.
 */
public class ObtainTokenRetryHandler implements HttpRequestRetryHandler {

    Logger logger = LoggerFactory.getLogger(ObtainTokenRetryHandler.class);

    public final int MAX_RETRY_TIME = 3;

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {

        if (executionCount > MAX_RETRY_TIME) {
            logger.warn("尝试获取 access token 超过 " + MAX_RETRY_TIME + " 次, 放弃重试.");
            return false;
        }
        if (exception instanceof InterruptedIOException) {
            logger.warn("尝试获取 access token 超时.");
            return false;
        }
        if (exception instanceof UnknownHostException) {
            logger.error("未知主机地址, 请确认微信 API 地址是否正确.");
            return false;
        }
        if (exception instanceof ConnectTimeoutException) {
            logger.error("获取 access token 的连接被拒绝.");
            return false;
        }
        if (exception instanceof SSLException) {
            logger.error("SSL 连接失败.");
            return false;
        }

        HttpClientContext clientContext = HttpClientContext.adapt(context);
        HttpRequest request = clientContext.getRequest();
        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
        if (idempotent) {
            return true;
        }

        return false;
    }
}
