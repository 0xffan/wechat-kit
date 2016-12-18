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

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import me.ixfan.wechatkit.WeChatKitComponent;
import me.ixfan.wechatkit.common.WeChatApiResult;
import me.ixfan.wechatkit.common.WeChatConstants;
import me.ixfan.wechatkit.exceptions.WeChatApiErrorException;
import me.ixfan.wechatkit.exceptions.WechatXmlMessageParseException;
import me.ixfan.wechatkit.exceptions.WechatXmlMessageSerializationException;
import me.ixfan.wechatkit.material.MediaObject;
import me.ixfan.wechatkit.message.in.*;
import me.ixfan.wechatkit.message.in.event.*;
import me.ixfan.wechatkit.message.out.OutMessageType;
import me.ixfan.wechatkit.message.out.json.MessageForMassSend;
import me.ixfan.wechatkit.message.out.template.MessageTemplate;
import me.ixfan.wechatkit.message.out.template.TemplateMessageForSend;
import me.ixfan.wechatkit.message.out.xml.*;
import me.ixfan.wechatkit.token.TokenManager;
import me.ixfan.wechatkit.util.HttpClientUtil;
import me.ixfan.wechatkit.util.JAXBUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.util.Args;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * 消息管理
 *
 * Created by Warran Fan on 16/3/26.
 */
public class MessageManager extends WeChatKitComponent {

    public MessageManager(String wechatAccountId, TokenManager tokenManager) {
        super(wechatAccountId, tokenManager);
    }

