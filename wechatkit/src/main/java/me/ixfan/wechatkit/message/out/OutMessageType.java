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
package me.ixfan.wechatkit.message.out;

/**
 * 公众号向用户发送消息时可选的消息类型。
 *
 * @author Warren Fan
 */
public enum OutMessageType {

    /**
     * 文本消息。主动推消息和发送被动响应消息时都可使用。
     */
    Text("text", "文本消息"),
    /**
     * 图片消息。主动推消息和发送被动响应消息时都可使用。
     */
    Image("image", "图片消息"),
    /**
     * 语音消息。主动推消息和发送被动响应消息时都可使用。
     */
    Voice("voice", "语音消息"),
    /**
     * 视频消息。发送被动响应视频消息时使用。
     */
    Video("video", "视频消息"),
    /**
     * 音乐消息。主动推消息和发送被动响应消息时都可使用。
     */
    Music("music", "音乐消息"),
    /**
     * 图文消息。发送被动响应消息时使用。
     */
    News("news", "图文消息"),
    /**
     * 图文消息。主动推消息时使用。
     */
    MpNews("mpnews", "图文消息"),
    /**
     * 视频消息。主动推消息时使用。
     */
    MpVideo("mpvideo", "视频消息"),
    /**
     * 卡券消息。主动推消息时使用。
     */
    WXCard("wxcard", "卡券消息");

    private String value;
    private String description;

    OutMessageType(String msgType, String description) {
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
