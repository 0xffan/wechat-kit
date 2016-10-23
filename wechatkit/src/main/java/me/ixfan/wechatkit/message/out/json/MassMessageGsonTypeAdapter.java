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

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.ixfan.wechatkit.message.out.OutMessageType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Warren Fan
 */
public class MassMessageGsonTypeAdapter extends TypeAdapter<MessageForMassSend> {
    @Override
    public void write(JsonWriter out, MessageForMassSend value) throws IOException {
        out.beginObject();

        if (null != value.getFilter()) {
            out.name("filter").beginObject();
            out.name("is_to_all").value(value.getFilter().isToAll());
            out.name("group_id").value(value.getFilter().getGroupId());
            out.endObject();
        }

        if (null != value.getToUser()) {
            out.name("touser").beginArray();
            for (String openid: value.getToUser()) {
                out.value(openid);
            }
            out.endArray();
        }

        out.name("msgtype").value(value.getMsgType());
        final OutMessageType msgType = OutMessageType.instanceOf(value.getMsgType());
        switch (msgType) {
            case TEXT:
                out.name(OutMessageType.TEXT.stringValue()).beginObject();
                out.name("content").value(value.getMsgContent());
                out.endObject();
                break;
            case IMAGE:
                out.name(OutMessageType.IMAGE.stringValue()).beginObject();
                out.name("media_id").value(value.getMsgContent());
                out.endObject();
                break;
            case VOICE:
                out.name(OutMessageType.VOICE.stringValue()).beginObject();
                out.name("media_id").value(value.getMsgContent());
                out.endObject();
                break;
            case VIDEO:
                out.name(OutMessageType.VIDEO.stringValue()).beginObject();
                out.name("media_id").value(value.getMsgContent());
                if (null != value.getTitle()) {
                    out.name("title").value(value.getTitle());
                }
                if (null != value.getDescription()) {
                    out.name("description").value(value.getDescription());
                }
                out.endObject();
                break;
            case MUSIC:
                out.name(OutMessageType.MUSIC.stringValue()).beginObject();
                out.name("media_id").value(value.getMsgContent());
                out.endObject();
                break;
            case NEWS:
                out.name(OutMessageType.NEWS.stringValue()).beginObject();
                out.name("media_id").value(value.getMsgContent());
                out.endObject();
                break;
            case MP_NEWS:
                out.name(OutMessageType.MP_NEWS.stringValue()).beginObject();
                out.name("media_id").value(value.getMsgContent());
                out.endObject();
                break;
            case MP_VIDEO:
                out.name(OutMessageType.MP_VIDEO.stringValue()).beginObject();
                out.name("media_id").value(value.getMsgContent());
                if (null != value.getTitle()) {
                    out.name("title").value(value.getTitle());
                }
                if (null != value.getDescription()) {
                    out.name("description").value(value.getDescription());
                }
                out.endObject();
                break;
            case WX_CARD:
                out.name(OutMessageType.WX_CARD.stringValue()).beginObject();
                out.name("card_id").value(value.getMsgContent());
                out.endObject();
                break;
            default:
                out.name("msg_body").value("未知消息类型");
        }

        out.endObject();
    }

    @Override
    public MessageForMassSend read(JsonReader in) throws IOException {
        MessageForMassSend.Filter filter = null;
        List<String> toUser = null;
        String msgType = null;
        String msgContent = null;

        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "msgtype":
                    msgType = in.nextString();
                    break;
                case "filter":
                    in.beginObject();
                    int groupId = -1;
                    boolean isToAll = false;
                    while (in.hasNext()) {
                        switch (in.nextName()) {
                            case "is_to_all":
                                isToAll = in.nextBoolean();
                                break;
                            case "group_id":
                                groupId = in.nextInt();
                                break;
                            default: break;
                        }
                    }
                    in.endObject();
                    filter = new MessageForMassSend.Filter(groupId, isToAll);
                    break;
                case "touser":
                    in.beginArray();
                    toUser = new ArrayList<>();
                    while (in.hasNext()) {
                        toUser.add(in.nextString());
                    }
                    in.endArray();
                    break;
                case "text":
                case "image":
                case "voice":
                case "mpnews":
                case "mpvideo":
                case "wxcard":
                    in.beginObject();
                    while (in.hasNext()) {
                        switch (in.nextName()) {
                            case "content":
                            case "media_id":
                            case "card_id":
                                msgContent = in.nextString();
                                break;
                            default: break;
                        }
                    }
                    in.endObject();
                    break;
                default: break;
            }
        }

        in.endObject();

        if (null != filter) {
            return new MessageForMassSend(OutMessageType.valueOf(msgType), msgContent, filter.getGroupId(), filter.isToAll());
        } else if (null != toUser) {
            return new MessageForMassSend(OutMessageType.valueOf(msgType), msgContent, toUser);
        }
        return null;
    }
}
