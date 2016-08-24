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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xfan on 16/3/26.
 */
@XmlRootElement(name = "xml")
public class ReceivedVoiceMsg extends ReceivedMsg {

    /**
     * 消息ID, 64位整型
     */
    private String msgId;

    /**
     * 语音消息媒体id, 可以调用多媒体文件下载接口拉取数据.
     */
    private String mediaId;

    /**
     * 语音格式, 如amr, speex等.
     */
    private String format;

    /**
     * 开通语音识别后, 微信会在推送的语音消息XML数据包中, 增加一个 Recognition 字段, 代表语音识别结果, UTF-8 编码。
     */
    private String recognition;

    @XmlElement(name = "MsgId", required = true)
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @XmlElement(name = "MediaId", required = true)
    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    @XmlElement(name = "Format", required = true)
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @XmlElement(name = "Recognition", required = false)
    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    @Override
    public String getMsgType() {
        return InMessageType.VOICE.stringValue();
    }
}
