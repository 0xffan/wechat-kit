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
import com.google.gson.GsonBuilder;
import me.ixfan.wechatkit.message.out.OutMessageType;

import java.util.List;

/**
 * @author Warren Fan
 */
public class MessageForMassSend {

    private Filter filter;
    private String msgType;
    private String msgContent;
    private List<String> toUser;
    private String title;
    private String description;

    /**
     * 构建根据分组群发消息的消息对象。
     *
     * @param msgType 消息类型。详见{@link OutMessageType}。
     * @param msgContent 消息内容。
     * @param groupId 接收消息的分组。
     * @param isToAll 使用 isToAll 为 true 且成功群发，会使得此次群发进入历史消息列表。
     */
    public MessageForMassSend(OutMessageType msgType, String msgContent, int groupId, boolean isToAll)  {
        this.msgType = msgType.stringValue();
        this.msgContent = msgContent;
        this.filter = new Filter(groupId, isToAll);
    }

    /**
     * 构建根据 OpenId 列表群发消息的消息对象。
     *
     * @param msgType 消息类型。详见{@link OutMessageType}。
     * @param msgContent 消息内容。
     * @param toUser 消息的接收者，一串OpenID列表，OpenID最少2个，最多10000个。
     */
    public MessageForMassSend(OutMessageType msgType, String msgContent, List<String> toUser) {
        this.msgType = msgType.stringValue();
        this.msgContent = msgContent;
        this.toUser = toUser;
    }

    public String toJsonString() {
        Gson gson = new GsonBuilder().registerTypeAdapter(MessageForMassSend.class, new MassMessageGsonTypeAdapter()).create();
        return gson.toJson(this);
    }

    public Filter getFilter() {
        return filter;
    }

    public String getMsgType() {
        return msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getToUser() {
        return toUser;
    }

    static class Filter {
        private boolean isToAll;
        private int groupId;

        Filter(int groupId, boolean isToAll) {
            this.groupId = groupId;
            this.isToAll = isToAll;
        }

        public boolean isToAll() {
            return isToAll;
        }

        public void setToAll(boolean toAll) {
            isToAll = toAll;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }
    }

}

