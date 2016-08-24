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

/**
 * @author Warren Fan
 */
public enum InMessageType {

    /**
     * 文本消息
     */
    TEXT { @Override public String stringValue() { return "text";}},
    /**
     * 图片消息
     */
    IMAGE { @Override public String stringValue() { return "image";}},
    /**
     * 语音消息
     */
    VOICE { @Override public String stringValue() { return "voice";}},
    /**
     * 视频消息
     */
    VIDEO { @Override public String stringValue() { return "video";}},
    /**
     * 小视频消息
     */
    SHORT_VIDEO { @Override public String stringValue() { return "shortvideo";}},
    /**
     * 地理位置消息
     */
    LOCATION { @Override public String stringValue() { return "location";}},
    /**
     * 链接消息
     */
    LINK { @Override public String stringValue() { return "link";}},
    /**
     * 事件推送
     */
    EVENT { @Override public String stringValue() { return "event";}},
    /**
     * 未知消息类型
     */
    UNKNOWN { @Override public String stringValue() { return "unknown";}};

    public static InMessageType of(String messageType) {
        switch (messageType) {
            case "text": return TEXT;
            case "image": return IMAGE;
            case "voice": return VOICE;
            case "video": return VIDEO;
            case "shortvideo": return SHORT_VIDEO;
            case "location": return LOCATION;
            case "link": return LINK;
            case "event": return EVENT;
            default: return UNKNOWN;
        }
    }

    public abstract String stringValue();

}
