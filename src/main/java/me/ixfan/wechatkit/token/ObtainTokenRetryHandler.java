package me.ixfan.wechatkit.token;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    Logger logger = LogManager.getLogger(ObtainTokenRetryHandler.class);

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
