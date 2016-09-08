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

import me.ixfan.wechatkit.WeChatKit;
import me.ixfan.wechatkit.message.in.event.*;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * 解析微信推送的事件消息。
 *
 * @author Warren Fan
 */
public class ParseEventFromWechatTest {
    private final String SUBSCRIBE_EVENT = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><EVENT><![CDATA[subscribe]]></EVENT></xml>";
    private final String UNSUBSCRIBE_EVENT = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><EVENT><![CDATA[unsubscribe]]></EVENT></xml>";
    private final String SCAN_CODE_THEN_FOLLOW_EVENT = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><EVENT><![CDATA[subscribe]]></EVENT><EventKey><![CDATA[qrscene_123123]]></EventKey><Ticket><![CDATA[TICKET]]></Ticket></xml>";
    private final String FOLLOWER_SCAN_CODE_EVENT = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><EVENT><![CDATA[SCAN]]></EVENT><EventKey><![CDATA[SCENE_VALUE]]></EventKey><Ticket><![CDATA[TICKET]]></Ticket></xml>";
    private final String REPORT_LOCATION_EVENT = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><EVENT><![CDATA[LOCATION]]></EVENT><Latitude>23.137466</Latitude><Longitude>113.352425</Longitude><Precision>119.385040</Precision></xml>";
    private final String MENU_CLICK_EVENT = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><EVENT><![CDATA[CLICK]]></EVENT><EventKey><![CDATA[EVENTKEY]]></EventKey></xml>";
    private final String MENU_VIEW_EVENT = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><EVENT><![CDATA[VIEW]]></EVENT><EventKey><![CDATA[www.qq.com]]></EventKey></xml>";
    WeChatKit weChatKit = WeChatKit.build("YOUR_WECHAt_ACCOUNT_ID", "YOUR_APPID", "YOUR_APP_SECRET", null);

    @Test
    public void parseSubscribeEventSuccessfully() {
        ReceivedMsg receivedMsg = weChatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                SUBSCRIBE_EVENT)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 123456789L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.EVENT.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof EventMsg);
        assertTrue(receivedMsg instanceof SubscribeEvent);
        SubscribeEvent subscribeEvent = (SubscribeEvent) receivedMsg;
        assertEquals("Get wrong <Event>!", EventType.SUBSCRIBE.stringValue(), subscribeEvent.getEvent());
    }

    @Test
    public void parseUnsubscribeEventSuccessfully() {
        ReceivedMsg receivedMsg = weChatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                UNSUBSCRIBE_EVENT)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 123456789L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.EVENT.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof EventMsg);
        assertTrue(receivedMsg instanceof UnsubscribeEvent);
        UnsubscribeEvent unsubscribeEvent = (UnsubscribeEvent) receivedMsg;
        assertEquals("Get wrong <Event>!", EventType.UNSUBSCRIBE.stringValue(), unsubscribeEvent.getEvent());
    }

    @Test
    public void parseScanCodeAndFollowEventSuccessfully() {
        ReceivedMsg receivedMsg = weChatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                SCAN_CODE_THEN_FOLLOW_EVENT)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 123456789L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.EVENT.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof EventMsg);
        assertTrue(receivedMsg instanceof SubscribeEvent);
        SubscribeEvent subscribeEvent  = (SubscribeEvent) receivedMsg;
        assertEquals("Get wrong <Event>!", EventType.SUBSCRIBE.stringValue(), subscribeEvent.getEvent());
        assertEquals("Get wrong <EventKey>!", "qrscene_123123", subscribeEvent.getEventKey());
        assertEquals("Get wrong <Ticket>!", "TICKET", subscribeEvent.getTicket());
    }

    @Test
    public void parseFollowerScanCodeEventSuccessfully() {
        ReceivedMsg receivedMsg = weChatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                FOLLOWER_SCAN_CODE_EVENT)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 123456789L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.EVENT.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof EventMsg);
        assertTrue(receivedMsg instanceof QrSceneEvent);
        QrSceneEvent qrSceneEvent = (QrSceneEvent) receivedMsg;
        assertEquals("Get wrong <Event>!", EventType.SCAN.stringValue(), qrSceneEvent.getEvent());
        assertEquals("Get wrong <EventKey>!", "SCENE_VALUE", qrSceneEvent.getEventKey());
        assertEquals("Get wrong <Ticket>!", "TICKET", qrSceneEvent.getTicket());
    }

    @Test
    public void parseLocationReportEventSuccessfully() {
        ReceivedMsg receivedMsg = weChatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                REPORT_LOCATION_EVENT)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 123456789L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.EVENT.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof EventMsg);
        assertTrue(receivedMsg instanceof LocationEvent);
        LocationEvent locationEvent = (LocationEvent) receivedMsg;
        assertEquals("Get wrong <Event>!", EventType.LOCATION.stringValue(), locationEvent.getEvent());
        assertEquals("Get wrong <Latitude>!", 23.137466D, locationEvent.getLatitude(), 0D);
        assertEquals("Get wrong <Longitude>!", 113.352425D, locationEvent.getLongitude(), 0D);
        assertEquals("Get wrong <Precision>!", 119.385040D, locationEvent.getPrecision(), 0D);
    }

    @Test
    public void parseMenuClickEventSuccessfully() {
        ReceivedMsg receivedMsg = weChatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                MENU_CLICK_EVENT)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 123456789L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.EVENT.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof EventMsg);
        assertTrue(receivedMsg instanceof MenuClickEvent);
        MenuClickEvent menuClickEvent = (MenuClickEvent) receivedMsg;
        assertEquals("Get wrong <Event>!", EventType.MENU_CLICK.stringValue(), menuClickEvent.getEvent());
        assertEquals("Get wrong <EventKey>!", "EVENTKEY", menuClickEvent.getEventKey());
    }

    @Test
    public void parseMenuViewEventSuccessfully() {
        ReceivedMsg receivedMsg = weChatKit.messageManager().parseXmlMessage(new BufferedReader(new StringReader(
                MENU_VIEW_EVENT)));
        assertEquals("Get wrong <ToUserName>!", "toUser", receivedMsg.getToUserName());
        assertEquals("Get wrong <FromUserName>!", "fromUser", receivedMsg.getFromUserName());
        assertEquals("Get wrong <CreateTime>!", 123456789L, receivedMsg.getCreateTime().longValue());
        assertEquals("Get wrong <MsgType>!", InMessageType.EVENT.stringValue(), receivedMsg.getMsgType());
        assertTrue(receivedMsg instanceof EventMsg);
        assertTrue(receivedMsg instanceof MenuViewEvent);
        MenuViewEvent menuViewEvent = (MenuViewEvent) receivedMsg;
        assertEquals("Get wrong <Event>!", EventType.MENU_VIEW.stringValue(), menuViewEvent.getEvent());
        assertEquals("Get wrong <EventKey>!", "www.qq.com", menuViewEvent.getEventKey());
    }
}
