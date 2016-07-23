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

/**
 * Created by xfan on 16/3/27.
 */
public enum ReceivedMsgType {

    Text("text", "文本消息"),
    Image("image", "图片消息"),
    Voice("voice", "语音消息"),
    Video("video", "视频消息"),
    ShortVideo("shortvideo", "小视频消息"),
    Location("location", "地理位置消息"),
    Link("link", "链接消息"),
    Event("event", "事件推送");

    private String value;
    private String description;

    ReceivedMsgType(String msgType, String description) {
        this.value = msgType;
        this.description = description;
    }

    public String value() {
        return value;
    }

    public String description() {
        return description;
    }
}
