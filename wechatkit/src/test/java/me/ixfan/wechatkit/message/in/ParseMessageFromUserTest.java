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
package me.ixfan.wechatkit.message.in;

import me.ixfan.wechatkit.WechatKit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.Assert.*;

/**
 * 解析微信推送的用户发送给公众号的消息。
 *
 * @author Warren Fan
 */
@RunWith(JUnit4.class)
public class ParseMessageFromUserTest {
    private final String TEXT_MSG_FROM_USER = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1358831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content><MsgId>1234567890123456</MsgId></xml>";
    private final String IMAGE_MSG_FROM_USER = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1358831860</CreateTime><MsgType><![CDATA[image]]></MsgType><PicUrl><![CDATA[this is a url]]></PicUrl><MediaId><![CDATA[media_id]]></MediaId><MsgId>1234567890123456</MsgId></xml>";
    private final String VOICE_MSG_FROM_USER = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1358831860</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[media_id]]></MediaId><Format><![CDATA[Format]]></Format><MsgId>1234567890123456</MsgId></xml>";
    private final String VOICE_MSG_WITH_RECOGNITON_FROM_USER = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1358831860</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[media_id]]></MediaId><Format><![CDATA[Format]]></Format><Recognition><![CDATA[腾讯微信团队]]></Recognition><MsgId>1234567890123456</MsgId></xml>";
    private final String VIDEO_MSG_FROM_USER = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1358831860</CreateTime><MsgType><![CDATA[video]]></MsgType><MediaId><![CDATA[media_id]]></MediaId><ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId><MsgId>1234567890123456</MsgId></xml>";
    private final String SHORT_VIDEO_MSG_FROM_USER = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1358831860</CreateTime><MsgType><![CDATA[shortvideo]]></MsgType><MediaId><![CDATA[media_id]]></MediaId><ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId><MsgId>1234567890123456</MsgId></xml>";
    private final String LOCATION_MSG_FROM_USER = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1358831860</CreateTime><MsgType><![CDATA[location]]></MsgType><Location_X>23.134521</Location_X><Location_Y>113.358803</Location_Y><Scale>20</Scale><Label><![CDATA[位置信息]]></Label><MsgId>1234567890123456</MsgId></xml>";
    private final String LINK_MSG_FROM_USER = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1358831860</CreateTime><MsgType><![CDATA[link]]></MsgType><Title><![CDATA[公众平台官网链接]]></Title><Description><![CDATA[公众平台官网链接]]></Description><Url><![CDATA[url]]></Url><MsgId>1234567890123456</MsgId></xml>";
    WechatKit wechatKit = WechatKit.build("YOUR_APPID", "YOUR_APP_SECRET", null);

    @Test
    public void parseXmlTextMessageSuccessfully() {
        ReceivedMsg receivedMsg = wechatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                TEXT_MSG_FROM_USER)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 1358831860L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.TEXT.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof ReceivedTextMsg);
        ReceivedTextMsg receivedTextMsg = (ReceivedTextMsg) receivedMsg;
        assertEquals("Get wrong content of text message!", "this is a test", receivedTextMsg.getContent());
        assertEquals("Get wrong <MsgId>!", "1234567890123456", receivedTextMsg.getMsgId());
    }

    @Test
    public void parseXmlImageMessageSuccessfully() {
        ReceivedMsg receivedMsg = wechatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                IMAGE_MSG_FROM_USER)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 1358831860L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.IMAGE.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof ReceivedImageMsg);
        ReceivedImageMsg receivedImageMsg = (ReceivedImageMsg) receivedMsg;
        assertEquals("Get wrong <PicUrl>!", "this is a url", receivedImageMsg.getPicUrl());
        assertEquals("get wrong <MediaId>!", "media_id", receivedImageMsg.getMediaId());
        assertEquals("Get wrong <MsgId>!", "1234567890123456", receivedImageMsg.getMsgId());
    }

    @Test
    public void parseXmlVoiceMessageSuccessfully() {
        ReceivedMsg receivedMsg = wechatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                VOICE_MSG_FROM_USER)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 1358831860L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.VOICE.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof ReceivedVoiceMsg);
        ReceivedVoiceMsg receivedVoiceMsg = (ReceivedVoiceMsg) receivedMsg;
        assertEquals("Get wrong <MediaId>!", "media_id", receivedVoiceMsg.getMediaId());
        assertEquals("Get wrong <Format>!", "Format", receivedVoiceMsg.getFormat());
        assertEquals("Get wrong <MsgId>!", "1234567890123456", receivedVoiceMsg.getMsgId());
        assertNull("There's no recognition for this voice message!", receivedVoiceMsg.getRecognition());
    }

    @Test
    public void parseXmlVoiceMessageWithRecognitionSuccessfully() {
        ReceivedMsg receivedMsg = wechatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                VOICE_MSG_WITH_RECOGNITON_FROM_USER)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 1358831860L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.VOICE.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof ReceivedVoiceMsg);
        ReceivedVoiceMsg receivedVoiceMsg = (ReceivedVoiceMsg) receivedMsg;
        assertEquals("Get wrong <MediaId>!", "media_id", receivedVoiceMsg.getMediaId());
        assertEquals("Get wrong <Format>!", "Format", receivedVoiceMsg.getFormat());
        assertEquals("Get wrong <MsgId>!", "1234567890123456", receivedVoiceMsg.getMsgId());
        assertEquals("Get wrong <Recognition>!", "腾讯微信团队", receivedVoiceMsg.getRecognition());
    }

    @Test
    public void parseXmlVideoMessageSuccessfully() {
        ReceivedMsg receivedMsg = wechatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                VIDEO_MSG_FROM_USER)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 1358831860L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.VIDEO.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof ReceivedVideoMsg);
        ReceivedVideoMsg receivedVideoMsg = (ReceivedVideoMsg) receivedMsg;
        assertEquals("Get wrong <MediaId>!", "media_id", receivedVideoMsg.getMediaId());
        assertEquals("Get wrong <ThumbMediaId>!", "thumb_media_id", receivedVideoMsg.getThumbMediaId());
        assertEquals("Get wrong <MsgId>!", "1234567890123456", receivedVideoMsg.getMsgId());
    }

    @Test
    public void parseXmlShortVideoMessageSuccessfully() {
        ReceivedMsg receivedMsg = wechatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                SHORT_VIDEO_MSG_FROM_USER)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 1358831860L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.SHORT_VIDEO.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof ReceivedVideoMsg);
        ReceivedVideoMsg receivedVideoMsg = (ReceivedVideoMsg) receivedMsg;
        assertEquals("Get wrong <MediaId>!", "media_id", receivedVideoMsg.getMediaId());
        assertEquals("Get wrong <ThumbMediaId>!", "thumb_media_id", receivedVideoMsg.getThumbMediaId());
        assertEquals("Get wrong <MsgId>!", "1234567890123456", receivedVideoMsg.getMsgId());
    }

    @Test
    public void parseXmlLocationMessageSuccessfully() {
        ReceivedMsg receivedMsg = wechatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                LOCATION_MSG_FROM_USER)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 1358831860L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.LOCATION.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof ReceivedLocationMsg);
        ReceivedLocationMsg receivedLocationMsg = (ReceivedLocationMsg) receivedMsg;
        assertEquals("Get wrong <Location_X>!", 23.134521D, receivedLocationMsg.getLatitude().doubleValue(), 0D);
        assertEquals("Get wrong <Location_Y>!", 113.358803D, receivedLocationMsg.getLongitude().doubleValue(), 0D);
        assertEquals("Get wrong <Scale>!", 20f, receivedLocationMsg.getScale().floatValue(), 0f);
        assertEquals("Get wrong <Label>!", "位置信息", receivedLocationMsg.getLabel());
        assertEquals("Get wrong <MsgId>!", "1234567890123456", receivedLocationMsg.getMsgId());
    }

    @Test
    public void parseXmlLinkMessageSuccessfully() {
        ReceivedMsg receivedMsg = wechatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                LINK_MSG_FROM_USER)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 1358831860L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.LINK.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof ReceivedLinkMsg);
        ReceivedLinkMsg receivedLinkMsg = (ReceivedLinkMsg) receivedMsg;
        assertEquals("Get wrong <Title>!", "公众平台官网链接", receivedLinkMsg.getTitle());
        assertEquals("Get wrong <Description>!", "公众平台官网链接", receivedLinkMsg.getDescription());
        assertEquals("Get wrong <Url>!", "url", receivedLinkMsg.getUrl());
        assertEquals("Get wrong <MsgId>!", "1234567890123456", receivedLinkMsg.getMsgId());
    }
}
