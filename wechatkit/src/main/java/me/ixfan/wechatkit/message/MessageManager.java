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
import me.ixfan.wechatkit.exceptions.WechatXmlMessageParseException;
import me.ixfan.wechatkit.message.in.*;
import me.ixfan.wechatkit.message.in.event.*;
import me.ixfan.wechatkit.token.TokenManager;
import me.ixfan.wechatkit.util.JAXBUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

/**
 * 消息管理
 *
 * Created by Warran Fan on 16/3/26.
 */
public class MessageManager {

    private TokenManager tokenManager;

    public MessageManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * 解析来自微信的 XML 格式的消息,返回对应的消息对象实例。
     *
     * @param xmlMessageReader 微信推送的 XML 格式的消息或事件。
     * @return 消息或事件对象实例
     */
    public ReceivedMsg parseXmlMessage(Reader xmlMessageReader) throws WechatXmlMessageParseException {
        String msgInXml;
        try {
            msgInXml = getContent(xmlMessageReader);
        } catch (IOException e) {
            throw new WechatXmlMessageParseException("Failed to read content from 'Reader'.", e);
        }
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new WechatXmlMessageParseException("Failed to create DocumentBuilder instance.", e);
        }

        Document document;
        try {
            document = documentBuilder.parse(new InputSource(new StringReader(msgInXml)));
        }  catch (Exception e) {
            throw new WechatXmlMessageParseException("Failed to parse XML message.", e);
        }

        NodeList msgTypeNodes = document.getElementsByTagName("MsgType");
        if (null == msgTypeNodes || msgTypeNodes.getLength() == 0) {
            throw new WechatXmlMessageParseException("Invalid XML message!");
        }

        InMessageType msgType = InMessageType.of(msgTypeNodes.item(0).getFirstChild().getNodeValue());
        switch (msgType) {
            case TEXT:
                try {
                    return JAXBUtil.unmarshal(msgInXml, ReceivedTextMsg.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize text message in XML to object.", e);
                }
            case IMAGE:
                try {
                    return JAXBUtil.unmarshal(msgInXml, ReceivedImageMsg.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize image message in XML to object.", e);
                }
            case VOICE:
                try {
                    return JAXBUtil.unmarshal(msgInXml, ReceivedVoiceMsg.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize voice message in XML to object.", e);
                }
            case VIDEO:
            case SHORT_VIDEO:
                try {
                    return JAXBUtil.unmarshal(msgInXml, ReceivedVideoMsg.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize video message in XML to object.", e);
                }
            case LOCATION:
                try {
                    return JAXBUtil.unmarshal(msgInXml, ReceivedLocationMsg.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize location message in XML to object.", e);
                }
            case LINK:
                try {
                    return JAXBUtil.unmarshal(msgInXml, ReceivedLinkMsg.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize link message in XML to object.", e);
                }
            case EVENT:
                EventType eventType = EventType.of(document.getElementsByTagName("EVENT").item(0).getFirstChild().getNodeValue());
                return parseEventMessage(msgInXml, eventType);
            default: return null;
        }

    }

    /**
     * 解析事件消息。
     *
     * @param msgInXml XML 消息数据包
     * @param eventType 事件类型
     * @return 解析后的事件消息对象
     */
    private ReceivedMsg parseEventMessage(String msgInXml, EventType eventType) throws WechatXmlMessageParseException {
        switch (eventType) {
            case SUBSCRIBE:
                try {
                    return JAXBUtil.unmarshal(msgInXml, SubscribeEvent.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize subscribe event message in XML to object.", e);
                }
            case UNSUBSCRIBE:
                try {
                    return JAXBUtil.unmarshal(msgInXml, UnsubscribeEvent.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize unsubscribe event message in XML to object.", e);
                }
            case SCAN:
                try {
                    return JAXBUtil.unmarshal(msgInXml, QrSceneEvent.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize QR scan event message in XML to object.", e);
                }
            case LOCATION:
                try {
                    return JAXBUtil.unmarshal(msgInXml, LocationEvent.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize location event message in XML to object.", e);
                }
            case MENU_CLICK:
                try {
                    return JAXBUtil.unmarshal(msgInXml, MenuClickEvent.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize menu CLICK event message in XML to object.", e);
                }
            case MENU_VIEW:
                try {
                    return JAXBUtil.unmarshal(msgInXml, MenuViewEvent.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize menu VIEW event message in XML to object.", e);
                }
            case MENU_PIC_OR_ALBUM:
            case MENU_PIC_SYS_PHOTO:
            case MENU_PIC_WEIXIN:
                try {
                    return JAXBUtil.unmarshal(msgInXml, MenuSendPictureEvent.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize menu send picture event message in XML to object.", e);
                }
            case MENU_SCAN_CODE_PUSH:
            case MENU_SCAN_CODE_WAIT_MSG:
                try {
                    return JAXBUtil.unmarshal(msgInXml, MenuScanCodeEvent.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize menu scan code event message in XML to object.", e);
                }
            case MENU_LOCATION_SELECT:
                try {
                    return JAXBUtil.unmarshal(msgInXml, MenuSendLocationEvent.class);
                } catch (JAXBException e) {
                    throw new WechatXmlMessageParseException("Failed to deserialize menu send location event message in XML to object.", e);
                }
            default: return null;
        }
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

    public String getContent(Reader reader) throws IOException {
        char[] charsArray = new char[1024];
        StringBuilder stringBuilder = new StringBuilder();
        int numCharsReaded = 0;
        while (-1 != (numCharsReaded = reader.read(charsArray))) {
            stringBuilder.append(charsArray, 0, numCharsReaded);
        }
        return stringBuilder.toString();
    }

}
