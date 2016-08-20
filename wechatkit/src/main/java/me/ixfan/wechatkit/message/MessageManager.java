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

package me.ixfan.wechatkit.message;

import me.ixfan.wechatkit.common.WechatApiResult;
import me.ixfan.wechatkit.message.in.ReceivedMsg;

import java.util.Map;

/**
 * 消息管理
 *
 * Created by Warran Fan on 16/3/26.
 */
public class MessageManager {

    /**
     * 解析来自微信的 XML 格式的消息,返回对应的消息对象实例。
     * @param msgInXml 微信推送的 XML 格式的消息或事件。
     * @return 消息或事件对象实例
     */
    public static ReceivedMsg parseXmlMessage(String msgInXml) {
        // TODO: 解析来自微信的 XML 格式的消息, 返回对应的消息对象实例

        return null;
    }

    public WechatApiResult massMessaging(String content, String[] openids) {
        return null;
    }

    public WechatApiResult massMessaging(String content, String tadId, boolean isToAll) {
        return null;
    }

    /**
     * TODO 向用户发送模板消息。
     *
     * @param templateId 消息模板ID
     * @param openid 接收消息的用户的 openid
     * @param params 消息模板中需要传入的数据
     * @return 微信模板消息接口的返回结果,封装成 {@link WechatApiResult} 的实例。
     */
    public WechatApiResult sendTemplateMessage(String templateId, String openid, Map<String, String> params) {
        return null;
    }
}
