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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.ixfan.wechatkit.message.out.template.TemplateMessageForSend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * @author Warren Fan
 */
@RunWith(JUnit4.class)
public class TemplateMessageJsonSerializeTest {

    private final String TPL_MSG_POST_DATA = "{\"touser\":\"OPENID\",\"template_id\":\"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY\",\"url\":\"http://weixin.qq.com/download\",\"data\":{\"first\":{\"value\":\"恭喜你购买成功！\",\"color\":\"#173177\"},\"keynote1\":{\"value\":\"巧克力\",\"color\":\"#173177\"},\"keynote2\":{\"value\":\"39.8元\",\"color\":\"#173177\"},\"keynote3\":{\"value\":\"2014年9月22日\",\"color\":\"#173177\"},\"remark\":{\"value\":\"欢迎再次购买！\"}}}";
    private final String TPL_MSG_POST_DATA_NO_URL = "{\"touser\":\"OPENID\",\"template_id\":\"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY\",\"url\":\"\",\"data\":{\"first\":{\"value\":\"恭喜你购买成功！\",\"color\":\"#173177\"},\"keynote1\":{\"value\":\"巧克力\",\"color\":\"#173177\"},\"keynote2\":{\"value\":\"39.8元\",\"color\":\"#173177\"},\"keynote3\":{\"value\":\"2014年9月22日\",\"color\":\"#173177\"},\"remark\":{\"value\":\"欢迎再次购买！\"}}}";

    @Test
    public void testSerializeTemplateMessageWithoutUrlForSendSuccess() {
        Map<String, String> params = new HashMap<>();
        params.put("first#173177", "恭喜你购买成功！");
        params.put("keynote1#173177", "巧克力");
        params.put("keynote2#173177", "39.8元");
        params.put("keynote3#173177", "2014年9月22日");
        params.put("remark", "欢迎再次购买！");
        TemplateMessageForSend templateMessageForSend =
                new TemplateMessageForSend("ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
                                           "OPENID",
                                           params);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(templateMessageForSend.toJsonString()).getAsJsonObject();
        assertTrue(jsonObject.equals(jsonParser.parse(TPL_MSG_POST_DATA_NO_URL).getAsJsonObject()));
    }

    @Test
    public void testSerializeTemplateMessageWithUrlForSendSuccess() {
        Map<String, String> params = new HashMap<>();
        params.put("first#173177", "恭喜你购买成功！");
        params.put("keynote1#173177", "巧克力");
        params.put("keynote2#173177", "39.8元");
        params.put("keynote3#173177", "2014年9月22日");
        params.put("remark", "欢迎再次购买！");
        TemplateMessageForSend templateMessageForSend =
                new TemplateMessageForSend("ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
                                           "OPENID",
                                           "http://weixin.qq.com/download",
                                           params);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(templateMessageForSend.toJsonString()).getAsJsonObject();
        assertTrue(jsonObject.equals(jsonParser.parse(TPL_MSG_POST_DATA).getAsJsonObject()));
    }
}
