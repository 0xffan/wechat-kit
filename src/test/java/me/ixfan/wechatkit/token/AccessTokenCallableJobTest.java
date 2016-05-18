package me.ixfan.wechatkit.token;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.fail;

/**
 * Created by xfan on 16/5/15.
 */
public class AccessTokenCallableJobTest {

    @Test
    public void successfullyRetrievedAccessToken() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<AccessToken> task = new ObtainAccessTokenCallableJob();
        Future<AccessToken> future = executor.submit(task);
        try {
            AccessToken accessToken = future.get();
            executor.shutdown();
            Assert.assertNotNull(accessToken);
            Assert.assertNotNull(accessToken.getAccessToken());
            Assert.assertNotNull(accessToken.getExpiresIn());
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Task has been interrupted!");
        } catch (ExecutionException e) {
            e.printStackTrace();
            fail("Exception should not occurred!");
        }
    }
}