    /**
     * 解析来自微信的 XML 格式的消息,返回对应的消息对象实例。
     *
     * @param msgInXml 微信推送的 XML 格式的消息或事件。
     * @return 消息或事件对象实例。
     */
    public ReceivedMsg parseXmlMessage(final String msgInXml) throws WechatXmlMessageParseException {

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
                EventType eventType = EventType.of(document.getElementsByTagName("Event").item(0).getFirstChild().getNodeValue());
                return parseEventMessage(msgInXml, eventType);
            default: return null;
        }

    }

    /**
     * 把被动回复的消息对象转换成 XML 数据包。
     *
     * @param responseMessage 被动回复消息对象。
     * @return 被动回复消息的 XML 数据包。
     */
    public String generateResponseMessageInXml(ResponseMsg responseMessage)
            throws WechatXmlMessageSerializationException {
        if (responseMessage instanceof ResponseTextMsg) {
            try {
                return JAXBUtil.marshal((ResponseTextMsg) responseMessage);
            } catch (Exception e) {
                throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
            }
        } else if (responseMessage instanceof ResponseImageMsg) {
            try {
                return JAXBUtil.marshal((ResponseImageMsg) responseMessage);
            } catch (Exception e) {
                throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
            }
        } else if (responseMessage instanceof ResponseVoiceMsg) {
            try {
                return JAXBUtil.marshal((ResponseVoiceMsg) responseMessage);
            } catch (Exception e) {
                throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
            }
        } else if (responseMessage instanceof ResponseMusicMsg) {
            try {
                return JAXBUtil.marshal((ResponseMusicMsg) responseMessage);
            } catch (Exception e) {
                throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
            }
        } else if (responseMessage instanceof ResponseVideoMsg) {
            try {
                return JAXBUtil.marshal((ResponseVideoMsg) responseMessage);
            } catch (Exception e) {
                throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
            }
        } else if (responseMessage instanceof ResponseNewsMsg) {
            try {
                return JAXBUtil.marshal((ResponseNewsMsg) responseMessage);
            } catch (Exception e) {
                throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
            }
        }
        return "success";
    }

    /**
     * 构造被动回复文本消息的 XML 数据包。
     *
     * @param toUserOpenid 接收消息的用户 openid。
     * @param content 文本消息内容。
     * @return 被动回复文本消息的 XML 数据包。
     */
    public String generateTextResponseMessageInXml(String toUserOpenid, String content) {
        ResponseTextMsg textMsg = new ResponseTextMsg(super.wechatId, toUserOpenid, System.currentTimeMillis(), content);
        try {
            return JAXBUtil.marshal(textMsg);
        } catch (Exception e) {
            throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
        }
    }

    /**
     * 构造被动回复图片消息的 XML 数据包。
     *
     * @param toUserOpenid 接收消息的用户的openid。
     * @param mediaId 通过素材管理中的接口上传多媒体文件得到的ID。
     * @return 被动回复图片消息的 XML 数据包。
     */
    public String generateImageResponseMessageInXml(String toUserOpenid, String mediaId) {
        ResponseImageMsg imageMsg = new ResponseImageMsg(super.wechatId, toUserOpenid, System.currentTimeMillis());
        imageMsg.setMediaImage(new MediaObject(mediaId));
        try {
            return JAXBUtil.marshal(imageMsg);
        } catch (Exception e) {
            throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
        }
    }

    /**
     * 构造被动回复语音消息的 XML 数据包。
     *
     * @param toUserOpenid 接收消息的用户的openid。
     * @param mediaId 通过素材管理中的接口上传多媒体文件得到的ID。
     * @return 被动回复语音消息的 XML 数据包。
     */
    public String generateVoiceResponseMessageInXml(String toUserOpenid, String mediaId) {
        ResponseVoiceMsg voiceMsg = new ResponseVoiceMsg(super.wechatId, toUserOpenid, System.currentTimeMillis());
        voiceMsg.setMediaVoice(new MediaObject(mediaId));
        try {
            return JAXBUtil.marshal(voiceMsg);
        } catch (Exception e) {
            throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
        }
    }

    /**
     * 构造被动回复视频消息的 XML 数据包。
     * @param toUserOpenid 接收消息的用户的 openid。
     * @param title 视频消息的标题
     * @param description 视频消息的描述
     * @param mediaId 通过素材管理中的接口上传多媒体文件得到的ID。
     * @return 被动回复视频消息的 XML 数据包。
     */
    public String generateVideoResponseMessageInXml(String toUserOpenid, String title, String description, String mediaId) {
        ResponseVideoMsg videoMsg = new ResponseVideoMsg(super.wechatId, toUserOpenid, System.currentTimeMillis());
        videoMsg.setVideo(new Video(title, description, mediaId));
        try {
            return JAXBUtil.marshal(videoMsg);
        } catch (Exception e) {
            throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
        }
    }

    /**
     * 构造被动回复音乐消息的 XML 数据包。
     * @param toUserOpenid 接收消息的用户的 openid。
     * @param music 要发送的音乐消息对象。
     * @return 被动回复音乐消息的 XML 数据包。
     */
    public String generateMusicResponseMessageInXml(String toUserOpenid, Music music) {
        ResponseMusicMsg musicMsg = new ResponseMusicMsg(super.wechatId, toUserOpenid, System.currentTimeMillis());
        musicMsg.setMusic(music);
        try {
            return JAXBUtil.marshal(musicMsg);
        } catch (Exception e) {
            throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
        }
    }

    /**
     * 构造被动回复图文消息的 XML 数据包。
     * @param toUserOpenid 接收消息的用户的 openid。
     * @param articles 多条图文消息信息，默认第一个item为大图。注意，如果图文数超过10，微信接口则将会无响应。
     * @return 被动回复图文消息的 XML 数据包。
     */
    public String generateNewsResponseMessageInXml(String toUserOpenid, Article... articles) {
        ResponseNewsMsg newsMsg = new ResponseNewsMsg(super.wechatId, toUserOpenid, System.currentTimeMillis());
        newsMsg.setArticles(Arrays.asList(articles));
        try {
            return JAXBUtil.marshal(newsMsg);
        } catch (Exception e) {
            throw new WechatXmlMessageSerializationException("消息对象转XML格式失败。", e);
        }
    }

    /**
     * 根据 OpenId 列表群发文本消息。
     * @param content 文本消息内容。
     * @param openIds OpenId 列表。
     * @return 发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID；
     * 若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WeChatApiResult sendMassTextMessageToUsers(String content, String... openIds) {
        Args.notEmpty(content, "文本消息内容");
        Args.notNull(openIds, "OpenId列表");
        Args.notEmpty(Arrays.asList(openIds), "OpenId列表");

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SNED_BY_OPENIDS.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.TEXT, content, Arrays.asList(openIds));
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据标签群发文本消息。
     * @param content 文本消息内容。
     * @param tagId 群发到的标签的tag_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id。
     * @param isToAll 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据tag_id发送给指定群组的用户。
     *                此参数的使用有些需要注意的地方，请查阅微信开发者文档群发消息的部分。
     * @return 发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID；
     * 若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WeChatApiResult sendMassTextMessageToUsersWithTag(String content, String tagId, boolean isToAll) {
        Args.notEmpty(content, "文本消息内容");
        if (!isToAll) {
            Args.notEmpty(tagId, "标签ID");
        }

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SEND_BY_TAG.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.TEXT, content, tagId, isToAll);
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据 OpenId 列表群发图片消息。
     * @param mediaId 用于群发的图片的 media_id。
     * @param openIds OpenId 列表。
     * @return 发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID；
     * 若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WeChatApiResult sendMassImageToUsers(String mediaId, String... openIds) {
        Args.notEmpty(mediaId, "Media ID of image material");
        Args.notNull(openIds, "OpenId list");
        Args.notEmpty(Arrays.asList(openIds), "OpenId list");

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SNED_BY_OPENIDS.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.IMAGE, mediaId, Arrays.asList(openIds));
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据标签群发图片消息。
     * @param mediaId 用于群发的图片的 media_id。
     * @param tagId 群发到的标签的tag_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id。
     * @param isToAll 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据tag_id发送给指定群组的用户。
     *                此参数的使用有些需要注意的地方，请查阅微信开发者文档群发消息的部分。
     * @return 发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID；
     * 若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WeChatApiResult sendMassImageToUsersWithTag(String mediaId, String tagId, boolean isToAll) {
        Args.notEmpty(mediaId, "Media ID of image material");
        if (!isToAll) {
            Args.notEmpty(tagId, "Tag ID");
        }

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SEND_BY_TAG.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.IMAGE, mediaId, tagId, false);
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据 OpenId 列表群发语音消息。
     * @param mediaId 用于群发的语音的 media_id。
     * @param openIds OpenId 列表。
     * @return 发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID；
     * 若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WeChatApiResult sendMassVoiceMessageToUsers(String mediaId, String... openIds) {
        Args.notEmpty(mediaId, "Media ID of voice material");
        Args.notNull(openIds, "OpenId list");
        Args.notEmpty(Arrays.asList(openIds), "OpenId list");

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SNED_BY_OPENIDS.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.VOICE, mediaId, Arrays.asList(openIds));
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据标签群发语音消息。
     * @param mediaId 用于群发的语音的 media_id。
     * @param tagId 群发到的标签的tag_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id。
     * @param isToAll 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据tag_id发送给指定群组的用户。
     *                此参数的使用有些需要注意的地方，请查阅微信开发者文档群发消息的部分。
     * @return 发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID；
     * 若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WeChatApiResult sendMassVoiceMessageToUsersWithTag(String mediaId, String tagId, boolean isToAll) {
        Args.notEmpty(mediaId, "Media ID of voice material");
        if (!isToAll) {
            Args.notEmpty(tagId, "Tag ID");
        }

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SEND_BY_TAG.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.VOICE, mediaId, tagId, false);
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据 OpenId 列表群发视频消息。
     * @param mediaId 用于群发的视频素材的 media_id。
     * @param openIds OpenId 列表。
     * @return 发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID；
     * 若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WeChatApiResult sendMassVideoMessageToUsers(String mediaId, String... openIds) {
        Args.notEmpty(mediaId, "Media ID of video material");
        Args.notNull(openIds, "OpenId list");
        Args.notEmpty(Arrays.asList(openIds), "OpenId list");

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SNED_BY_OPENIDS.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.MP_VIDEO, mediaId, Arrays.asList(openIds));
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据标签群发视频消息。
     * @param mediaId 用于群发的视频素材的 media_id。
     * @param tagId 群发到的标签的tag_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id。
     * @param isToAll 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据tag_id发送给指定群组的用户。
     *                此参数的使用有些需要注意的地方，请查阅微信开发者文档群发消息的部分。
     * @return 发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID；
     * 若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WeChatApiResult sendMassVideoMessageToUsersWithTag(String mediaId, String tagId, boolean isToAll) {
        Args.notEmpty(mediaId, "Media ID of video material");
        if (!isToAll) {
            Args.notEmpty(tagId, "Tag ID");
        }

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SEND_BY_TAG.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.MP_VIDEO, mediaId, tagId, false);
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据 OpenId 列表群发音乐消息。
     * @param mediaId 用于群发的音乐素材的 media_id。
     * @param openIds OpenId 列表。
     * @return 发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID；
     * 若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WeChatApiResult sendMassMusicToUsers(String mediaId, String... openIds) {
        Args.notEmpty(mediaId, "Media ID of music material");
        Args.notNull(openIds, "OpenId list");
        Args.notEmpty(Arrays.asList(openIds), "OpenId list");

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SNED_BY_OPENIDS.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.MUSIC, mediaId, Arrays.asList(openIds));
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据标签群发音乐消息。
     * @param mediaId 用于群发的音乐素材的 media_id。
     * @param tagId 群发到的标签的tag_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id。
     * @param isToAll 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据tag_id发送给指定群组的用户。
     *                此参数的使用有些需要注意的地方，请查阅微信开发者文档群发消息的部分。
     * @return 发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID；
     * 若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。
     */
    public WeChatApiResult sendMassMusicToUsersWithTag(String mediaId, String tagId, boolean isToAll) {
        Args.notEmpty(mediaId, "Media ID of music material");
        if (!isToAll) {
            Args.notEmpty(tagId, "Tag ID");
        }

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SEND_BY_TAG.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.MUSIC, mediaId, tagId, false);
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据 OpenId 列表群发图文消息。
     * @param mediaId 用于群发的图文素材的 media_id。
     * @param openIds OpenId 列表。
     * @return <p>发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID。
     * 通过 {@link WeChatApiResult#getMsgDataId()} 可以得到消息的数据ID，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍。</p>
     * <p>若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。</p>
     */
    public WeChatApiResult sendMassArticleToUsers(String mediaId, String... openIds) {
        Args.notEmpty(mediaId, "Media ID of news material");
        Args.notNull(openIds, "OpenId list");
        Args.notEmpty(Arrays.asList(openIds), "OpenId list");

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SNED_BY_OPENIDS.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.MP_NEWS, mediaId, Arrays.asList(openIds));
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据标签群发图文消息。
     * @param mediaId 用于群发的图文素材的 media_id。
     * @param tagId 群发到的标签的tag_id，参加用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id。
     * @param isToAll 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据tag_id发送给指定群组的用户。
     *                此参数的使用有些需要注意的地方，请查阅微信开发者文档群发消息的部分。
     * @return <p>发送成功后通过 {@link WeChatApiResult#getMsgId()} 可以得到消息发送任务的ID。
     * 通过 {@link WeChatApiResult#getMsgDataId()} 可以得到消息的数据ID，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍。</p>
     * <p>若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。</p>
     */
    public WeChatApiResult sendMassArticleToUsersWithTag(String mediaId, String tagId, boolean isToAll) {
        Args.notEmpty(mediaId, "Media ID of news material");
        if (!isToAll) {
            Args.notEmpty(tagId, "Tag ID");
        }

        final String url = WeChatConstants.WECHAT_POST_MESSAGE_MASS_SEND_BY_TAG.replace("${ACCESS_TOKEN}", super.getTokenManager().getAccessToken());
        MessageForMassSend msg = new MessageForMassSend(OutMessageType.MP_NEWS, mediaId, tagId, false);
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, msg.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 向用户发送模板消息。
     *
     * @param templateId 消息模板ID
     * @param openid 接收消息的用户的 openid
     * @param contentParams <p>消息模板中需要传入的数据，key-value pairs。数据的 key 为消息模板中设置的参数名，若需要自定义该数据的显示颜色，
     *                      则在 key 后面加上十六进制的颜色值。</p>
     *                      <p>举例：消息模板内容中设置了参数数据 {{withdrawMoney.DATA}}，则此参数的 key 为 "withdrawMoney"，
     *                      若需要该数据显示颜色为 #173177，则 key 为 "withdrawMoney#173177"。</p>
     * @return <p>微信服务器成功接收发送模板消息的请求后，可通过 {@link WeChatApiResult#getMsgId()} 得到消息发送任务的ID。
     * 注意：此时消息可能还未发送给用户。在模版消息发送任务完成后，微信服务器将会向开发者服务器推送是否送达成功的事件通知。</p>
     * <p>若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。</p>
     */
    public WeChatApiResult sendTemplateMessage(String templateId, String openid, Map<String, String> contentParams) {
        Args.notEmpty(templateId, "ID of message template");
        Args.notEmpty(openid, "OpenID of message receiver");
        final String url = WeChatConstants.WECHAT_POST_SEND_TEMPLATE_MESSAGE.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken());
        final TemplateMessageForSend templateMessageForSend =
                new TemplateMessageForSend(templateId, openid, contentParams);
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(url, templateMessageForSend.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 向用户发送模板消息。
     * @param templateId 消息模板ID。
     * @param openid 接收消息的用户的 openid。
     * @param url 模板跳转链接。
     * @param contentParams <p>消息模板中需要传入的数据，key-value pairs。数据的 key 为消息模板中设置的参数名，若需要自定义该数据的显示颜色，
     *                      则在 key 后面加上十六进制的颜色值。</p>
     *                      <p>举例：消息模板内容中设置了参数数据 {{withdrawMoney.DATA}}，则此参数的 key 为 "withdrawMoney"，
     *                      若需要该数据显示颜色为 #173177，则 key 为 "withdrawMoney#173177"。</p>
     * @return <p>微信服务器成功接收发送模板消息的请求后，可通过 {@link WeChatApiResult#getMsgId()} 得到消息发送任务的ID。
     * 注意：此时消息可能还未发送给用户。在模版消息发送任务完成后，微信服务器将会向开发者服务器推送是否送达成功的事件通知。</p>
     * <p>若发送失败，可以通过 {@link WeChatApiResult#getErrcode()} 和 {@link WeChatApiResult#getErrmsg()}
     * 得到错误代码和错误信息。</p>
     */
    public WeChatApiResult sendTemplateMessage(String templateId, String openid, String url, Map<String, String> contentParams) {
        Args.notEmpty(templateId, "ID of message template");
        Args.notEmpty(openid, "OpenID of message receiver");
        final String postUrl = WeChatConstants.WECHAT_POST_SEND_TEMPLATE_MESSAGE.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken());
        final TemplateMessageForSend templateMessageForSend =
                new TemplateMessageForSend(templateId, openid, url, contentParams);
        try {
            JsonObject jsonResponse = HttpClientUtil.sendPostRequestWithJsonBody(postUrl, templateMessageForSend.toJsonString());
            return WeChatApiResult.instanceOf(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取帐号下所有的消息模板列表。
     * @return 帐号下所有的消息模板列表。
     * @throws WeChatApiErrorException 如果微信API调用失败，返回了错误码和错误信息，会抛出此异常。
     */
    public List<MessageTemplate> retrieveMessageTemplates() throws WeChatApiErrorException {
        final String url = WeChatConstants.WECHAT_GET_MESSAGE_TEMPLATES.replace("${ACCESS_TOKEN}", super.tokenManager.getAccessToken());
        JsonObject jsonResponse;
        try {
            jsonResponse = HttpClientUtil.sendGetRequestAndGetJsonResponse(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!jsonResponse.has("template_list")) {
            throw new WeChatApiErrorException(jsonResponse.get("errcode").getAsInt(), jsonResponse.get("errmsg").getAsString());
        } else {
            JsonArray teplArray = jsonResponse.getAsJsonArray("template_list");
            if (teplArray.size() == 0) {
                return Collections.emptyList();
            }
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            return gson.fromJson(teplArray, new TypeToken<ArrayList<MessageTemplate>>(){}.getType());
        }

    }


    /**
     * 解析事件消息。
     *
     * @param msgInXml XML 消息数据包
     * @param eventType 事件类型
     * @return 解析后的事件消息对象
     */
    private ReceivedMsg parseEventMessage(final String msgInXml, EventType eventType) throws WechatXmlMessageParseException {
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

}
