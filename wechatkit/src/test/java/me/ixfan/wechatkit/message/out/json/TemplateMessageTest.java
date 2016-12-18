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
import me.ixfan.wechatkit.exceptions.WeChatApiErrorException;
import me.ixfan.wechatkit.message.out.template.MessageTemplate;
import me.ixfan.wechatkit.token.SimpleTokenContainer;
import me.ixfan.wechatkit.util.AppProperties;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * @author Warren Fan
 */
@RunWith(JUnit4.class)
public class TemplateMessageTest {

    private WeChatKit weChatKit = WeChatKit.build(AppProperties.get("WECHAT_ID"),
                                                  AppProperties.get("APPID"),
                                                  AppProperties.get("APPSECRET"),
                                                  SimpleTokenContainer.getTokenContainer());
    private final String myOpenId = "op8KJwO3XUAMMsvDXqwVMWskLxV0";

    @Ignore("测试通过")
    @Test
    public void testGetMessageTemplatesSuccess() {
        List<MessageTemplate> msgTpls;
        try {
            msgTpls = weChatKit.messageManager().retrieveMessageTemplates();
        } catch (WeChatApiErrorException e) {
            fail(e.getMessage());
            return;
        }
        assertNotNull(msgTpls);
    }

    @Ignore("测试通过")
    @Test
    public void testSendTemplateMessageSuccess() {
        Map<String, String> contentParams = new HashMap<>();
        contentParams.put("title#06D6A0", "Hello, World!");
        contentParams.put("nickname#EF476F", "X.F.");
        contentParams.put("gender#26547C", "男");
        contentParams.put("city", "成都");
        contentParams.put("remark", "查看详情了解更多信息");
        WeChatApiResult result = weChatKit.messageManager().sendTemplateMessage("7hdW0wvApI6VVdJyyooLyvu7GD1z66Vc1ZoDzSrUdSg", myOpenId, contentParams);
        assertNotNull(result);
        if (null != result) {
            assertNotNull(result.getMsgId());
        }
    }

    @Ignore("测试通过")
    @Test
    public void testSendTemplateMessageWithUrlSuccess() {
        Map<String, String> contentParams = new HashMap<>();
        contentParams.put("title#06D6A0", "Hello, World!");
        contentParams.put("nickname#EF476F", "X.F.");
        contentParams.put("gender#26547C", "男");
        contentParams.put("city", "成都");
        contentParams.put("remark", "查看详情了解更多信息");
        WeChatApiResult result = weChatKit.messageManager().sendTemplateMessage("7hdW0wvApI6VVdJyyooLyvu7GD1z66Vc1ZoDzSrUdSg",
                                                                                myOpenId,
                                                                                "https://mp.weixin.qq.com/wiki",
                                                                                contentParams);
        assertNotNull(result);
        if (null != result) {
            assertNotNull(result.getMsgId());
        }
    }

}
