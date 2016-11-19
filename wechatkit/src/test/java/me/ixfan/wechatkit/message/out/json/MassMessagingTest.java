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

package me.ixfan.wechatkit.message.out.json;

import me.ixfan.wechatkit.WeChatKit;
import me.ixfan.wechatkit.common.WeChatApiResult;
import me.ixfan.wechatkit.token.SimpleTokenContainer;
import me.ixfan.wechatkit.util.AppProperties;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * @author Warren Fan
 */
@RunWith(JUnit4.class)
public class MassMessagingTest {

    private WeChatKit weChatKit = WeChatKit.build(AppProperties.get("WECHAT_ID"),
                                                  AppProperties.get("APPID"),
                                                  AppProperties.get("APPSECRET"),
                                                  SimpleTokenContainer.getTokenContainer());
    private final String myOpenId = "op8KJwO3XUAMMsvDXqwVMWskLxV0";
    private final String testTag = "1";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testToUsersTextMessageContentCannotBeEmpty() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(startsWith("文本消息内容"));
        weChatKit.messageManager().sendMassTextMessageToUsers("", "openid1", "openid2");
    }

    @Test
    public void testToUsersTextMessageOpenIdsCannotBeEmpty() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(startsWith("OpenId列表"));
        weChatKit.messageManager().sendMassTextMessageToUsers("Hello World!", null);
    }

    @Test
    public void testToTagTextMessageContentCannotBeEmpty() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(startsWith("文本消息内容"));
        weChatKit.messageManager().sendMassTextMessageToUsersWithTag("", "2", false);
    }

    @Test
    public void testToTagTextMessageTagIdCannotBeEmpty() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(startsWith("标签ID"));
        weChatKit.messageManager().sendMassTextMessageToUsersWithTag("Hello World!", "", false);
    }

    @Ignore("测试通过")
    @Test
    public void testSendMassTextMessageToUsersSuccessfully() {
        WeChatApiResult result = weChatKit.messageManager().sendMassTextMessageToUsers("Hello World!", myOpenId, myOpenId);
        Assert.assertEquals("err_code should be 0!", 0L, result.getErrcode());
        Assert.assertNotNull("msg_id should not be blank!", result.getMsgId());
    }

    @Ignore("测试通过")
    @Test
    public void testSendMassImageMessageToUsersSuccessfully() {
        WeChatApiResult result = weChatKit.messageManager().sendMassImageToUsers("Y2H84Vo0AWTV8WL4jYMrZN6tWmvsWrspeafAXUSFvAo", myOpenId, myOpenId);
        Assert.assertEquals("err_code should be 0!", 0L, result.getErrcode());
        Assert.assertNotNull("msg_id should not be blank!", result.getMsgId());
    }

    @Ignore("未测")
    @Test
    public void testSendMassTextMessageToTagSuccessfully() {
        WeChatApiResult result = weChatKit.messageManager().sendMassTextMessageToUsersWithTag("Hello World!", testTag, false);
        Assert.assertEquals("err_code should be 0!", 0L, result.getErrcode());
        Assert.assertNotNull("msg_id should not be blank!", result.getMsgId());
    }

    @Ignore("未测")
    @Test
    public void testSendMassImageMessageToTagSuccessfully() {
        WeChatApiResult result = weChatKit.messageManager().sendMassImageToUsersWithTag("Y2H84Vo0AWTV8WL4jYMrZN6tWmvsWrspeafAXUSFvAo", testTag, false);
        Assert.assertEquals("err_code should be 0!", 0L, result.getErrcode());
        Assert.assertNotNull("msg_id should not be blank!", result.getMsgId());
    }
}
